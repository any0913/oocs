package api.dongsheng.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * membership
 * @author 
 */
@Data
public class Membership implements Serializable {
    /**
     * 自增id
     */
    private Long id;

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
     * 商品渠道 指该商品应用于哪个下游渠道
     */
    private Long membershipChannel;

    /**
     * 商品平台
     */
    private String membershipPlatform;

    /**
     * 商品状态，0：待售状态；1：上架即处于可售状态。
     */
    private Byte status;

    /**
     * 商品折扣 预留字段
     */
    private String discount;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    private static final long serialVersionUID = 1L;


}