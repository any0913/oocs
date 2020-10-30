package api.dongsheng.model.mapper;

import api.dongsheng.model.entity.MembershipRightsError;

public interface MembershipRightsErrorMapper {


    int insert(MembershipRightsError record);

    int insertSelective(MembershipRightsError record);

    MembershipRightsError selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MembershipRightsError record);

    int updateByPrimaryKey(MembershipRightsError record);
}