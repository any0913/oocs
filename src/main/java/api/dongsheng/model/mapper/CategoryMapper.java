package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.ChannelCategory;
import api.dongsheng.model.entity.ChannelData;
import java.util.List;
import java.util.Map;

public interface CategoryMapper {


    /**
     * 根据渠道id和分类id查询信息
     *
     * @param params
     * @return
     */
    ChannelCategory getChannelCategory(Map<String, Object> params);

    /**
     * 根据渠道id获取当前渠道下的分类
     *
     * @param map
     * @return
     */
    List<ChannelCategory> findChannelCategoryList(Map<String, Object> map);

    /**
     * 根据来源获取渠道对应的数据权限
     * @param map
     * @return
     */
    ChannelData getChannelDataBySourceId(Map<String, Object> map);
}
