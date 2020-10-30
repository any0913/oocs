package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.Orders;

import java.util.Map;

public interface OrdersMapper {

    /**
     * 查询订单信息
     *
     * @param params
     * @return
     */
    Orders findOrder(Map<String, Object> params);

    /**
     * 同步订单
     *
     * @param order 订单对象
     * @return
     */
    void syncOrder(Orders order);

    /**
     * 根据渠道和订单查询是否存在
     *
     * @param order
     * @return
     */
    Orders findOrderByNo(Orders order);

    /**
     * 查询当前单曲是否同步
     *
     * @param params
     * @return
     */
    Orders findOrderByMusicId(Map<String, Object> params);
}
