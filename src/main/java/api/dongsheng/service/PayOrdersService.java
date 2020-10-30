package api.dongsheng.service;

import api.dongsheng.model.dto.NotifyDTO;
import api.dongsheng.model.entity.PaymentOrder;
import api.dongsheng.model.vo.OrderRespVO;
import api.dongsheng.model.vo.PaymentOrderReqVO;
import api.dongsheng.model.vo.PlaceOrderReqVO;

import java.util.List;
import java.util.Map;

/**
 * @program: aioh_sw_oocs
 * @description: 订单付款处理
 * @author: urbane
 * @create: 2020-04-18 11:35
 **/
public interface PayOrdersService {

    /**
     * 统一下单接口
     *
     * @param placeOrderReqVO 下订单参数对象
     * @param ip              用户真实IP
     * @param channelId       下游渠道ID
     * @param productId       下游渠道产品ID
     * @return api.dongsheng.model.vo.OrderRespVO
     * @title unifiedOrder
     * @description
     * @author urbane
     * @updateTime 2020/4/18  15:42
     */
    OrderRespVO unifiedOrder(PlaceOrderReqVO placeOrderReqVO, String ip, String channelId,String productId) throws Exception;

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
    void callbackNotify(NotifyDTO notifyDTO);

    /**
     * 查询订单信息
     * @param paymentOrderId
     * @return
     *
     */
    PaymentOrderReqVO selectPaymentOrderById(String paymentOrderId);

    /**
     * 获取每天赠送会员数
     * @return
     */
    Integer selectGiveMemberNum();

    /**
     * 获取每天支付总金额
     * @return
     */
    List<PaymentOrder> selectPaymentTotalMoney();
}
