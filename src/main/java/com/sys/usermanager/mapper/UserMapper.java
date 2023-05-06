package com.sys.usermanager.mapper;

import com.sys.usermanager.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangjia
 * @since 2023-04-24
 */
public interface UserMapper extends BaseMapper<User> {
    public User selectUser(@Param("username") String username, @Param("password") String password);

    public List<String> selectUserInfoByUserId(@Param("userId")Integer userId);
}
