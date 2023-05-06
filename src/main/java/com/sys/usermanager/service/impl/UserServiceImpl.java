package com.sys.usermanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sys.usermanager.entity.User;
import com.sys.usermanager.mapper.UserMapper;
import com.sys.usermanager.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangjia
 * @since 2023-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;


    @Resource
    RedisTemplate redisTemplate;


    @Override
    public Map<String, Object> login(String username, String password) {
        User loginUser = userMapper.selectUser(username,password);
        if (loginUser != null){
            String key = "user:"+UUID.randomUUID();

            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,10800, TimeUnit.SECONDS);
            Map<String,Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }else {
            return null;
        }
        //存如redis
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        User user = (User) redisTemplate.opsForValue().get(token);
        if(user != null){
            Map<String,Object> data = new HashMap<>();
            data.put("name",user.getUsername());
            data.put("avatar",user.getAvatar());
            List<String> list = this.baseMapper.selectUserInfoByUserId(user.getId());

            data.put("roles",list);
            return  data;
        }

        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }


}
