package api.dongsheng.service.impl;

import api.dongsheng.model.entity.Member;
import api.dongsheng.model.mapper.MembershipMapper;
import api.dongsheng.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: aioh_sw_oocs
 * @description:
 * @author: urbane
 * @create: 2020-04-17 17:20
 **/
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private MembershipMapper membershipMapper;


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
    @Override
    public List<Member> getMemberList(String sp, String channelId) {
        List<Member> memberList = membershipMapper.selectMemberList(Long.valueOf(channelId), sp);
        return memberList;
    }
}
