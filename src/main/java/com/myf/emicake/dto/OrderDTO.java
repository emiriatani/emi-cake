package com.myf.emicake.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName com.myf.emicake.dto OrderDTO
 * @Description
 * @Author Afengis
 * @Date 2021/3/27 21:49
 * @Version V1.0
 **/
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 4906752978973836634L;

    private Integer id;

    /*订单编号*/
    private String orderId;

    /*会员id*/
    private Integer memberId;

    /*商品总数量*/
    private Integer productAmountTotal;

    /*商品总价*/
    private BigDecimal productTotalPrice;

    /*实际付款*/
    private BigDecimal orderTotalPrice;

    /*配送方式 0 门店自提 1 外卖配送
    * */
    private Byte deliveryType;

    /*支付方式 0 支付宝  1微信
    * */
    private Byte paymentType;

    /*订单留言*/
    private String orderMessage;

    /*订单详情DTO*/
    private List<OrderDetailDTO>  orderDetail;
    /*订单自提DTO*/
    private OrderPickUpDTO orderPickup;
    /*订单配送DTO*/
    private OrderAddressDTO orderAddress;



}
