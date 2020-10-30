package api.dongsheng.model.entity;

import lombok.Data;

@Data
public class Album {


    /**
     * 主键
     */
    private Long id;
    /**
     * 专辑id
     */
    private Long thirdId;
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
    private String announcer = "";
    /**
     * 封面地址
     */
    private String cover;
    /**
     * 书籍状态 1完结 2未完结',
     */
    private Integer isFinished;
    /**
     * 分类id,小类
     */
    private Integer categoryId;
    /**
     * 父类id
     */
    private Integer parentId;
    /**
     * 总音频数
     */
    private Integer musicCount;
    /**
     * 收费类型 0免费 1整本 2按章收费 3订阅（可以当做整本收费来处理）
     */
    private Integer payType;
    /**
     * 价格（单位分）
     */
    private Integer price;
    /**
     * 来源1蜻蜓,4懒人
     */
    private Integer sourceId;
    /**
     * 是否付费 1免费 2会员免费 3付费
     */
    private Integer isFree;
    /**
     * 是否同步订单,1不需要，2需要
     */
    private Integer syncOrder;

}
