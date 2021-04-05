package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.domain.Member;
import com.myf.emicake.domain.MemberAddress;
import com.myf.emicake.dto.MemberFullAddressDTO;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.service.MemberAddressService;
import com.myf.emicake.service.MemberService;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * @ClassName com.myf.emicake.controller MemberAddressController
 * @Description
 * @Author Afengis
 * @Date 2021/3/28 14:39
 * @Version V1.0
 **/
@RestController
@Slf4j
@RequestMapping("/memberAddress")
public class MemberAddressController {

    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/all")
    public Result getAll() throws InvocationTargetException, IllegalAccessException {

        Subject subject = SecurityUtils.getSubject();
        Member principal = (Member) subject.getPrincipal();

        Map<String, Object> map = memberAddressService.selectByMemberId(principal.getId());

        if (!ObjectUtils.isEmpty(map)) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), map);
        }

        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody @Valid MemberFullAddressDTO memberFullAddressDTO){

        MemberAddress memberAddress = new MemberAddress();
        BeanUtils.copyProperties(memberFullAddressDTO, memberAddress);

        int result = memberAddressService.insertSelective(memberAddress);

        if (result > 0) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        } else {
            return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody @Valid MemberFullAddressDTO MemberFullAddressDTO){

        System.out.println(MemberFullAddressDTO);
        MemberAddress memberAddress = new MemberAddress();
        BeanUtils.copyProperties(MemberFullAddressDTO, memberAddress);

        int update = memberAddressService.updateByPrimaryKeySelective(memberAddress);

        if (update > 0) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        } else {
            return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());

        }

    }

    @RequestMapping("/get")
    public Result getDefault(@RequestParam("memberId") Integer memberId) throws InvocationTargetException, IllegalAccessException {

        Member member = memberService.selectByPrimaryKey(memberId);

        if (ObjectUtils.isEmpty(member)){
            throw new GlobalException(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());
        }

        MemberFullAddressDTO memberFullAddressDTO = memberAddressService.selectDefaultByMemberId(memberId);

        return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(),memberFullAddressDTO);

    }



}
