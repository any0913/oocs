package api.dongsheng.service;

import api.dongsheng.model.entity.ChannelApi;
import api.dongsheng.model.entity.ChannelAuth;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:16
 **/
public interface ChannelService {

    /**
     * 根据渠道id获取渠道授权信息
     *
     * @param channelId
     * @return
     */
    ChannelAuth getChannelAuth(Long channelId);

    /**
     * 根据渠道idh和api接口查询
     *
     * @param channelId
     * @param channelApi
     * @return
     */
    ChannelApi getChanelApi(Long channelId, String channelApi);
}
