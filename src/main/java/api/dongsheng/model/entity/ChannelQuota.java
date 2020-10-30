package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @Author rx
 * @Description 渠道配额
 * @Date 2020/8/22 11:11
 **/
@Data
public class ChannelQuota {

    /**
     * 主键
     */
    private Long id;
    /**
     * 渠道id
     */
    private Long channel_id;
    /**
     * 设备类型
     */
    private String device_type;
    /**
     * 渠道配额（数量）
     */
    private Integer channel_quota;
    /**
     * 配额模式
     */
    private Integer quota_mode;
    /**
     * 单位，1天，2月
     */
    private Integer quota_unit;
    /**
     * 每个配额对应的价格
     */
    private Integer quota_money;
    /**
     * 状态
     */
    private Integer quota_status;
}
