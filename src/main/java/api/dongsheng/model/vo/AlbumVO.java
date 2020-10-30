package api.dongsheng.model.vo;

import lombok.Data;

@Data
public class AlbumVO {


    /**
     * 专辑id
     */
    private Long albumId;
    /**
     * 标题
     */
    private String title;
    /**
     * 书籍简介
     */
    private String intro;
    /**
     * 主播
     */
    private String announcer;
    /**
     * 封面地址
     */
    private String cover;
    /**
     * 来源
     */
    private Integer sourceId;
    /**
     * 书籍状态 1完结 2未完结',
     */
    private Integer isFinished;
    /**
     * 分类id,小类
     */
    private Integer categoryId;
    /**
     * 总音频数
     */
    private Integer musicCount;
    /**
     * 价格（单位分）
     */
    private Integer price;
    /**
     * 是否付费 1免费 2会员免费 3付费
     */
    private Integer isFree;
    /**
     * 是否同步订单,1不需要，2需要
     */
    private Integer syncOrder;
}
