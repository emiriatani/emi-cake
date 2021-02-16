package com.myf.emicake;

import com.myf.emicake.utils.RedisUtils;
import com.myf.emicake.utils.ShiroUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyprojectApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    void contextLoads() {
        String  str = ShiroUtils.encryptPassword("SHA-256", "minyifeng789.", ShiroUtils.generateSalt(8), 1024);
        System.out.println(str);

    }

}
