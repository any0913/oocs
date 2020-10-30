package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @Author rx
 * @Description 渠道
 * @Date 2019/8/8 19:27
 **/
@Data
public class Channel {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 渠道id
     */
    private Long channelId;
    /**
     * 渠道名称
     */
    private String channelName;
    /**
     * 合作方式1买断,2分成
     */
    private Integer cooperateMode;
    /**
     * 渠道类型，1音响。2LOT
     */
    private Integer channelType;
    /**
     * resType类型，1只有酷狗，2没有酷狗，3综合资源
     */
    private Integer resType;

    /**
     * 修改license绑定用户的权限0没有，1有
     */
    private Integer modifyAuth;
    /**
     * 渠道状态
     */
    private Integer channelStatus;
}
