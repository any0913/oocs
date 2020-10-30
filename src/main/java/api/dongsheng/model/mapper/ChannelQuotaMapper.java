package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.ChannelQuota;

public interface ChannelQuotaMapper {


    /**
     * 根据渠道id查询渠道对应的配额
     * @param channelId
     * @return
     */
    ChannelQuota selectChannelQuota(Long channelId);

    /**
     * 修改当前配额额度
     * @param channelQuota
     */
    void updateChannelQuota(ChannelQuota channelQuota);
}
