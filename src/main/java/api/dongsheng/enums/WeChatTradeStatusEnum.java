package api.dongsheng.enums;

/**
 * @program: aioh_sw_payment
 * @description: 微信支付状态枚举
 * @author: urbane
 * @create: 2019-12-10 15:48
 **/
public enum WeChatTradeStatusEnum {
    /**
     * 用户支付中
     */
    USERPAYING("USERPAYING", (byte)1),
    /**
     * 支付成功
     */
    SUCCESS("SUCCESS", (byte)2),
    /**
     * 转入退款
     */
    REFUND("REFUND", (byte)12),
    /**
     * 未支付
     */
    NOTPAY("NOTPAY", (byte)1),
    /**
     * 已关闭
     */
    CLOSED("CLOSED", (byte)8),
    /**
     * 已撤销（刷卡支付）
     */
    REVOKED("REVOKED", (byte)9),
    /**
     * 支付失败(其他原因，如银行返回失败)
     */
    PAYERROR("PAYERROR", (byte)3);

    WeChatTradeStatusEnum(String tradeState, byte status) {
        this.tradeState = tradeState;
        this.status = status;
    }

    public String getTradeState() {
        return tradeState;
    }

    public byte getStatus() {
        return status;
    }

    /**
     * 微信支付状态
     */
    private final String tradeState;
    /**
     * 支付平台交易状态
     */
    private final byte status;

    public static Byte getStatus(String tradeState) {
        if (tradeState != null) {
            for (WeChatTradeStatusEnum weChatTradeStatusEnum : WeChatTradeStatusEnum.values()) {
                if (weChatTradeStatusEnum.getTradeState().equals(tradeState)) {
                    return weChatTradeStatusEnum.getStatus();
                }
            }
        }
        return null;
    }
}
