package api.dongsheng.model.vo;

import lombok.Data;

/**
 * @program: aioh_sw_oocs
 * @description: 订单信息返回vo
 * @author: urbane
 * @create: 2020-04-18 11:26
 **/
@Data
public class PaymentOrderReqVO {


    /**
     * 用户身份唯一标识
     */
    private String passport;

    /**
     * 付款用户ID
     */
    private String userId;

    /**
     * 设备ID，暂存数据用于牛云方案会员权益激活
     */
    private String deviceId;

    /**
     * 用户token，暂存数据用于牛云方案会员权益激活
     */
    private String token;

    /**
     * 服务提供商 "KG" "KW" "QM";分别为 酷狗,酷我,Q音，暂存数据用于牛云方案会员权益激活
     */
    private String sp;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 订单金额， 单位（分）
     */
    private Integer orderAmount;

    /**
     * 支付订单ID
     */
    private String paymentOrderId;

    /**
     * 支付金额，单位（分）
     */
    private Integer paymentAmount;

    /**
     * 支付状态 0  待支付，1 支付中 ，2  支付成功，3 支付失败，4  取消支付，默认 0
     */
    private Byte paymentStatus;

    /**
     * 10 : 支付宝扫码支付，20：微信扫码支付
     */
    private Byte payType;
}
