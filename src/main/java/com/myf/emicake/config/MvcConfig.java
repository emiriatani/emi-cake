package com.myf.emicake.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName com.myf.emicake.config MvcConfig
 * @Description mvc配置类
 * @Author Afengis
 * @Date 2021/2/5 15:06
 * @Version V1.0
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/common/index");
        registry.addViewController("/index.html").setViewName("/common/index");
        registry.addViewController("/member/login.html").setViewName("/member/login");
        registry.addViewController("/member/register.html").setViewName("/member/register");
        registry.addViewController("/member/setPassword.html").setViewName("/member/setPassword");
        registry.addViewController("/member/memberCentre.html").setViewName("/member/memberCentre");
        registry.addViewController("/member/accountAddress.html.html").setViewName("/member/accountAddress");
        registry.addViewController("/member/accountEdit.html").setViewName("/member/accountEdit");
        registry.addViewController("/member/accountOrder.html").setViewName("/member/accountOrder");
        registry.addViewController("/product/goods.html").setViewName("/product/goods");
        registry.addViewController("/product/category.html").setViewName("/product/category");
        registry.addViewController("/shop/cart.html").setViewName("/cart/cart");
        registry.addViewController("/order/deal.html").setViewName("/order/deal");
        registry.addViewController("/order/addAccountAddress.html").setViewName("/order/addAccountAddress");
        registry.addViewController("/order/trade.html").setViewName("/order/trade");
        registry.addViewController("/order/success.html").setViewName("/order/success");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/prod/img/**").addResourceLocations("classpath:/upload/prod/img/");
        registry.addResourceHandler("/upload/prod/video/**").addResourceLocations("classpath:/upload/prod/video/");

    }


}
