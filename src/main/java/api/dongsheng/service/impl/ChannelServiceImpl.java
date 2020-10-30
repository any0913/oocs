package api.dongsheng.service.impl;

import api.dongsheng.model.dao.ChannelDao;
import api.dongsheng.service.ChannelService;
import api.dongsheng.model.entity.ChannelApi;
import api.dongsheng.model.entity.ChannelAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:16
 **/
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelDao channelDao;

    /**
     * 根据渠道id获取渠道授权信息
     *
     * @param channelId
     * @return
     */
    @Override
    public ChannelAuth getChannelAuth(Long channelId) {
        return channelDao.getChannelAuth(channelId);
    }

    /**
     * 根据渠道id和渠道api查询
     *
     * @param channelId
     * @param channelApi
     * @return
     */
    @Override
    public ChannelApi getChanelApi(Long channelId, String channelApi) {
        return channelDao.getChanelApi(channelId, channelApi);
    }
}
