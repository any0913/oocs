package api.dongsheng.model.vo;

import lombok.Data;

/**
 * @program: aioh_sw_oocs
 * @description: 下单返回vo
 * @author: urbane
 * @create: 2020-04-18 11:55
 **/
@Data
public class OrderRespVO {
    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String membershipName;

    /**
     * 商品规格，单位（月）；示例：3(3个月)
     */
    private Integer membershipNorms;

    /**
     * 订单金额， 单位（分）
     */
    private Integer orderAmount;

    /**
     * 支付订单ID
     */
    private String paymentOrderId;

    /**
     * 支付宝支付二维码生成链接
     */
    private String alipayCodeUrl;

    /**
     * 微信支付二维码生成链接
     */
    private String weChaCodeUrl;

    public OrderRespVO() {
    }

    public OrderRespVO(String goodsId, String membershipName, Integer membershipNorms, Integer orderAmount,
                       String paymentOrderId, String alipayCodeUrl, String weChaCodeUrl) {
        this.goodsId = goodsId;
        this.membershipName = membershipName;
        this.membershipNorms = membershipNorms;
        this.orderAmount = orderAmount;
        this.paymentOrderId = paymentOrderId;
        this.alipayCodeUrl = alipayCodeUrl;
        this.weChaCodeUrl = weChaCodeUrl;
    }


}
