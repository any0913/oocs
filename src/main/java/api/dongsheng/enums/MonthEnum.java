package api.dongsheng.enums;

/**
 * @program: content
 * @description: 月份枚举
 * @author: urbane
 * @create: 2020-03-11 17:14
 **/
public enum MonthEnum {
    /**
     * 一个月
     */
    ONE_MONTH(1,"一个月"),
    /**
     * 三个月
     */
    THREE_MONTHS(3,"三个月"),
    /**
     * 六个月
     */
    SIX_MONTHS(6,"六个月"),
    /**
     * 十二个月
     */
    TWELVE_MONTHS(12,"十二个月");

    private int month;

    private String explain;

    MonthEnum(int month, String explain) {
        this.month = month;
        this.explain = explain;
    }

    public int getMonth() {
        return month;
    }
}
