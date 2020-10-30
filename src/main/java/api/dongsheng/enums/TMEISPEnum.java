package api.dongsheng.enums;

/**
 * @program: aioh_sw_content
 * @description: tme服务提供商枚举
 * @author: urbane
 * @create: 2020-03-10 11:09
 **/
public enum TMEISPEnum {
    /**
     * 酷狗
     */
    KG("KG"),
    /**
     * 酷我
     */
    KW("KW"),
    /**
     * Q音
     */
    QM("QM");
    public String ISP;



    TMEISPEnum(String ISP) {
        this.ISP = ISP;
    }

    public String getISP() {
        return ISP;
    }

    public static String getSp(String sp){
        if (sp != null) {
            for (TMEISPEnum tmeispEnum : TMEISPEnum.values()) {
                if (tmeispEnum.getISP().equals(sp)) {
                    return tmeispEnum.getISP();
                }
            }
        }
        return null;
    }
}
