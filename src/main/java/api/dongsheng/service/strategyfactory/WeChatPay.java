package api.dongsheng.service.strategyfactory;

import api.dongsheng.common.RetCode;
import api.dongsheng.config.WeChatConfig;
import api.dongsheng.enums.WeChatTradeTypeEnum;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.dto.PayOrderStatusDTO;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @program: aioh_sw_oocs
 * @description: 微信支付
 * @author: urbane
 * @create: 2020-04-16 16:13
 **/
@Slf4j
@Component("WeChat")
public class WeChatPay implements PayStrategy {

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 此接口微信文档地址：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
     *
     * @param payOrderId 支付订单ID
     * @param orderPrice 订单金额
     * @param ip         用户真实IP
     * @param goodsName  商品名称
     * @return java.lang.String    生成二维码的字符串
     * @title scanCodePay
     * @description
     * @author urbane
     * @updateTime 2020/4/16  17:16
     */
    @Override
    public String scanCodePay(String payOrderId, int orderPrice, String ip, String goodsName) {
        log.info("WeChatPay in parameter payOrderId:{},orderPrice:{},ip:{},goodsName:{}", payOrderId, orderPrice, ip,
                goodsName);
        String body = weChatConfig.getBody();
        if (goodsName != null) {
            body = "购买" + goodsName;
        }
        Map<String, String> params = UnifiedOrderModel
                .builder()
                .appid(weChatConfig.getAppId())
                .mch_id(weChatConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .body(body)
                .out_trade_no(payOrderId)
                .total_fee(String.valueOf(orderPrice))
                .spbill_create_ip(ip)
                .trade_type(WeChatTradeTypeEnum.NATIVE.getTradeType())
                .notify_url(weChatConfig.getNotifyUrl())
                .build()
                .createSign(weChatConfig.getKey(), SignType.MD5);
        String xmlResult = WxPayApi.pushOrder(false, params);
        Map<String, String> result = WxPayKit.xmlToMap(xmlResult);
        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (!WxPayKit.codeIsOk(returnCode)) {
            log.error("WeChatAppPay payment error! parameter:[{}], Error cause == >>> Message:[{}]", params,
                    returnMsg);
            throw new BaseException(RetCode.ERROR_WECHAT_ORDERS.getCode(), returnMsg);
        }
        String resultCode = result.get("result_code");
        if (!WxPayKit.codeIsOk(resultCode)) {
            String err_code_des =  result.get("err_code_des");
            log.error("WeChatAppPay payment error! parameter:[{}], Error cause == >>> Message:[{}]", params,
                    err_code_des);
            throw new BaseException(RetCode.ERROR_WECHAT_ORDERS.getCode(), err_code_des);
        }
        return result.get("code_url");
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
        String body = weChatConfig.getBody();
        if (goodsName != null) {
            body = "购买" + goodsName;
        }
        Map<String, String> signInfo = new HashMap<>();
        signInfo.put("appid", weChatConfig.getAppId());
        signInfo.put("mch_id", weChatConfig.getMchId());
        signInfo.put("contract_mchid", weChatConfig.getMchId());
        signInfo.put("contract_appid",  weChatConfig.getAppId());
        signInfo.put("out_trade_no", payOrderId);
        signInfo.put("nonce_str", UUID.randomUUID().toString().replaceAll("-",""));
        signInfo.put("body", body);
        signInfo.put("notify_url",weChatConfig.getNotifyUrl());
        signInfo.put("total_fee", String.valueOf(orderPrice));
        signInfo.put("spbill_create_ip", ip);
        signInfo.put("trade_type", WeChatTradeTypeEnum.NATIVE.getTradeType());
        signInfo.put("plan_id", "111111111");
        signInfo.put("contract_code", "1"+new Random().nextInt(10000000));
        signInfo.put("request_serial", "1"+new Random().nextInt(10000000));
        signInfo.put("contract_display_account", "any");
        signInfo.put("contract_notify_url", "https://t1.aidongsheng.com/v2/open/ocs/payment/notify/contract");
        String sign = WxPayKit.createSign(signInfo,weChatConfig.getKey(),SignType.MD5);
        signInfo.put("sign", sign);
        String xmlResult = WxPayApi.contractOrder(signInfo);
        Map<String, String> result = WxPayKit.xmlToMap(xmlResult);
        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (!WxPayKit.codeIsOk(returnCode)) {
            log.error("WeChatAppPay payment error! parameter:[{}], Error cause == >>> Message:[{}]", signInfo,
                    returnMsg);
            throw new BaseException(RetCode.ERROR_WECHAT_ORDERS.getCode(), returnMsg);
        }
        String resultCode = result.get("result_code");
        if (!WxPayKit.codeIsOk(resultCode)) {
            String err_code_des =  result.get("err_code_des");
            log.error("WeChatAppPay payment error! parameter:[{}], Error cause == >>> Message:[{}]", signInfo,
                    err_code_des);
            throw new BaseException(RetCode.ERROR_WECHAT_ORDERS.getCode(), err_code_des);
        }
        return result.get("code_url");
    }

    @Override
    public String doOperation() {
        return "这是微信支付";
    }
}
