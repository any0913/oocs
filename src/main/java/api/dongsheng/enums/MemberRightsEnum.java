package api.dongsheng.enums;

/**
 * @program: aioh_sw_oocs
 * @description: 会员权益
 * @author: urbane
 * @create: 2020-04-22 10:04
 **/
public enum MemberRightsEnum {
    /**
     * 未发放
     */
    NOT_TO_ISSUE((byte)0),
    /**
     * 放成功
     */
    ISSUE_SUCCESS((byte)1),
    /**
     * 发放失败
     */
    ISSUE_FALL((byte)2);

    /**
     * 发放状态
     */
    private  Byte issuedRights;

    MemberRightsEnum(Byte issuedRights) {
        this.issuedRights = issuedRights;
    }

    public Byte getIssuedRights() {
        return issuedRights;
    }
}
