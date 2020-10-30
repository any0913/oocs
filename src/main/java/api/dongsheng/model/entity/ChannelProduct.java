package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @Author rx
 * @Description 渠道产品
 * @Date 2020/8/22 11:11
 **/
@Data
public class ChannelProduct {

    /**
     * 主键
     */
    private Long id;
    /**
     * 渠道id
     */
    private Long channel_id;
    /**
     * 产品id
     */
    private Long product_id;
    /**
     * 产品名称
     */
    private String product_name;
    /**
     * 牛方案pid
     */
    private String tme_pid;
    /**
     * 牛方案key
     */
    private String tme_appkey;
    /**
     * 牛方案2.0会员直充签名key
     */
    private String tme_member_key;
}
