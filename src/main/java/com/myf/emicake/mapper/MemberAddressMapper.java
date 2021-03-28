package com.myf.emicake.mapper;

import com.myf.emicake.domain.MemberAddress;

import java.util.List;

public interface MemberAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAddress record);

    int insertSelective(MemberAddress record);

    MemberAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberAddress record);

    int updateByPrimaryKey(MemberAddress record);

    List<MemberAddress> selectByMemberId(Integer memberId);

}