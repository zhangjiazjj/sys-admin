package com.sys.usermanager.common;

import com.sys.usermanager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Result<T> {
    public Integer code;
    public String message;
    public T data;
    public static <T> Result<T> getSussess(T data){
        return new Result<>(20000,"成功",data);
    }

    public static<T> Result<T> getFail(){
        return new Result<>(20000,"用户名或密码错误",null);
    };
    public static<T> Result<T> getLogoutSuccess(){
        return new Result<>(20000,"退出登录成功",null);
    };

}
