package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.ChannelData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChannelDataMapper {


    /**
     * 　根据渠道获取渠道资源类型
     *
     * @param channelId
     * @return
     */
    @Select("select * from channel_data where channel_id = #{channelId}")
    List<ChannelData> findChannelDataList(Long channelId);
}
