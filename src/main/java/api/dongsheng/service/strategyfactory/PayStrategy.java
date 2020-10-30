package api.dongsheng.service.strategyfactory;

import api.dongsheng.model.dto.PayOrderStatusDTO;

/**
 * @program: aioh_sw_payment
 * @description: 支付类型策略
 * @author: urbane
 * @create: 2020-01-03 15:14
 **/
public interface PayStrategy {

    /**
     * @param payOrderId 支付订单ID
     * @param orderPrice 订单金额
     * @param ip         用户真实IP
     * @param goodsName  商品名称
     * @return java.lang.String    生成二维码的字符串
     * @title scanCodePay
     * @description
     * @author urbane
     * @updateTime 2020/4/16  17:16
     */
    String scanCodePay(String payOrderId, int orderPrice, String ip, String goodsName);



    /**
     * 主动去第三方查询支付状态
     *
     * @param payOrderId 支付订单ID
     * @return
     */
    PayOrderStatusDTO getOrderStatus(String payOrderId);


    /**
     * 支付中签约
     * @param payOrderId 支付订单ID
     * @return
     */
    String contractOrder(String payOrderId, int orderPrice, String ip, String goodsName);

    String doOperation();
}
