package com.upc;

import com.alibaba.fastjson2.JSON;
import com.upc.entity.School;
import com.upc.entity.User;
import com.upc.utils.IotUtils;


import com.upc.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@SpringBootTest
class QuickapiApplicationTests {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Test
    void testIotList() {
        IotUtils.getList();
    }

    @Test
    void testProrerty()
    {
        IotUtils.getProperty("456");
    }
    @Test
    void testSpecific()
    {
        IotUtils.getSpecificDevice("123456");
    }

    @Test
    void testRedis()
    {
//        ValueOperations operations = redisTemplate.opsForValue();
        User user = new User();
        user.setUserid(999L);
        user.setUsername("test");
        user.setCreateTime(LocalDateTime.now());
        String jsonString = JSON.toJSONString(user);
        stringRedisTemplate.opsForValue().set("user",jsonString);
        String user1 = stringRedisTemplate.opsForValue().get("user");
        User user2 = JSON.parseObject(jsonString, User.class);
        System.out.println(user2);


    }



    @Test
    //测试jwt令牌
    void contextLoads() {
        Map<String,Object> chaims=new HashMap<>();

        chaims.put("password",new School(1,"123"));
       String jwt= JwtUtils.generateJwt(chaims);
       System.out.println(jwt);
        Map<String,Object> chaims1=JwtUtils.parseJWT(jwt);
        Object password = chaims1.get("password");


    }


//    @Test
//    void testJwt()
//    {
//        Map<String,Object> chaims1=JwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJ0aWNrZXQiOiJnUUU2OER3QUFBQUFBQUFBQVM1b2RIUndPaTh2ZDJWcGVHbHVMbkZ4TG1OdmJTOXhMekF5ZERsRWQyeG9ZbFpsV2tVeFJuZFNkMmhETVVrQUFnUkFtYUJtQXdRZ0hBQUEiLCJvcGVuaWQiOiJvUzNtOTZJOU8yMFdjLUtWWVpFUzYzb09iV1ZnIiwiZGVsaXZlclJld2FyZCI6MC4wLCJ1c2VyaWQiOjUsInNjb3JlIjo1LCJwYXNzd29yZCI6Im9TM205Nkk5TzIwV2MtS1ZZWkVTNjNvT2JXVmciLCJiYWxhbmNlIjowLjAsInBob25lIjoiMTg3MDI1NDg5NzIiLCJpbWFnZVVybCI6Imh0dHBzOi8vdWktYXZhdGFycy5jb20vYXBpLz9uYW1lPW9TM205Nkk5TzIwV2MtS1ZZWkVTNjNvT2JXVmcmYmFja2dyb3VuZD00NTVhNjQmY29sb3I9ZmZmZmZmIiwibmlja25hbWUiOiJqYWNrIiwic3RhdGUiOjAsInVzZXJuYW1lIjoib1MzbTk2STlPMjBXYy1LVllaRVM2M29PYldWZyIsImV4cCI6MTcyMTg0NDI1Mn0.fIe7QYh4_mLJ-hPNtWB0faO4aAFJaJyEnxJjQgw8dkg");
//        System.out.println(chaims1);
//    }

//@Test
//    public void sendHtmlEmail() {
//        try {
//            // 创建邮件消息
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setFrom("3145267275@qq.com");
//            // 设置收件人
//            helper.setTo("h3145267275@163.com");
//            // 设置邮件主题
//            helper.setSubject("test");
//            // 设置邮件内容
//            helper.setText("<h1>nihao</h1>", true);
//
//            // 发送邮件
//            mailSender.send(mimeMessage);
//
//            log.info("发送邮件成功");
//
//        } catch (MailException e) {
//            log.error("发送邮件失败: {}", e.getMessage());
//        } catch (Exception e) {
//            log.error("发送邮件时发生未知错误: {}", e.getMessage());
//        }
//    }



//    获得一个json数据
    void testJson()
    {
        User user = new User();
        user.setUserid(5L);
        user.setUsername("春日里的暖阳光影");




    }


}
