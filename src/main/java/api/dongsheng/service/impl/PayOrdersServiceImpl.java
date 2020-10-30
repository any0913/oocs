package api.dongsheng.service.impl;

import ai.dongsheng.id.tool.core.IdTemplate;
import api.dongsheng.common.RetCode;
import api.dongsheng.common.TmeCattleConstant;
import api.dongsheng.enums.MemberRightsEnum;
import api.dongsheng.enums.OrderStatusEnum;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.dto.NotifyDTO;
import api.dongsheng.model.entity.ChannelProduct;
import api.dongsheng.model.entity.Membership;
import api.dongsheng.model.entity.MembershipRightsError;
import api.dongsheng.model.entity.PaymentOrder;
import api.dongsheng.model.mapper.ChannelProductMapper;
import api.dongsheng.model.mapper.MembershipMapper;
import api.dongsheng.model.mapper.MembershipRightsErrorMapper;
import api.dongsheng.model.mapper.PaymentOrderMapper;
import api.dongsheng.model.vo.OrderRespVO;
import api.dongsheng.model.vo.PaymentOrderReqVO;
import api.dongsheng.model.vo.PlaceOrderReqVO;
import api.dongsheng.service.PayOrdersService;
import api.dongsheng.service.strategyfactory.PayStrategyFactory;
import api.dongsheng.util.StringUtil;
import api.dongsheng.util.TmeCattleUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @program: aioh_sw_oocs
 * @description:
 * @author: urbane
 * @create: 2020-04-18 11:36
 **/
@Slf4j
@Service
public class PayOrdersServiceImpl implements PayOrdersService {

    @Autowired
    private MembershipMapper membershipMapper;

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private PayStrategyFactory payStrategyFactory;

    @Autowired
    private MembershipRightsErrorMapper membershipRightsErrorMapper;

    @Autowired
    private ChannelProductMapper channelProductMapper;

    /**
     * 统一下单接口
     *
     * @param placeOrderReqVO 下订单参数对象
     * @param ip              用户真实IP
     * @param channelId       下游渠道ID
     * @return api.dongsheng.model.vo.OrderRespVO
     * @title unifiedOrder
     * @description
     * @author urbane
     * @updateTime 2020/4/18  15:42
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderRespVO unifiedOrder(PlaceOrderReqVO placeOrderReqVO, String ip, String channelId,String productId) throws Exception {
        OrderRespVO orderRespVO;
        String goodsId = placeOrderReqVO.getGoodsId();
        Membership membership = membershipMapper.selectByGoodsId(goodsId);
        if(membership != null){
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setChannelId(Long.valueOf(channelId));
            paymentOrder.setProductId(Long.valueOf(productId));
            paymentOrder.setPassport(placeOrderReqVO.getPassport());
            paymentOrder.setUserId(placeOrderReqVO.getUserid());
            paymentOrder.setUserIp(ip);
            paymentOrder.setDeviceId(placeOrderReqVO.getDevice_id());
            paymentOrder.setToken(placeOrderReqVO.getToken());
            paymentOrder.setSp(placeOrderReqVO.getSp());
            paymentOrder.setGoodsId(goodsId);
            paymentOrder.setOrderAmount(membership.getRulingPrice());
            // 生成支付平台订单ID
            Long id = Long.valueOf(new Random().nextInt(100000000));//IdTemplate.nextId();
            String datetime = new SimpleDateFormat("yyyyMMHHdd").format(new Date());
            String payOrderId = new StringBuffer().append(datetime).append(id).toString();
            paymentOrder.setPaymentOrderId(payOrderId);
            paymentOrderMapper.insertSelective(paymentOrder);
            // 获取支付宝支付url
            String alipayCodeUrl = payStrategyFactory.getPayStrategy("Alipay").scanCodePay(payOrderId,
                    paymentOrder.getOrderAmount(), ip,
                    membership.getMembershipName());
            // 获取微信支付url
            String WeChaCodeUrl = payStrategyFactory.getPayStrategy("WeChat").contractOrder(payOrderId,
                    paymentOrder.getOrderAmount(), ip,
                    membership.getMembershipName());
            orderRespVO = new OrderRespVO(goodsId, membership.getMembershipName(), membership.getMembershipNorms(),
                    paymentOrder.getOrderAmount(), payOrderId, alipayCodeUrl, WeChaCodeUrl);
        }else{
            throw new BaseException(RetCode.GOODSID_NOT_FOUNT.getCode(),RetCode.GOODSID_NOT_FOUNT.getMsg());
        }
        return orderRespVO;
    }


    /**
     * 获取渠道产品对应的产品信息
     * @param channelId
     * @param productId
     */
    private synchronized ChannelProduct getChannelProduct(String channelId,String productId){
        ChannelProduct channelProduct = new ChannelProduct();
        channelProduct.setChannel_id(Long.valueOf(channelId));
        channelProduct.setProduct_id(Long.valueOf(productId));
        ChannelProduct product = channelProductMapper.selectChannelProduct(channelProduct);
        if(product == null){
            throw new BaseException(RetCode.PRODUCT_NOT_FOUND.getCode(),RetCode.PRODUCT_NOT_FOUND.getMsg());
        }
        return product;
    }


    /**
     * 处理第三方异步通知结果
     *
     * @param notifyDTO
     * @return void
     * @title callbackNotify
     * @description
     * @author urbane
     * @updateTime 2020/4/18  17:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callbackNotify(NotifyDTO notifyDTO) {
        PaymentOrder paymentOrder = paymentOrderMapper.selectByPaymentOrderId(notifyDTO.getPaymentOrderId());
        if (paymentOrder.getOrderAmount().equals(notifyDTO.getPaymentAmount())) {
            paymentOrder.setPaymentStatus(OrderStatusEnum.ORDER_PAYMENT_SUCCESS.getCode());
        }else {
            log.error("支付金额被篡改:{},orderAmount:{}",notifyDTO,paymentOrder.getOrderAmount());
            throw new BaseException(RetCode.ERROR_PAYMENT_AMOUNT.getCode(), RetCode.ERROR_PAYMENT_AMOUNT.getMsg());
        }
        paymentOrder.setThirdPartyOrder(notifyDTO.getThirdPartyOrder());
        paymentOrder.setPaymentAmount(notifyDTO.getPaymentAmount());
        paymentOrder.setPayType(notifyDTO.getPayType());
        paymentOrderMapper.updateByPrimaryKeySelective(paymentOrder);
        if (paymentOrder.getPaymentStatus().equals(OrderStatusEnum.ORDER_PAYMENT_SUCCESS.getCode())
                && paymentOrder.getIssuedRights().equals(MemberRightsEnum.NOT_TO_ISSUE.getIssuedRights())) {
            issuedMembers(paymentOrder);
        }
    }

    /**
     * 查询订单信息
     * @param paymentOrderId
     * @return
     */
    @Override
    public PaymentOrderReqVO selectPaymentOrderById(String paymentOrderId) {
        PaymentOrderReqVO paymentOrderReqVO = new PaymentOrderReqVO();
        PaymentOrder paymentOrder = paymentOrderMapper.selectByPaymentOrderId(paymentOrderId);
        if(paymentOrder != null){
            BeanUtils.copyProperties(paymentOrder,paymentOrderReqVO);
        }else{
            throw new BaseException(RetCode.PAYMENTORDERID_NOT_FOUNT.getCode(),RetCode.PAYMENTORDERID_NOT_FOUNT.getMsg());
        }
        return paymentOrderReqVO;
    }


    /**
     * 收到订单支付成功后进行下发会员权益
     *
     * @param paymentOrder
     * @return void
     * @title issuedMembers
     * @description
     * @author urbane
     * @updateTime 2020/4/21  17:55
     */
    public void issuedMembers(PaymentOrder paymentOrder) {
        Membership membership = membershipMapper.selectByGoodsId(paymentOrder.getGoodsId());
        JSONObject tmeReqJson = new JSONObject();
        tmeReqJson.put("sp", paymentOrder.getSp());
        tmeReqJson.put("device_id", paymentOrder.getDeviceId());
        tmeReqJson.put("client_ip", paymentOrder.getUserIp());
        tmeReqJson.put("userid", paymentOrder.getUserId());
        tmeReqJson.put("token", paymentOrder.getToken());
        tmeReqJson.put("partner_no", paymentOrder.getPaymentOrderId());
        tmeReqJson.put("month", membership.getMembershipNorms());
        // 根据订单渠道id和产品id，获取产品对应的牛方案2.0
        ChannelProduct product = getChannelProduct(paymentOrder.getChannelId().toString(),paymentOrder.getProductId().toString());
        tmeReqJson.put(TmeCattleConstant.TME_PID,product.getTme_pid());
        tmeReqJson.put(TmeCattleConstant.TME_MEMBER_KEY,product.getTme_member_key());
        String giveVip = TmeCattleUtil.giveVip(tmeReqJson);
        JSONObject tmeRespJson = JSONObject.parseObject(giveVip);
        Integer error_code = tmeRespJson.getInteger("error_code");
        if (!error_code.equals(0)) {
            MembershipRightsError membershipRightsError = new MembershipRightsError();
            membershipRightsError.setPaymentOrderId(paymentOrder.getPaymentOrderId());
            membershipRightsError.setRequestingInformation(tmeReqJson.toJSONString());
            membershipRightsError.setErrorMessage(tmeRespJson.toJSONString());
            membershipRightsErrorMapper.insertSelective(membershipRightsError);
            paymentOrder.setIssuedRights(MemberRightsEnum.ISSUE_FALL.getIssuedRights());
        } else {
            paymentOrder.setIssuedRights(MemberRightsEnum.ISSUE_SUCCESS.getIssuedRights());
        }
        paymentOrderMapper.updateByPrimaryKeySelective(paymentOrder);

    }


    /**
     * 获取赠送会员数
     * @return
     */
    @Override
    public Integer selectGiveMemberNum() {
        return paymentOrderMapper.selectGiveMemberNum();
    }

    /**
     * 获取每天各支付方式的支付总金额
     * @return
     */
    @Override
    public List<PaymentOrder> selectPaymentTotalMoney() {
        return paymentOrderMapper.selectPaymentTotalMoney();
    }
}
