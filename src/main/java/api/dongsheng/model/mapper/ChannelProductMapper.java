package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.ChannelProduct;

public interface ChannelProductMapper {


    /**
     * 获取渠道产品对应的产品信息
     * @param channelProduct
     * @return
     */
    ChannelProduct selectChannelProduct(ChannelProduct channelProduct);
}
