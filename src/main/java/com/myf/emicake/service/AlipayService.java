package com.myf.emicake.service;

import java.math.BigDecimal;

/**
 * @InterfaceName com.myf.emicake.service AlipayService
 * @Description
 * @Author Afengis
 * @Date 2021/4/5 20:41
 * @Version V1.0
 **/
public interface AlipayService {

    /*支付宝支付*/
    String alipay(String orderId, String orderName, BigDecimal price, String orderDesc);

}
