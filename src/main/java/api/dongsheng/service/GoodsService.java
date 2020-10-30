package api.dongsheng.service;

import api.dongsheng.model.entity.Member;

import java.util.List;

/**
 * @program: aioh_sw_oocs
 * @description: 商品相关处理
 * @author: urbane
 * @create: 2020-04-17 17:19
 **/
public interface GoodsService {

    /**
     * 查询会员列表
     *
     * @param sp        服务提供商
     * @param channelId 下游渠道ID
     * @return java.util.List<api.dongsheng.model.entity.Member>
     * @title getMemberList
     * @description
     * @author urbane
     * @updateTime 2020/4/17  17:28
     */
    List<Member> getMemberList(String sp, String channelId);
}
