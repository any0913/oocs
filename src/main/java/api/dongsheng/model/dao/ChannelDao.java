package api.dongsheng.model.dao;

import api.dongsheng.model.mapper.*;
import api.dongsheng.model.entity.*;
import com.sun.corba.se.spi.ior.ObjectKey;
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
public class ChannelDao {


    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private ChannelAuthMapper authMapper;
    @Resource
    private ChannelApiMapper apiMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ChannelDataMapper channelDataMapper;


    /**
     * 获取所有渠道
     *
     * @return
     */
    public List<Channel> findChannelList() {
        return channelMapper.findChannelList();
    }

    /**
     * 根据渠道id获取渠道授权信息
     *
     * @param channelId
     * @return
     */
    public ChannelAuth getChannelAuth(Long channelId) {
        return authMapper.getChannelAuth(channelId);
    }

    /**
     * 根据渠道id和渠道api查询
     *
     * @param channelId
     * @param channelApi
     * @return
     */
    public ChannelApi getChanelApi(Long channelId, String channelApi) {
        return apiMapper.getChanelApi(channelId, channelApi);
    }

    /**
     * 根据渠道id渠道信息
     *
     * @param channelId
     * @return
     */
    public Channel getChannel(Long channelId) {
        return channelMapper.getChannel(channelId);
    }


    /**
     * 根据渠道id和分类id查询信息
     *
     * @param   params
     * @return
     */
    public ChannelCategory getChannelCategory(Map<String, Object> params) {
        return categoryMapper.getChannelCategory(params);
    }

    /**
     * 新增访问记录
     *
     * @param record
     */
    public void inserVisitRecord(VisitRecord record) {
        channelMapper.inserVisitRecord(record);
    }


    /**
     * 根据渠道id获取当前渠道下的分类
     *
     * @param map
     * @return
     */
    public List<ChannelCategory> findChannelCategoryList(Map<String, Object> map) {
        return categoryMapper.findChannelCategoryList(map);
    }

    /**
     * 根据来源获取当前渠道的数据
     *
     * @param map
     * @return
     */
    public ChannelData getChannelDataBySourceId(Map<String, Object> map) {
        return categoryMapper.getChannelDataBySourceId(map);
    }
}
