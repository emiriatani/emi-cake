package com.myf.emicake.service;

import com.myf.emicake.domain.MemberAddress;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface MemberAddressService{


    int deleteByPrimaryKey(Integer id);

    int insert(MemberAddress record);

    int insertSelective(MemberAddress record);

    MemberAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberAddress record);

    int updateByPrimaryKey(MemberAddress record);

    Map<String,Object> selectByMemberId(Integer memberId) throws InvocationTargetException, IllegalAccessException;

}
