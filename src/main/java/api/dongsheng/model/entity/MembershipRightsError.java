package api.dongsheng.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * membership_rights_error
 * @author 
 */
@Data
public class MembershipRightsError implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 对应的支付订单
     */
    private String paymentOrderId;

    /**
     * 请求激活信息
     */
    private String requestingInformation;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}