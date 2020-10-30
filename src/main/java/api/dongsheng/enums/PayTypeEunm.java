package api.dongsheng.enums;

/**
 * @program: aioh_sw_pay
 * @description: 支付类型枚举
 * @author: urbane
 * @create: 2019-11-22 19:40
 **/
public enum PayTypeEunm {
    /**
     * 支付宝扫码支付
     */
    ALIPAY_SCANCODE_PAY( (byte) 10, "Alipay"),
    /**
     * 微信扫码支付
     */
    WECHAT_SCANCODE_PAY( (byte) 20, "WeChat");


    /**
     * 支付类型code
     */
    private byte payTypeCode;
    /**
     * 支付类型策略key
     */
    private String PayStrategyKey;

    PayTypeEunm(byte payTypeCode, String payStrategyKey) {
        this.payTypeCode = payTypeCode;
        PayStrategyKey = payStrategyKey;
    }

    public byte getPayTypeCode() {
        return payTypeCode;
    }

    public String getPayStrategyKey() {
        return PayStrategyKey;
    }

    public static PayTypeEunm getByCode(int code) {
        if (code > -1) {
            for (PayTypeEunm status : PayTypeEunm.values()) {
                if (status.getPayTypeCode() == code) {
                    return status;
                }
            }
        }
        return null;
    }

}
