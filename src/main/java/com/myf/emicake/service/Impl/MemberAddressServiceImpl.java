package com.myf.emicake.service.Impl;

import com.myf.emicake.domain.MemberAddress;
import com.myf.emicake.mapper.MemberAddressMapper;
import com.myf.emicake.service.MemberAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class MemberAddressServiceImpl implements MemberAddressService{

    @Resource
    private MemberAddressMapper memberAddressMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return memberAddressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberAddress record) {
        return memberAddressMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberAddress record) {
        return memberAddressMapper.insertSelective(record);
    }

    @Override
    public MemberAddress selectByPrimaryKey(Integer id) {
        return memberAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberAddress record) {
        return memberAddressMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberAddress record) {
        return memberAddressMapper.updateByPrimaryKey(record);
    }

}
