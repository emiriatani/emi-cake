package com.myf.emicake.component.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName com.myf.emicake.config AliyunSmsProperties
 * @Description
 * @Author Afengis
 * @Date 2021/2/27 18:48
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSmsProperties {

    private String connectTimeout;
    private String readTimeout;
    private String product;
    private String domain;
    private String regionId;
    private String sysVersion;
    private String sysAction;
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
}
