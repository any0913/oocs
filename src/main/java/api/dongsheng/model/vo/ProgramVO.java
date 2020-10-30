package api.dongsheng.model.vo;

import lombok.Data;

@Data
public class ProgramVO {


    /**
     * 节目id
     */
    private Long id;
    /**
     * 电台id
     */
    private Long radioId;
    /**
     * 标题
     */
    private String title;
    /**
     * 时长
     */
    private Integer duration;
    /**
     * 来源2蜻蜓早教，3蜻蜓成人，4懒人听书，5腾讯音乐
     */
    private Integer sourceId;
    /**
     * 周几，星期几
     */
    private Integer week;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
