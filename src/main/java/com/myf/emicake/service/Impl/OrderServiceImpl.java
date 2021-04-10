package com.myf.emicake.service.Impl;

import com.myf.emicake.domain.Order;
import com.myf.emicake.domain.ProductSku;
import com.myf.emicake.domain.ProductStock;
import com.myf.emicake.dto.OrderDTO;
import com.myf.emicake.dto.OrderDetailDTO;
import com.myf.emicake.mapper.OrderMapper;
import com.myf.emicake.service.MemberService;
import com.myf.emicake.service.OrderService;
import com.myf.emicake.service.ProductSkuService;
import com.myf.emicake.service.ProductStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private MemberService memberService;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Order record) {
        return orderMapper.insert(record);
    }

    @Override
    public int insertSelective(Order record) {
        return orderMapper.insertSelective(record);
    }

    @Override
    public Order selectByPrimaryKey(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        return orderMapper.updateByPrimaryKey(record);
    }

    /**
     * 判断订单中的商品是否有库存、是否有效、是否在上架状态
     * @param orderDTO
     * @return
     */
    @Override
    public Map<Integer, Integer> isValid(OrderDTO orderDTO) {

        Map<Integer, Integer> map = new HashMap<>();
        for (OrderDetailDTO item :
                orderDTO.getOrderDetail()) {
            System.out.println("订单详情购物项:" + item);

            log.info("订单购物项sku id:" + item.getProductSku().toString());
            /*该商品库存信息*/
            ProductStock productStock = productStockService.selectByProductSkuId(item.getProductSku());
            System.out.println("该商品库存信息:" + productStock);
            /*该商品信息*/
            ProductSku productSku = productSkuService.selectByPrimaryKey(item.getProductSku());
            System.out.println("该商品信息:" + productSku);

            Integer productSkuId = productSku.getId();

            if (productStock.getStockState() ==  (byte)1){
                /*该商品无库存*/
                 map.put(2, productSkuId);
            }
            if (item.getPurchaseQuantity().intValue() > productStock.getAvailableStock().intValue()){
                /*该商品购买数量大于库存数量*/
                map.put(1, productSkuId);
            }else {
                /*该商品有库存，且订单购买数量小于库存数量*/
                if (productSku.getProductStatus() == 0){
                    /*商品无效*/
                    map.put(3, productSkuId);
                }
                if (productSku.getSaleStatus() == 0) {
                    map.put(4, productSkuId);
                }
            }
        }
        return map;
    }


    /**
     * 生成订单
     * @param orderDTO
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public Order generateOrder(OrderDTO orderDTO) throws InvocationTargetException, IllegalAccessException {


        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        log.info("日期前缀:" + format);
        String memberId = memberService.selectByPrimaryKey(orderDTO.getMemberId()).getMemberId();
        log.info("用户名前缀:" + memberId);
        String orderId= "E" + format + memberId;
        log.info("订单号:" + orderId);
        orderDTO.setOrderId(orderId);
        Order order = orderDTOToOrder(orderDTO);
        order.setOrderStatus((byte) 0);
        order.setOrderSettlementStatus((byte) 0);

        return order;
    }

    @Override
    public Order selectByOrderId(String orderId) {

        Order order = orderMapper.selectByOrderId(orderId);

        return order;
    }


    private Order orderDTOToOrder(OrderDTO orderDTO) throws InvocationTargetException, IllegalAccessException {
        Order order = new Order();
        BeanUtils.copyProperties(order, orderDTO);
        return order;
    }

}
