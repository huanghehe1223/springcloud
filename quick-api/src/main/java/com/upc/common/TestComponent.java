package com.upc.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestComponent {

    @Autowired
    RedisTemplate redisTemplate;


   private String test="123";

   public  String hello()
   {
       return test;
   }


}
