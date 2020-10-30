package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.Channel;
import api.dongsheng.model.entity.VisitRecord;

import java.util.List;

public interface ChannelMapper {


    /**
     * 获取所有渠道
     *
     * @return
     */
    List<Channel> findChannelList();

    /**
     * 根据渠道id获取渠道信息
     *
     * @param channelId
     * @return
     */
    Channel getChannel(Long channelId);

    /**
     * 新增访问记录
     *
     * @param record
     */
    void inserVisitRecord(VisitRecord record);
}
