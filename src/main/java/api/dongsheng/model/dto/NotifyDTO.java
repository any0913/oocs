package api.dongsheng.model.dto;


import api.dongsheng.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * @program: aioh_sw_payment
 * @description: 异步通知参数
 * @author: MichelleJou
 * @create: 2019-12-11 14:29
 **/
@Setter
@Getter
public class NotifyDTO {

    /**
     * 支付系统订单号
     */
    private String paymentOrderId;
   /**
     * 支付金额，单位（分）
     */
    private Integer paymentAmount;;
    /**
     * 支付状态 0  待支付，1 支付中 ，2  支付成功，3 支付失败，4  取消支付
     */
    private Byte paymentStatus;
    /**
     * 第三方订单号
     */
    private String thirdPartyOrder;

    /**
     * 10 : 支付宝扫码支付，20：微信扫码支付
     */
    private Byte payType;


    /**
     * 第三方回调信息
     */
    private String thirdPartyNotification;

    @Override
    @SneakyThrows
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
