package com.myf.myproject.service;

import com.myf.myproject.entity.Member;
public interface MemberService{


    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    boolean isExistsMember(String phone);
}
