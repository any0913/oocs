package api.dongsheng.model.entity;

import lombok.Data;

/**
 * @Author rx
 * @Description 渠道分类
 * @Date 2019/8/8 19:27
 **/
@Data
public class ChannelCategory {


    /**
     * 来源id
     */
    private Integer sourceId;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 分类名称
     */
    private String categoryName;

}
