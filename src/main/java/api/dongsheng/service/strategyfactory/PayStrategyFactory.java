package api.dongsheng.service.strategyfactory;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: aioh_sw_oocs
 * @description: 支付策略工厂
 * @author: urbane
 * @create: 2020-04-16 15:56
 **/
@Service
public class PayStrategyFactory {

    @Autowired
    Map<String, PayStrategy> payTypeStrategyMap = Maps.newHashMapWithExpectedSize(3);

    public PayStrategy getPayStrategy(String component) throws Exception{
        PayStrategy payStrategy = payTypeStrategyMap.get(component);
        if (payStrategy == null) {
            throw new RuntimeException("no strategy defined >>> " + component);
        }
        return payStrategy;
    }
}
