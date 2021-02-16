package com.myf.emicake.mapper;

import com.myf.emicake.domain.Member;

public interface MemberMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    /**
     * 通过手机号查询会员用户信息
     * @param phone
     * @return
     */
    Member selectByMemberPhone(String phone);

    /**
     * 通过会员用户名(memberId)查询会员用户信息
     * @param memberId
     * @return
     */
    Member selectByMemberId(String memberId);
}