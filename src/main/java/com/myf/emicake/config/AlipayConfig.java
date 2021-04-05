package com.myf.emicake.config;

import org.springframework.stereotype.Component;

/**
 * @ClassName com.myf.emicake.config AlipayConfig
 * @Description
 * @Author Afengis
 * @Date 2021/4/5 20:40
 * @Version V1.0
 **/
@Component
public class AlipayConfig {

    /*应用ID，沙箱环境提供的APPID，对应的是收款账号的APPID*/
    public static final String APP_ID = "2021000116695237";

    /*商户私钥，即生成的应用私钥*/
    public static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDvMTI140m/pCSUtXXKZyNhuJiirct/mzX7DikyarWq8JmuCnRAt674cj13ToPJTkRMky+9/OpnCedybbbB3MO2zcgdKRlN9xz2nyXeUjofjcjSu7pK88TBlXwevL3R8oqirclQqz9nMa8v11jCURuMJ9FvfjatfmIqkJ4csibBh+My6IdhcxWsFkXZX5TlXMcJAp8k7QRDEdzwlov8Th0F4uWrRQMcJF3ZmyDGiau9giyCkhP9X1VUXxq3BqafeMNOjdVlLM1KkHE5Oqm+rvwy/2au2Ch4D5LTj6AzRrwJhpvIyPLD6xjohJDaMsLq8upOeW6XdbLZp7NDZSAsPIabAgMBAAECggEBALVmE/Q2SWnjbDKzY396tKSZwHgvYdOGvGuHk8q+jsPy8DnDh+LE6xdUXDUtAFkc2ENSpLRrKMG4CyUG+8EOUgdDDO7AioZMUFTg4XE33wirGJBo+qF5OdCuM5GEBllAnc8+CRz6onz6eyfHI3NK259xWy45A/KTlJM/VgnhOe8uTcGpAXMliU/4FKI6maqlXvRHcISfD0Dp27rMuPs93zKh+jg+y9zDPfB5Trc2GkneRZ7eBaYKB6SamnMYGHDIvUEPaH/XkSamwzPSJc1+JeZnBpSKgTRsjLQ0WAmBaDYG3VhEviMJ9IfB+uQTzk4u+amQ224tF/pyNqHwlW87LzECgYEA/Gq8byOoCwv/diG6FRj0GDVp7HgG2Zvcr4uOhktFRdea7YIwJaD5jF+sw7/roZupffH0b2Xo+CPRs1vHZfjdZedle/kPIfY3pGp6pSiCM35wUYjXCEU8BId3MDLeb8O7N3AJ3ursHfprf0f/cFnf7YfF3va9zMNbgyGCx/ACeqcCgYEA8pZm+2gwSADs5FQwwQfiQxYHNQyvA3oiJbd7O/L9b0SbxvMWLuU0QtLzCLoS4W55wdU8OFA4NQAqWbLGdfS8pgIbmTO/Dl2w0N+oc0REz1tv0BRvV3s50oJbmfMcdeSoN0hTVUiCFpnhoivnbh98ACmc3KgE89I8PGPmSHRYdu0CgYEA24F2nK/FUtX8Mh7iU7Z/jw3kL1+xlUuVfpM+Pkr20uXMNP8iH5b9bLVG6HPd1YwouP3QM4/MKP39Ppxxqpvg7N1tw4GzRzV27eQrA7VWhLFPu5fkZ6zNpKKpY6DZDxiHlo44B0MEUo2TLfzOhkWiFk4cJOkt5QN+cWqS8Iwnm8ECgYEAjjldoTIBdIWOXYN6ngFYR0qn6RNJnHlDgedjyc75hP2tcuPb8pyQmkkBEfG91gsmOV0iFEX73h79tV8Z+dgFrjJ4Kcbr0aleW+d05Dd3PDb4g/8nFBv7145z0/tziS8I7Hhr2wlKs6N+ZMzmv2qYEaRUAg5D8QsmI6XSQlJr9zUCgYBR4jgjH/qds9b3mv317azeKUUs0wTDJN1BzkxCpWsPZSv5RvBYnj1YvRGW5FcGfiPMJPJussGTLbVJBhgetmJsK05fWOS7sR8HhUQh4fiQ3E32W3WXqFxJ0R3opCiQBJlkwS1nr5vbzUZyVr3x9wZtSi1yA1Y523ckDkihaaSYLQ==";

    /*支付宝公钥*/
    public static final String Alipay_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAigMAXA2iv0dwiKMdNxCU+CnN7fM48IziciRwm++7whdo7iZS32UB5mgRo9HS2nPT27ovQEywCziCVCpXtUpm1+Jr8/7lVybWAa7oiIRVQCBYiuXi28s2f0svW7d2AiwLIRZf/u3Q7H97Q+xSQhmwiK6vM8LvfwqCHtVrfXRIbukMxGTjxLl131sr14Y/EOXzyqlu9OIMjcf07tJX131rVu76meYhvViZ1PErSOW3nuH54+sIx65NoupxOFqUrRMXT2/wFmve6U5CFdW/doI+w68MqOf+e7HwolemOVGxU9pjG7yJZLroKrhV/iG7bFlOSdOggxwB0oYpPEAduVLT9QIDAQAB";

    /*支付宝网关*/
    public static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    /*支付宝异步通知路径，付款完毕后会异步调用本项目的方法，必须为公网地址，格式为(http://)开头*/
    public static final String NOTIFY_URL = "http://uweu5n.natappfree.cc/pay/paydone";

    /*支付宝同步通知路径，也就是当付款完毕后跳转本项目的页面，可以不是公网地址，格式为(http://)开头*/
    public static  final String RETURN_URL = "http://localhost:8080/success.html";

    /*签名方式*/
    public static final  String SIGN_TYPE = "RSA2";

    /*编码类型*/
    public static final String CHARSET = "UTF-8";

    /*数据类型*/
    public static final String FORMAT = "json";

}
