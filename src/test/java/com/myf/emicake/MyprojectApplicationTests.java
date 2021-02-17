package com.myf.emicake;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myf.emicake.domain.Member;
import com.myf.emicake.domain.Product;
import com.myf.emicake.service.MemberService;
import com.myf.emicake.service.ProductService;
import com.myf.emicake.utils.JSONUtils;
import com.myf.emicake.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MyprojectApplicationTests {

    @Autowired
    private JSONUtils jsonUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
        Member member = memberService.selectByPrimaryKey(36);
        boolean member1 = redisUtils.set("member1", member);
        if (member1){
            Member member2 = redisUtils.get("member1");
            log.info(member2.toString());
        }
    }

    @Test
    void productServiceTest() throws JsonProcessingException {
        Product product = productService.selectByPrimaryKey(1);
        String s = jsonUtils.Object2JSON(product);
        redisUtils.set("prod", product);
        log.info(s);



    }

}
