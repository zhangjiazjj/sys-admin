package com.sys;

import com.alibaba.fastjson2.JSON;
import com.sys.usermanager.common.Result;
import com.sys.usermanager.entity.User;
import com.sys.usermanager.mapper.UserMapper;
import com.sys.usermanager.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SysAdminApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    IUserService userService;


    @Test
    void contextLoads() {
//        Object o = redisTemplate.opsForValue().get("user:1df3b5ac-7674-4b72-8719-13706b8fd5f8");
//        System.out.println(o);
////        User user = JSON.parseObject(JSON.toJSONString(o), User.class);
////        System.out.println(user);
        Map<String, Object> userInfo = userService.getUserInfo("user:2771804e-8965-4797-a7af-08106c3d6cd2");
        System.out.println(userInfo);

    }



}
