package org.ph.ealer.service;

import org.ph.ealer.bean.User;
import org.ph.ealer.status.UserResponseResult;

public interface UserService {
    //登录
    UserResponseResult login(String tel_email, String password);
    //注册
    UserResponseResult register(User user);
}
