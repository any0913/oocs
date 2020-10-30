package api.dongsheng.model.mapper;

import api.dongsheng.model.entity.PaymentOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PaymentOrderMapper {

    int insert(PaymentOrder record);

    int insertSelective(PaymentOrder record);

    PaymentOrder selectByPrimaryKey(Long id);

    /**
     * 根据支付订单ID查询订单信息
     *
     * @param paymentOrderId
     * @return api.dongsheng.model.entity.PaymentOrder
     * @title selectByPaymentOrderId
     * @description
     * @author urbane
     * @updateTime 2020/4/20  13:50
     */
    PaymentOrder selectByPaymentOrderId(@Param("paymentOrderId") String paymentOrderId);

    /**
     * 根据渠道和设备id查询是否领取过激活赠送会员
     * @param map
     * @return
     */
    PaymentOrder selectPaymentOrderByDeviceId(Map<String,Object> map);

    int updateByPrimaryKeySelective(PaymentOrder record);

    int updateByPrimaryKey(PaymentOrder record);

    /**
     * 获取每天赠送会员数
     * @return
     */
    Integer selectGiveMemberNum();

    /**
     * 获取每天各支付方式的支付总金额
     * @return
     */
    List<PaymentOrder> selectPaymentTotalMoney();
}