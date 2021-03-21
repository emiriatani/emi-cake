package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName com.myf.emicake.controller OrderController
 * @Description
 * @Author Afengis
 * @Date 2021/3/21 15:15
 * @Version V1.0
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @RequestMapping("/toDeal")
    public Result toDeal(@RequestBody @Validated CartDTO cartDTO){

          if (!ObjectUtils.isEmpty(cartDTO)){
              return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
          }

        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());

    }


}
