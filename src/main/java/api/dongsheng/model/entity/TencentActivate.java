package api.dongsheng.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author rx
 * @Description 渠道
 * @Date 2019/8/8 19:27
 **/
@Data
public class TencentActivate {

    /**
     * 身份唯一标识
     */
    private String passport;
    /**
     * 渠道id
     */
    private Long channelId;
    /**
     * 酷狗userid
     */
    private String kguserId;
    /**
     * token
     */
    private String token;
    /**
     * 设备唯一标识
     */
    private String sn;
    /**
     * 创建时间
     */
    private Date createTime;
}
