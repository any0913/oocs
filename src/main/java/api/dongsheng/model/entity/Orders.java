package api.dongsheng.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/9 14:22
 **/
@Data
public class Orders {

    private Long id;
    private Long channelId;
    private Integer os;
    private String passport;
    private String orderNo;
    private Integer orderType;
    private Integer goodsType;
    private Long albumId;
    private String musicId;
    private Integer originalFee;
    private Integer totalFee;
    private Long payTime;
    private Integer quantity;
    private String remark;
    private Integer status;
    private Date createTime;
}
