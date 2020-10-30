package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.Radio;

import java.util.List;
import java.util.Map;

public interface RadioMapper {


    /**
     * 根据分类获取电台
     * @param map
     * @return
     */
    List<Radio> getRadio(Map<String, Object> map);

    /**
     * 根据电台id查询电台
     * @param params
     * @return
     */
    Radio getRadioById(Map<String, Object> params);

    /**
     * 根据分类获取电台
     * @param categoryId
     * @return
     */
    List<Integer> getRadioList(Integer categoryId);
}
