package com.myf.emicake.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * sku-id
     */
    private Integer itemId;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 结算状态，0未结算 1已结算
     */
    private Byte settlementStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}