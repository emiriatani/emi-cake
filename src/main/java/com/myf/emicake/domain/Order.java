package com.myf.emicake.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private Integer id;

    /**
    * 订单编号
    */
    private Integer orderId;

    /**
    * 用户id
    */
    private Integer memberId;

    /**
    * 商品总数量
    */
    private Integer productAmountTotal;

    /**
    * 商品总价
    */
    private BigDecimal productTotalPrice;

    /**
    * 实际付款
    */
    private BigDecimal orderTotalPrice;

    /**
    * 订单状态 0未付款 1已付款
    */
    private Byte orderStatus;

    /**
    * 配送方式 0门店自提，1外卖配送
    */
    private Byte deliveryType;

    /**
    * 支付方式 0支付宝 1微信
    */
    private Byte paymentType;

    /**
    * 付款时间
    */
    private Date paymentTime;

    /**
    * 发货时间
    */
    private Date deliveryTime;

    /**
    * 订单留言
    */
    private String orderMessage;

    /**
    * 订单结算状态 0未结算 1已结算
    */
    private Byte orderSettlementStatus;

    /**
    * 交易完成时间
    */
    private LocalDateTime orderCompletionTime;

    /**
    * 交易关闭时间
    */
    private LocalDateTime orderCloseTime;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;

    /**
    * 删除时间
    */
    private LocalDateTime deleteTime;

    private static final long serialVersionUID = 1L;
}