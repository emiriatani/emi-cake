package com.myf.emicake.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName com.myf.emicake.config MvcConfig
 * @Description  mvc配置类
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
            registry.addViewController("/member/register.html").setViewName("member/register");
            registry.addViewController("/member/setPassword.html").setViewName("/member/setPassword");
            registry.addViewController("/prod/goods.html").setViewName("/product/goods");
            registry.addViewController("/prod/category.html").setViewName("/product/category");

    }


}
