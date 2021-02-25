package com.myf.emicake.service.Impl;

import com.myf.emicake.mapper.MemberMapper;
import com.myf.emicake.domain.Member;
import com.myf.emicake.service.MemberService;
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


    /**
     *  通过手机号查询会员用户,判断是否存在
     * @param phone 手机号
     * @return true:存在该会员用户 false:不存在该会员用户
     */
    @Override
    public boolean isExistsMember(String phone) {
        Member member = memberMapper.selectByMemberPhone(phone);
        if (StringUtils.isEmpty(member)){
            return false;
        }
        return true;
    }

    /**
     * 通过手机号查询会员用户信息
     * @param phone 手机号
     * @return Member:会员用户
     */
    @Override
    public Member selectByMemberPhone(String phone) {
        Member member = memberMapper.selectByMemberPhone(phone);
        return member;
    }

    /**
     * 通过会员用户名(memberId)查询会员信息
     * @param memberId
     * @return
     */
    @Override
    public Member selectByMemberId(String memberId) {
        Member member = memberMapper.selectByMemberId(memberId);
        return member;
    }

}
