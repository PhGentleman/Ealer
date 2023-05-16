package org.ph.ealer.mapper;

import org.ph.ealer.bean.User;

public interface UserMapper {
    //添加用户
    int addUser(User user);

    //通过手机号查询用户
    User queryUserByTel(long tel);

    //通过邮箱查询用户
    User queryUserByEmail(String email);

    //通过id查询用户
    User queryUserById(long uid);

    //通过用户名查询用户
    User queryUserByUsername(String username);
}
