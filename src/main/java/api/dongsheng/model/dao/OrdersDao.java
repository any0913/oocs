package api.dongsheng.model.dao;

import api.dongsheng.model.mapper.OrdersMapper;
import api.dongsheng.model.entity.Orders;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/9 14:20
 **/
@Repository
public class OrdersDao {

    @Resource
    private OrdersMapper ordersMapper;


    /**
     * 查询订单
     *
     * @param params
     * @return
     */
    public Orders findOrder(Map<String, Object> params) {
        return ordersMapper.findOrder(params);
    }

    /**
     * 根据渠道和订单查询是否存在
     *
     * @param order
     * @return
     */
    public Orders findOrderByNo(Orders order) {
        return ordersMapper.findOrderByNo(order);
    }

    /**
     * 同步订单
     *
     * @param order
     */
    public void syncOrder(Orders order) {
        ordersMapper.syncOrder(order);
    }

    /**
     * 查询当前单曲是否同步
     *
     * @param params
     * @return
     */
    public Orders findOrderByMusicId(Map<String, Object> params) {
        return ordersMapper.findOrderByMusicId(params);
    }
}
