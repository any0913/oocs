package api.dongsheng.service.strategyfactory;

import api.dongsheng.common.RetCode;
import api.dongsheng.config.AlipayConfig;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.dto.PayOrderStatusDTO;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @program: aioh_sw_oocs
 * @description: 支付宝支付
 * @author: urbane
 * @create: 2020-04-16 16:11
 **/
@Slf4j
@Component("Alipay")
public class AlipayPay implements PayStrategy {

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * 保留2位小数
     */
    private final static int SCALE = 2;
    /**
     * 将分转换成元
     */
    private final static BigDecimal CONVERSION__BIGDECIMAL = new BigDecimal("100");

    /**
     * 此接口支付宝文档地址：https://opendocs.alipay.com/apis/api_1/alipay.trade.precreate
     *
     * @param payOrderId 支付订单ID
     * @param orderPrice 订单金额
     * @param ip         用户真实IP
     * @param goodsName  商品名称
     * @return java.lang.String    生成二维码的字符串
     * @title scanCodePay
     * @description 扫码支付
     * @author urbane
     * @updateTime 2020/4/16  16:32
     */
    @Override
    public String scanCodePay(String payOrderId, int orderPrice, String ip, String goodsName) {
        log.info("AlipayPay in parameter payOrderId:{},orderPrice:{},ip:{},goodsName:{}", payOrderId, orderPrice, ip,
                goodsName);
        // 将金额转换成以元为单位
        BigDecimal orderPriceBigDecimal = new BigDecimal(String.valueOf(orderPrice));
        BigDecimal payMoneyBigDecimal = orderPriceBigDecimal.divide(CONVERSION__BIGDECIMAL, SCALE,
                BigDecimal.ROUND_HALF_UP);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        Map<String, Object> alipayMap = Maps.newHashMapWithExpectedSize(5);
        //付款ID，必填
        alipayMap.put("out_trade_no", payOrderId);
        //付款金额，必填
        alipayMap.put("total_amount", payMoneyBigDecimal.toString());
        // 订单标题，必填
        if (goodsName == null) {
            alipayMap.put("subject", alipayConfig.getSubject());
        } else {
            alipayMap.put("subject", "购买" + goodsName);
        }
        String alipayJson = new JSONObject(alipayMap).toJSONString();
        request.setBizContent(alipayJson);
        // 支付回调通知接口，可以被外网访问的
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("AlipayPay scanCodePay 支付宝SDK错误 错误信息：{}，请求参数：{}", e.getErrMsg(), alipayJson);
            throw new BaseException(RetCode.ERROR_ALIPAY_PAYMENT.getCode(),
                    RetCode.ERROR_ALIPAY_PAYMENT.getMsg());
        }
        String alipayResp = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(alipayResp);
        JSONObject alipay_trade_precreate_response = jsonObject.getJSONObject("alipay_trade_precreate_response");
        if (!alipay_trade_precreate_response.getString("code").equals("10000") || !alipay_trade_precreate_response.getString(
                "msg").equals("Success")) {
            log.error("向支付宝下单失败！请求参数：{},返回信息：{}", alipayJson, alipay_trade_precreate_response);
            throw new BaseException(RetCode.ERROR_ALIPAY_ORDERS.getCode(), RetCode.ERROR_ALIPAY_ORDERS.getMsg());
        }
        return alipay_trade_precreate_response.getString("qr_code");
    }


    /**
     * 主动去第三方查询支付状态
     *
     * @param payOrderId 支付订单ID
     * @return
     */
    @Override
    public PayOrderStatusDTO getOrderStatus(String payOrderId) {
        return null;
    }

    /**
     * 支付中签约代扣协议
     * @param payOrderId 支付订单ID
     * @param orderPrice 订单金额
     * @param ip         用户真实IP
     * @param goodsName  商品名称
     * @return
     */
    @Override
    public String contractOrder(String payOrderId, int orderPrice, String ip, String goodsName) {
        return null;
    }

    @Override
    public String doOperation() {
        return "这是支付宝支付";
    }
}
