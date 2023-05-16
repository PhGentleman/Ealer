package org.ph.ealer.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.ph.ealer.bean.User;
import org.ph.ealer.mapper.UserMapper;
import org.ph.ealer.service.UserService;
import org.ph.ealer.status.UserResponseResult;
import org.ph.ealer.status.UserStatus;
import org.ph.ealer.utils.MyBatisUtil;
import org.ph.ealer.utils.PatternUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    /*
    * 登录
    * */
    public UserResponseResult login(String telOrEmail, String password){
        if (telOrEmail.isEmpty() || telOrEmail.trim().isEmpty()
                || password.isEmpty() || password.trim().isEmpty()){
            return new UserResponseResult(UserStatus.EMPTY_ERROR, "错误：字符串为空");
        }
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            if (PatternUtil.isTel(telOrEmail)){
                User user = mapper.queryUserByTel(Long.parseLong(telOrEmail));
                return verifyPassword(user, password);
            }else if(PatternUtil.isEmail(telOrEmail)){
                User user = mapper.queryUserByEmail(telOrEmail);
                return verifyPassword(user, password);
            }else {
                return new UserResponseResult(UserStatus.PATTERN_ERROR, "错误：账号字符串不符合格式");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new UserResponseResult(UserStatus.FATAL, "内部错误");
    }

    private UserResponseResult verifyPassword(User user, String password){
        if (user != null){
            if (user.getPassword().equals(password)){
                return new UserResponseResult(UserStatus.SUCCESS, "成功", user);
            }else {
                return new UserResponseResult(UserStatus.WRONG_PASSWORD, "密码错误");
            }
        }else {
            return new UserResponseResult(UserStatus.NO_SUCH_USER_ERROR, "该用户不存在！");
        }
    }

    /*
     * 注册
     * */
    public UserResponseResult register(User user){
        if (PatternUtil.isUserLegal(user)){
            try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                if (mapper.queryUserByTel(user.getTel()) != null
                        || mapper.queryUserByEmail(user.getEmail()) != null){
                    return new UserResponseResult(UserStatus.USER_EXIST, "用户已存在");
                }else {
                    int res = mapper.addUser(user);
                    if (res == 1){
                        return new UserResponseResult(UserStatus.SUCCESS, "成功", user);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            return new UserResponseResult(UserStatus.PATTERN_ERROR, "账号信息格式有误");
        }
        return new UserResponseResult(UserStatus.FATAL, "内部错误");
    }
}
