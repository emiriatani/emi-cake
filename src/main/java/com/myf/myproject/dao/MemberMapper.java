package com.myf.myproject.dao;

import com.myf.myproject.entity.Member;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    /*通过手机号查询会员用户*/
    Member selectByMemberPhone(String phone);
}