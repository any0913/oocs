package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @program: aioh_sw_oocs
 * @description: 会员信息
 * @author: urbane
 * @create: 2020-04-18 10:52
 **/
@Data
public class Member {
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
     * 商品原价，单位（分）
     */
    private Integer costPrice;

    /**
     * 商品现价，单位（分）
     */
    private Integer rulingPrice;

    /**
     * 是否主要推荐，0：不主推，1：主推
     */
    private Byte isMainPush;

    /**
     * 商品折扣 预留字段
     */
    private String discount;
}
