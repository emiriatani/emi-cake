package com.myf.emicake.controller;

import com.myf.emicake.common.Constants;
import com.myf.emicake.component.Producter;
import com.myf.emicake.component.properties.RabbitMqMsgProperties;
import com.myf.emicake.domain.Member;
import com.myf.emicake.domain.Order;
import com.myf.emicake.dto.MemberDTO;
import com.myf.emicake.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName com.myf.emicake.controller PayController
 * @Description
 * @Author Afengis
 * @Date 2021/4/7 16:55
 * @Version V1.0
 **/
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderPickupService orderPickupService;
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private RabbitMqMsgProperties rabbitMqMsgProperties;
    @Autowired
    private Producter producter;
    @Autowired
    private CartService cartService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductStockService productStockService;


    @RequestMapping("/toPay")
    @ResponseBody
    public String toPay(@RequestParam("orderId") Integer id) {
        /*
        二、支付接口调用成功后，
        */
        Order fullOrder = orderService.selectByPrimaryKey(id);
        String orderForm = alipayService.alipay(fullOrder.getOrderId(), Constants.ORDER_NAME, fullOrder.getOrderTotalPrice(), fullOrder.getOrderMessage());
        log.info("支付接口调用产生数据：" + orderForm);
        return orderForm;
    }

    @RequestMapping("/done/{memberId}")
    public String payDone(@RequestParam("out_trade_no") String orderId,
                          @PathVariable("memberId") String memberId,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        /*
        三、支付成功后，
            异步通知MQ 更新库存，保存订单详情信息、自提订单信息/外卖配送订单信息
        */
        /*查询该订单状态*/
        log.info("支付完成回调... 订单id:" + orderId);
        log.info("会员id:" + memberId);
//        String decode = "";
//        try {
//            //passback_params = new String(request.getParameter("passback_params").getBytes(), "UTF-8");
//            decode = URLDecoder.decode(passback_params, "UTF-8");
//            log.info("用户信息:" + decode);
//        } catch (UnsupportedEncodingException e) {
//            log.info("不支持的字符集转化成string");
//            e.printStackTrace();
//        }

        Member member = memberService.selectByPrimaryKey(Integer.valueOf(memberId));
        if (!ObjectUtils.isEmpty(member)) {
            MemberDTO memberDTO = new MemberDTO();
            try {
                BeanUtils.copyProperties(memberDTO, member);
                MemberDTO sessionMemberDTO = (MemberDTO) SecurityUtils.getSubject().getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);
                System.out.println("支付完成回调后session中的用户信息:" + sessionMemberDTO);
                /*在session中重新放入用户信息*/
                SecurityUtils.getSubject().getSession().setAttribute(Constants.LOGIN_MEMBER_KEY, memberDTO);
                request.getSession().setAttribute(Constants.LOGIN_MEMBER_KEY, memberDTO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        Order order = orderService.selectByOrderId(orderId);
        if ((order.getOrderStatus() & order.getOrderSettlementStatus()) != 1) {
            /*没有重复支付的情况下*/
            order.setOrderStatus((byte) 1);
            order.setOrderSettlementStatus((byte) 1);
            orderService.updateByPrimaryKeySelective(order);
             /*发送MQ消息，
            解锁库存，扣减库存，增加销售量
            保存订单详情信息、自提订单信息/外卖配送订单信息
            清空账户购物车
            */
            log.info("发送MQ消息，开始扣减库存");
            /*1.扣减库存*/
            /*2.增加商品销售*/
            /*3.保存订单详情信息,自提订单/外卖配送订单信息*/
            /*4.清空购物车*/
            cartService.deleteAllCartItem();
            //response.getWriter().write("success");

        } else {
            /*订单重复支付*/
        }
        return "order/success";
    }

    @RequestMapping(value = "/return",method = RequestMethod.GET)
    public String returnHandler(){
        return "order/success";
    }

}
