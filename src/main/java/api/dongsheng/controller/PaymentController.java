package api.dongsheng.controller;

import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.config.AlipayConfig;
import api.dongsheng.config.WeChatConfig;
import api.dongsheng.enums.AlipayTradeStatusEnum;
import api.dongsheng.enums.PayTypeEunm;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.dto.NotifyDTO;
import api.dongsheng.model.vo.OrderRespVO;
import api.dongsheng.model.vo.PaymentOrderReqVO;
import api.dongsheng.model.vo.PlaceOrderReqVO;
import api.dongsheng.service.PayOrdersService;
import api.dongsheng.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: aioh_sw_oocs
 * @description:
 * @author: urbane
 * @create: 2020-04-16 19:13
 **/
@Slf4j
@RestController(value = "paymaentColler")
@RequestMapping(value = "/v2/open/ocs/payment")
public class PaymentController {
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private PayOrdersService payOrdersService;

    @PostMapping(value = "/unifiedOrder")
    public RetResult unifiedOrder(@Valid @RequestBody PlaceOrderReqVO placeOrderReqVO, HttpServletRequest request) throws Exception {
        // 从请求头中获取渠道ID
        String channelId = request.getHeader(StringUtil.CHANNELID);
        String productId = request.getHeader(StringUtil.PRODUCTID);
        if(StringUtils.isEmpty(productId)){
            throw new BaseException(RetCode.PRODUCT_NOT_FOUND.getCode(),RetCode.PRODUCT_NOT_FOUND.getMsg());
        }
        String ip = StringUtil.getIpAddress(request);
        OrderRespVO orderRespVO = payOrdersService.unifiedOrder(placeOrderReqVO, ip, channelId,productId);
        return new RetResult(orderRespVO);
    }

    /**
     * @param request
     * @return java.lang.String
     * @title alipayNotify
     * @description 支付宝支付状态异步通知
     * @author urbane
     * @updateTime 2019/12/9  17:27
     */
    @RequestMapping(value = "/notify/alipay", method = RequestMethod.POST)
    public String alipayNotify(HttpServletRequest request) {
        try {
            //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            log.info("CallBackNotifyController alipayNotify parameter:{}", params);
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean flag = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), "UTF-8","RSA2");
            if (flag) {
                // 验参成功处理后续业务逻辑
                NotifyDTO notifyDTO = new NotifyDTO();
                //  交易的订单金额，单位为元，两位小数。
                String totalAmount = params.get("total_amount");
                double amount = Double.valueOf(totalAmount.toString());
                // 支付平台金额单位为分
                int price = (int) (amount * 100);
                //  交易状态
                int status = AlipayTradeStatusEnum.getStatus(params.get("trade_status"));
                notifyDTO.setPaymentAmount(price);
                notifyDTO.setPaymentStatus((byte) status);
                notifyDTO.setPaymentOrderId(params.get("out_trade_no"));
                notifyDTO.setThirdPartyOrder(params.get("trade_no"));
                String jsonString = JSON.toJSONString(params);
                notifyDTO.setThirdPartyNotification(jsonString);
                // 处理异步通知
                notifyDTO.setPayType(PayTypeEunm.ALIPAY_SCANCODE_PAY.getPayTypeCode());
                payOrdersService.callbackNotify(notifyDTO);
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception e) {
            log.error("CallBackNotifyController alipayNotify Error cause ==>> {}", e);
            return "failure";
        }
    }


    /**
     * @param request
     * @return java.lang.String
     * @title wechatNotify
     * @description 微信支付异步通知
     * @author urbane
     * @updateTime 2019/12/11  14:46
     */
    @PostMapping("/notify/wechat")
    public String wechatNotify(HttpServletRequest request) {
        String xmlMsg = HttpKit.readData(request);
        log.info("CallBackNotifyController wechatNotify parameter:[{}]", xmlMsg);
        Map<String, String> paramsMap = WxPayKit.xmlToMap(xmlMsg);
        String appid = paramsMap.get("appid");
        Map<String, String> xml = new HashMap<String, String>(2);
        boolean verifySign = false;
        if (weChatConfig.getAppId().equals(appid)) {
            verifySign = WxPayKit.verifyNotify(paramsMap, weChatConfig.getKey(), SignType.MD5);
        } else {
            xml.put("return_code", "FAIL");
            xml.put("return_msg", "sign error.");
            return WxPayKit.toXml(xml);
        }
        if (verifySign) {
            //  验参成功处理具体后续业务
            NotifyDTO notifyDTO = new NotifyDTO();
            // 现金支付金额,微信是分
            String cashFee = paramsMap.get("cash_fee");
            int price = Integer.parseInt(cashFee);
            // 交易状态
            // Byte status = WeChatTradeStatusEnum.getStatus(paramsMap.get("trade_state"));
            // notifyDTO.setPaymentStatus(status);
            notifyDTO.setPaymentAmount(price);
            //  商户订单号
            notifyDTO.setPaymentOrderId(paramsMap.get("out_trade_no"));
            //  微信支付订单号
            notifyDTO.setThirdPartyOrder(paramsMap.get("transaction_id"));
            String jsonString = JSON.toJSONString(paramsMap);
            notifyDTO.setThirdPartyNotification(jsonString);
            // 处理异步通知
            try {
                notifyDTO.setPayType(PayTypeEunm.WECHAT_SCANCODE_PAY.getPayTypeCode());
                payOrdersService.callbackNotify(notifyDTO);
            } catch (Exception e) {
                log.error("CallBackNotifyController wechatNotify Error cause ==>> {}", e);
                xml.put("return_code", "FAIL");
                xml.put("return_msg", "sign error.");
            }
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
        } else {
            log.error("CallBackNotifyController wechatNotify Error cause ==>>Attestation of failure,parameter:[{}]",
                    xmlMsg);
            xml.put("return_code", "FAIL");
            xml.put("return_msg", "sign error.");
        }
        return WxPayKit.toXml(xml);
    }

    /**
     * 获取订单信息
     * @param json
     * @return
     */
    @PostMapping(value = "/getOrderById")
    public RetResult getOrderById(@RequestBody JSONObject json,HttpServletRequest request){
        String paymentOrderId = json.getString("paymentOrderId");
        PaymentOrderReqVO paymentOrderReqVO = payOrdersService.selectPaymentOrderById(paymentOrderId);
        return new RetResult().setData(paymentOrderReqVO);
    }
}
