package com.myf.myproject;

import com.myf.myproject.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyprojectApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    void contextLoads() {
        boolean set = redisUtils.set("test", "testvalue");
        System.out.println(set);
        Object test = redisUtils.get("test");
        System.out.println(test);
    }

}
