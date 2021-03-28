package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.domain.Member;
import com.myf.emicake.service.MemberAddressService;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/all")
    public Result getAllMemberAddress() throws InvocationTargetException, IllegalAccessException {

        log.info("test");
        Subject subject = SecurityUtils.getSubject();
        Member principal = (Member) subject.getPrincipal();

        Map<String, Object> map = memberAddressService.selectByMemberId(principal.getId());

        if (!ObjectUtils.isEmpty(map)) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), map);
        }

        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());
    }


}
