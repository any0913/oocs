package api.dongsheng.model.mapper;

import api.dongsheng.model.entity.Member;
import api.dongsheng.model.entity.Membership;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MembershipMapper {


    /**
     *  根据会员商品对应下游渠道和会员商品对应上游服务商查询出商品列表
     * @param membershipChannel    商品渠道 指该商品应用于哪个下游渠道
     * @param membershipPlatform   商品平台
     * @return
     */
    List<Member>  selectMemberList(@Param("membershipChannel") Long membershipChannel, @Param(
            "membershipPlatform") String membershipPlatform);
    /**
     *  根据商品ID查询出商品信息
     * @param goodsId  商品ID
     * @return
     */
    Membership selectByGoodsId(@Param("goodsId")String goodsId);

    int deleteByPrimaryKey(Long id);

    int insert(Membership record);

    int insertSelective(Membership record);

    Membership selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Membership record);

    int updateByPrimaryKey(Membership record);
}