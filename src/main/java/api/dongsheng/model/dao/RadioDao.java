package api.dongsheng.model.dao;


import api.dongsheng.model.entity.Radio;
import api.dongsheng.model.mapper.RadioMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:18
 **/
@Repository
public class RadioDao {

    @Resource
    private RadioMapper radioMapper;

    /**
     * 根据分类获取电台
     * @param map
     * @return
     */
    public List<Radio> getRadio(Map<String, Object> map) {
        return radioMapper.getRadio(map);
    }

    /**
     * 根据电台id查询电台信息
     * @param params
     * @return
     */
    public Radio getRadioById(Map<String, Object> params) {
        return radioMapper.getRadioById(params);
    }

    /**
     * 根据分类获取电台
     * @param categoryId
     * @return
     */
    public List<Integer> getRadioList(Integer categoryId) {
        return radioMapper.getRadioList(categoryId);
    }
}
