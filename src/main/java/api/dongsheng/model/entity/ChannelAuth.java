package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @Author rx
 * @Description 渠道认证
 * @Date 2019/8/8 11:11
 **/
@Data
public class ChannelAuth {

    /**
     * 合作方id
     */
    private Long channel_id;
    /**
     * 合作密匙
     */
    private String channel_key;
}
