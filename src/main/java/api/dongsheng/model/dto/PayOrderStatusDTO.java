package api.dongsheng.model.dto;


import api.dongsheng.util.JsonUtil;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * @program: aioh_sw_payment
 * @description: 第三方查询订单支付状态返回信息
 * @author: urbane
 * @create: 2019-12-10 13:42
 **/

@Data
public class PayOrderStatusDTO {


    /**
     * 支付系统订单号
     */
    private String payOrderId;


    /**
     * 支付金额，单位（分）
     */
    private Integer payPrice;

    /**
     * 支付状态 0  待支付，1 支付中 ，2  支付成功，3 支付失败，4  取消支付
     */
    private int status;

    /**
     * 第三方订单号
     */
    private String thirdPartyOrder;

    @Override
    @SneakyThrows
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
