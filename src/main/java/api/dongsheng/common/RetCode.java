package api.dongsheng.common;

/**
 * @Author rx
 * @Description 返回对象编码
 * @Date 19:39 2019/7/8
 * @Param
 * @return
 **/
public enum RetCode {

    // 成功
    SUCCESS(0, "success"),
    //参数异常
    PARAM_ERROR(1001, "参数有误"),
    // token不匹配
    TOKEN_ERROR(1002, "签名验证失败"),
    // 没有当前API权限
    API_IS_NOT(1003, "没有当前API权限"),
    //渠道id错误
    CHANNEL_ID_ERROR(1004, "渠道有误"),
    //专辑不存在
    ALBUM_NOT_FOUNT(1005, "专辑不存在"),
    //音频不存在
    MUSIC_NOT_FOUNT(1005, "音频不存在"),
    //当前兑换码不存在
    CODE_NOT_FOUNT(1006, "当前 license 无效"),
    //当前兑换码已使用
    CODE_USED(1007, "当前 license 已经被使用"),
    //音频未购买
    AUDIO_NOT_PURCHASED(1008, "用户未使用license"),
    //用户imei超限
    USER_IMEI(1009, "用户imei超限"),
    // code已过期
    CODE_DUE(1010, "license已过期"),
    // license未绑定用户
    LICENSE_UNBOUND(1011,"license未绑定用户"),
    //不允许访问
    CHANNEL_NOT_ALLOW(1012, "不允许访问"),
    //设备未激活
    DEVICE_NO_ACTIVATE(1014, "设备未激活"),
    //设备未使用license
    DEVICE_NO_LICENSE(1015, "未使用 license"),
    //用户已绑定license
    USER_USE_LICENSE(1016, "用户已使用 license"),
    //未搜索到音频
    NO_AUDIO_FOUND(1017, "未搜索到音频"),
    // 超过最大查询数
    MAX_NUMBER(1018, "超过最大查询数"),
    // json 格式有误
    JSON_ERREO(1019, "json数据格式有误"),
    // 配额码不存在
    QUOTA_NOT_FOUND(1020, "会员码不存在"),
    // 当前配额码已被使用
    QUOTA_USED(1021, "当前会员码已被使用"),
    // 配额配额不存在，请联系商务
    QUOTA_LAPSE(1022, "渠道配额不存在，请联系商务"),
    // 配额已用完，请联系商务
    QUOTA_USE_UP(1023, "配额已用完，请联系商务"),
    // 设备已领取过会员
    DELICE_RECEIVE_MEMBER(1024, "设备已领取过会员"),
    // 渠道下产品不存在
    PRODUCT_NOT_FOUND(2001, "渠道下产品不存在"),
    //酷码已过期
    COOLCODE_EXPIRATION(200100, "酷码已过期"),
    //酷码已经被使用
    COOLCODE_USE(200102, "酷码已经被使用"),
    //酷码不存在
    COOLCODE_NO_FOUND(200103, "酷码不存在"),
    //服务器内部错误
    SERVER_ERROR(500, "服务器内部错误,请联系商务！"),
    // 请求支付宝失败！
    ERROR_ALIPAY_PAYMENT( 6000,"支付宝SDK异常！"),
    // 支付宝下单异常
    ERROR_ALIPAY_ORDERS(60001,"支付宝下单异常！"),
    // 微信下单异常
    ERROR_WECHAT_ORDERS(60002,"微信下单异常！"),
    // 支付金额异常
    ERROR_PAYMENT_AMOUNT(60003,"支付金额异常！"),
    // 支付金额异常
    GOODSID_NOT_FOUNT(60004,"商品id不存在！"),
    // 支付订单id不存在
    PAYMENTORDERID_NOT_FOUNT(60005,"支付订单id不存在！"),
    ;

    public int code;
    private String msg;

    RetCode(int code) {
        this.code = code;
    }

    RetCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}