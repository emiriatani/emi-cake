package com.myf.emicake.service;

import com.myf.emicake.domain.Member;

public interface MemberService{


    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    boolean isExistsMember(String phone);

    Member selectByMemberPhone(String phone);

    Member selectByMemberId(String memberId);
}
