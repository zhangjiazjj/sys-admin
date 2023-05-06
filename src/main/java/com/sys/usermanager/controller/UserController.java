package com.sys.usermanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.usermanager.common.Result;
import com.sys.usermanager.entity.User;
import com.sys.usermanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangjia
 * @since 2023-04-24
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> getUserOne(@RequestBody User user) {
        Map<String, Object> res = userService.login(user.getUsername(), user.getPassword());
        if (res != null) {
            return Result.getSussess(res);
        } else {
            return Result.getFail();
        }
    }

    @GetMapping("/info")
    public Result<?> getUserInfo(@RequestParam(name = "token") String token) {
        Map<String, Object> data = userService.getUserInfo(token);
        if (data != null) {
            return Result.getSussess(data);
        } else {
            return Result.getFail();
        }
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        userService.logout(token);
        return Result.getLogoutSuccess();
    }

    //  查找用户
    @GetMapping("/list")
    public Result<?> selectUserList(@RequestParam(value = "pageSize", required = false) Long pageSize,
                                    @RequestParam(value = "pageNumber", required = false) Long pageNumber,
                                    @RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "phone", required = false) String phone
    ) {
//        条件构造器
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
//        按id降序排序
        wrapper.orderByDesc(User::getId);
//      页
        Page<User> page = new Page<>(pageNumber, pageSize);

//        分页查询,需要进行mybatis plus 配置类
        userService.page(page, wrapper);

        HashMap<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.getSussess(data);

    }

    //   添加用户
    @PostMapping
    public Result<?> addUser(@RequestBody User user) {
        userService.save(user);
        return Result.getSussess(null);
    }

    //    修改用户
    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        user.setPassword(null);
        userService.updateById(user);
        return Result.getSussess(null);
    }

    @GetMapping("/{id}")
    public Result<User> selectById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        user.setPassword(null);
        return Result.getSussess(user);
    }

    //    删除用户接口
//    需要判断是逻辑删除还是物理删除
    @DeleteMapping("/{id}")
    public Result<User> deleteById(@PathVariable("id") Integer id) {
        userService.removeById(id);
        return Result.getSussess(null);

    }


}
