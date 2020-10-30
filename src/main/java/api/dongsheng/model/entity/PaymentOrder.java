package api.dongsheng.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * payment_order
 * @author 
 */
@Data
public class PaymentOrder implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 订单来源渠道
     */
    private Long channelId;

    /**
     * 订单来源渠道产品id
     */
    private Long productId;

    /**
     * 用户身份唯一标识
     */
    private String passport;

    /**
     * 付款用户ID
     */
    private String userId;

    /**
     * 用户真实IP
     */
    private String userIp;

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

    /**
     * 币种 0  RMB(人民币)，默认 0
     */
    private Byte currency;

    /**
     * 支付成功时间
     */
    private Date paymentTime;

    /**
     * 第三方订单号
     */
    private String thirdPartyOrder;

    /**
     * 下发权益，0：未下发，1：下发成功，2：下发失败
     */
    private Byte issuedRights;

    /**
     * 资源渠道
     */
    private String rspId;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}