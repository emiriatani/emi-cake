package com.myf.myproject.service.Impl;

import com.myf.myproject.dao.MemberMapper;
import com.myf.myproject.entity.Member;
import com.myf.myproject.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
@Service
public class MemberServiceImpl implements MemberService{

    @Resource
    private MemberMapper memberMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return memberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Member record) {
        return memberMapper.insert(record);
    }

    @Override
    public int insertSelective(Member record) {
        return memberMapper.insertSelective(record);
    }

    @Override
    public Member selectByPrimaryKey(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Member record) {
        return memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Member record) {
        return memberMapper.updateByPrimaryKey(record);
    }


    /*通过手机号查询会员用户,判断是否存在*/
    @Override
    public boolean isExistsMember(String phone) {
        Member member = memberMapper.selectByMemberPhone(phone);
        if (StringUtils.isEmpty(member)){
            return false;
        }
        return true;
    }

}
