package com.myf.emicake.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.myf.emicake.domain.Member;
import com.myf.emicake.domain.MemberAddress;
import com.myf.emicake.dto.MemberAddressDTO;
import com.myf.emicake.dto.MemberFullAddressDTO;
import com.myf.emicake.dto.OrdererInfoDTO;
import com.myf.emicake.mapper.MemberAddressMapper;
import com.myf.emicake.mapper.MemberMapper;
import com.myf.emicake.service.MemberAddressService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MemberAddressServiceImpl implements MemberAddressService{

    @Resource
    private MemberAddressMapper memberAddressMapper;
    @Autowired
    private MemberMapper memberMapper;

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

    @Override
    public Map<String, Object> selectByMemberId(Integer memberId) throws InvocationTargetException, IllegalAccessException {

        /*根据主键查找*/
        Member member = memberMapper.selectByPrimaryKey(memberId);

        Map<String, Object> map = new LinkedHashMap<>();
        
        if (ObjectUtil.isNotNull(member)){
            List<MemberAddress> memberAddresses = memberAddressMapper.selectByMemberId(memberId);
            List<OrdererInfoDTO> ordererInfoDTOList = new LinkedList<>();
            List<MemberAddressDTO> memberAddressDTOList = new LinkedList<>();

            for (MemberAddress mAddr :
                    memberAddresses) {

                OrdererInfoDTO ordererInfoDTO = new OrdererInfoDTO();
                MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
                /*memberAddress表字段拆分存储*/
                BeanUtils.copyProperties(ordererInfoDTO,mAddr);
                BeanUtils.copyProperties(memberAddressDTO, mAddr);
                ordererInfoDTOList.add(ordererInfoDTO);
                memberAddressDTOList.add(memberAddressDTO);

            }
            map.put("ordererInfo", ordererInfoDTOList);
            map.put("ordererAddressInfo", memberAddressDTOList);
            return map;
        }
        return null;

    }

    @Override
    public MemberFullAddressDTO selectDefaultByMemberId(Integer memberId) throws InvocationTargetException, IllegalAccessException {

        /*根据主键查找*/
        MemberAddress memberAddress = memberAddressMapper.selectDefaultByMemberId(memberId);

        MemberFullAddressDTO memberFullAddressDTO = new MemberFullAddressDTO();
        BeanUtils.copyProperties(memberFullAddressDTO, memberAddress);

        return memberFullAddressDTO;

    }

}
