package api.dongsheng.model.vo;

import lombok.Data;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/7/29 9:05
 **/
@Data
public class MusicVO {

    /**
     * 音频ID
     */
    private Long id;
    /**
     * 专辑id
     */
    private Long albumId;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String intro;
    /**
     * 来源
     */
    private Integer sourceId;
    /**
     * 时长,单位秒
     */
    private Integer duration;
    /**
     * 音频大小kb
     */
    private Long fileSize;
    /**
     * 是否付费 1免费 2会员免费 3付费
     */
    private Integer isFree;
    /**
     * 是否同步订单,1不需要，2需要
     */
    private Integer syncOrder;
}
