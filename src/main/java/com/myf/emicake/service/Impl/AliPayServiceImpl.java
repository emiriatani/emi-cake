package com.myf.emicake.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.myf.emicake.common.Constants;
import com.myf.emicake.config.AlipayConfig;
import com.myf.emicake.dto.MemberDTO;
import com.myf.emicake.service.AlipayService;
import com.myf.emicake.service.OrderService;
import com.myf.emicake.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @ClassName com.example.demo.service AliPayServiceImpl
 * @Description
 * @Author Afengis
 * @Date 2021/1/10 15:52
 * @Version V1.0
 **/
@Service
@Slf4j
public class AliPayServiceImpl implements AlipayService {


    @Autowired
    private OrderService orderService;
    @Autowired
    private JSONUtils jsonUtils;



    @Override
    public String alipay(String orderId, String orderName, BigDecimal price, String orderDesc) {


        //获得初始化的AlipayClient
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.Alipay_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        /*商户订单号，商户网站订单系统中唯一订单号，必填*/
        String out_trade_no = orderId;
        /*
        商品标题/交易标题/订单标题/订单关键字等。
        注意：不可使用特殊字符，如 /，=，& 等。
         */
        String title = orderName;
        /*订单总金额，单位为人民币（元），取值范围为 0.01~100000000.00，精确到小数点后两位。*/
        String total_amount = String.valueOf(price);
        //商品描述，可空
        String body = orderDesc;
        //获取session中的用户信息
        Subject subject = SecurityUtils.getSubject();
        MemberDTO memberInfo = (MemberDTO) subject.getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);
        System.out.println("session中的用户信息:" + memberInfo);

        //String memberInfoStr = jsonUtils.Object2JSON(memberInfo);
        //String memberInfoJson = "{\"memberInfo\":\"" + memberInfoStr + "\"}";
        //String memberInfoStr = "";

        //设置同步通知和异步通知的地址
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        alipayRequest.setReturnUrl(AlipayConfig.NOTIFY_URL + memberInfo.getId());

//        try {
//            memberInfoStr = URLEncoder.encode(new String(p_param.getBytes(), "UTF-8"), "UTF-8");
//            log.info("编码后的用户信息:" + memberInfoStr);
//        } catch (UnsupportedEncodingException e) {
//            log.info("不支持的字符集转化成string");
//            e.printStackTrace();
//        }

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + title + "\","
                + "\"body\":\"" + body + "\","
                //+ "\"passback_params\":\"" + memberInfoStr + "\","
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
