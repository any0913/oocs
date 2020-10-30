package api.dongsheng.enums;

/**
 * @program: aioh_sw_pay
 * @description: 支付订单状态枚举
 * @author: urbane
 * @create: 2019-11-22 19:53
 **/
public enum OrderStatusEnum {
    /**
     * 订单待支付
     */
    ORDER_UNPAID( (byte)0),
    /**
     * 订单正在支付
     */
    ORDER_IN_THE_PAYMENT( (byte)1),
    /**
     * 订单支付成功
     */
    ORDER_PAYMENT_SUCCESS( (byte)2),
    /**
     * 订单支付失败
     */
    ORDER_PAYMENT_FAILURE( (byte)3),
    /**
     * 订单支付取消
     */
    ORDER_PAY_FOR_CANCELLATION( (byte)4);
    /**
     * 对应数据库状态
     */
    private byte code;

    OrderStatusEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
