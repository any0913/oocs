package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.ChannelAuth;
import org.apache.ibatis.annotations.Select;

public interface ChannelAuthMapper {


    /**
     * 根据渠道id获取渠道授权信息
     *
     * @param channelId
     * @return
     */
    @Select("select a.* from channel c left JOIN channel_auth a on c.channel_id = a.channel_id where c.channel_status = 0 and c.channel_id = #{channelId}")
    ChannelAuth getChannelAuth(Long channelId);
}
