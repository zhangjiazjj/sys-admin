package com.sys.usermanager.service;

import com.sys.usermanager.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangjia
 * @since 2023-04-24
 */
public interface IUserService extends IService<User> {



    Map<String, Object> login(String username, String password);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);
}
