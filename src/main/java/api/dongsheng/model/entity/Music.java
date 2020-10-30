package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/7/29 9:05
 **/
@Data
public class Music {


    private Long id;
    /**
     * 分类1音频，2视频，3电台
     */
    private Integer type;
    /**
     * 音频ID
     */
    private Long thirdId;
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
    private String intro = "";
    /**
     * 分类
     */
    private Integer categoryId;
    /**
     * 序号
     */
    private Integer orderNum = 0;
    /**
     * 音频大小kb
     */
    private Long fileSize = 0L;
    /**
     * 时长,单位秒
     */
    private Integer duration;
    /**
     * 封面地址
     */
    private String cover = "";
    /**
     * 来源1蜻蜓,2懒人
     */
    private Integer sourceId;
    /**
     * 是否允许试听
     */
    private Boolean isAudition = false;
    /**
     * 是否付费 1免费 2会员免费 3付费
     */
    private Integer isFree;

    /**
     * 播放地址
     */
    private String playUrl;

    /**
     * 电台节目开始时间
     */
    private String startTime;
    /**
     * 电台节目结束时间
     */
    private String endTime;
    /**
     * 周几，星期几
     */
    private Integer week;
}
