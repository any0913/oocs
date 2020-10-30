package api.dongsheng.enums;

/**
 * @program: aioh_sw_pay
 * @description: 微信支付类型枚举
 * @author: urbane
 * @create: 2019-11-22 19:40
 **/
public enum WeChatTradeTypeEnum {
    /**
     * 微信公众号支付或者小程序支付
     */
    JSAPI("JSAPI"),
    /**
     * 微信扫码支付
     */
    NATIVE("NATIVE"),
    /**
     * 微信APP支付
     */
    APP("APP"),
    /**
     * 付款码支付
     */
    MICROPAY("MICROPAY"),
    /**
     * H5支付
     */
    MWEB("MWEB");

    WeChatTradeTypeEnum(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 交易类型
     */
    private final String tradeType;

    public String getTradeType() {
        return tradeType;
    }
}
