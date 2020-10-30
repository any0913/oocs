package api.dongsheng.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Radio {


    /**
     * 专辑id
     */
    private Long id;
    /**
     * 第三方专辑id
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
     * 封面地址
     */
    private String cover;
    /**
     * 来源2蜻蜓早教，3蜻蜓生态，4懒人听书，5腾讯音乐
     */
    private Integer sourceId;
    /**
     * 收费，1免费，3付费
     */
    private Integer isFree;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
