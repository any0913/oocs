package api.dongsheng.enums;

/**
 * @program: aioh_sw_payment
 * @description: 支付宝返回交易状态枚举
 * @author: urbane
 * @create: 2019-12-10 13:58
 **/
public enum AlipayTradeStatusEnum {
    /**
     * 交易创建，等待买家付款
     */
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", (byte)1),
    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    TRADE_CLOSED("TRADE_CLOSED", (byte)4),
    /**
     * 交易支付成功
     */
    TRADE_SUCCESS("TRADE_SUCCESS", (byte)2),
    /**
     * 交易结束，不可退款（此处的支付平台状态暂时未使用,后续有退款业务此状态标记不可退）
     */
    TRADE_FINISHED("TRADE_FINISHED", (byte)6);

    AlipayTradeStatusEnum(String tradeStatus, byte status) {
        this.tradeStatus = tradeStatus;
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }


    /**
     * 支付宝交易状态
     */
    private final String tradeStatus;
    /**
     * 支付平台交易状态
     */
    private final byte status;


    public static int getStatus(String tradeStatus) {
        if (tradeStatus != null) {
            for (AlipayTradeStatusEnum alipayTradeStatusEnum : AlipayTradeStatusEnum.values()) {
                if (alipayTradeStatusEnum.getTradeStatus().equals(tradeStatus)) {
                    return alipayTradeStatusEnum.getStatus();
                }
            }
        }
        return AlipayTradeStatusEnum.WAIT_BUYER_PAY.getStatus();
    }
}
