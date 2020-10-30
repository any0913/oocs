package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @Author rx
 * @Description 渠道数据源
 * @Date 2019/8/8 11:11
 **/
@Data
public class ChannelData {

    /**
     * 合作方id
     */
    private Long channel_id;
    /**
     * 合作密匙
     */
    private Integer channel_source;
    /**
     * 数据类型0全部，1免费，2付费
     */
    private Integer data_type;
}
