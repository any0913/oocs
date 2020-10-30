package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.ChannelApi;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ChannelApiMapper {


    /**
     * 根据渠道id和url地址获取信息
     *
     * @param channelId
     * @param channelApi
     * @return
     */
    @Select("select * from channel_api where channel_id = #{channelId} and channel_api = #{channelApi}")
    ChannelApi getChanelApi(@Param("channelId") Long channelId, @Param("channelApi") String channelApi);

}
