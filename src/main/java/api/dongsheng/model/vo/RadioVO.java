package api.dongsheng.model.vo;

import lombok.Data;

@Data
public class RadioVO {


    /**
     * 专辑id
     */
    private Long id;
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
     * 分类id
     */
    private Integer categoryId;
}
