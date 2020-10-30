package api.dongsheng.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author rx
 * @Description 访问记录
 * @Date 2019/8/8 19:27
 **/
@Data
public class VisitRecord {

    /**
     * 渠道id
     */
    private Long channelId;
    /**
     * 访问地址
     */
    private String visitUrl;
    /**
     * 专辑id
     */
    private Long albumId = 0L;
    /**
     * 音频id
     */
    private Long musicId = 0L;
    /**
     * 第三方用户凭证
     */
    private String passport = "";
    /**
     * 第三方用户凭证
     */
    private String deviceNo = "";
    /**
     * 第三方用户凭证
     */
    private String deviceIp = "";
    /**
     * imei
     */
    private String imei = "";
    /**
     * 访问ip
     */
    private String visitIp;
    /**
     * 访问状态1访问成功,2失败
     */
    private Integer visitStatus;
    /**
     * 访问说明
     */
    private String visitExplain;
    /**
     * 访问时间
     */
    private Date visitTime;
}
