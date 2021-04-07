package com.myf.emicake.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.myf.emicake.config.AlipayConfig;
import com.myf.emicake.service.AlipayService;
import org.springframework.stereotype.Service;

/**
 * @ClassName com.example.demo.service AliPayServiceImpl
 * @Description
 * @Author Afengis
 * @Date 2021/1/10 15:52
 * @Version V1.0
 **/
@Service
public class AliPayServiceImpl implements AlipayService {


    @Override
    public String alipay(String orderId, String orderName, double price, String orderDesc) {

        //获得初始化的AlipayClient
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.Alipay_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //设置同步通知和异步通知的地址
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        alipayRequest.setReturnUrl(AlipayConfig.NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderId;
        //订单名称，必填
        String subject = orderName;
        //付款金额，必填
        String total_amount = String.valueOf(price);
        //商品描述，可空
        String body = orderDesc;

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String form = "";

        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()) {
                form = alipayClient.pageExecute(alipayRequest).getBody();
                // 这里返回的 form 是一个字符串，里面封装了支付的表单信息
                //（即 html 标签 和 javascript 代码），直接将这个 form 输出到页面即可。
                return form;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return null;
    }
}
