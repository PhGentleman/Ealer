package org.ph.ealer.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.ph.ealer.mapper.UserMapper;
import org.ph.ealer.service.ValidationService;
import org.ph.ealer.status.UserResponseResult;
import org.ph.ealer.status.UserStatus;
import org.ph.ealer.utils.MyBatisUtil;
import org.ph.ealer.utils.PatternUtil;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public UserResponseResult isUsernameExist(String username) {
        if (PatternUtil.isUsername(username)){
            try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                if (mapper.queryUserByUsername(username) == null){
                    return new UserResponseResult(UserStatus.SUCCESS, "可用");
                }else {
                    return new UserResponseResult(UserStatus.USER_EXIST, "用户名已存在");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            return new UserResponseResult(UserStatus.PATTERN_ERROR, "用户名长度不正确");
        }
        return new UserResponseResult(UserStatus.FATAL, "错误：内部错误");
    }

    @Override
    public UserResponseResult isPasswordLegal(String password) {
        if (PatternUtil.isPassword(password)){
            return new UserResponseResult(UserStatus.SUCCESS, "可用");
        }else {
            return new UserResponseResult(UserStatus.PATTERN_ERROR, "密码长度不正确");
        }
    }

    @Override
    public UserResponseResult isTelExist(long tel) {
        if (!PatternUtil.isTel(String.valueOf(tel))){
            return new UserResponseResult(UserStatus.PATTERN_ERROR, "手机号格式有误");
        }
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            if (mapper.queryUserByTel(tel) == null){
                return new UserResponseResult(UserStatus.SUCCESS, "可用");
            }else {
                return new UserResponseResult(UserStatus.USER_EXIST, "手机号已被注册");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new UserResponseResult(UserStatus.FATAL, "错误：内部错误");
    }

    @Override
    public UserResponseResult isEmailExist(String email) {
        if (!PatternUtil.isEmail(email)){
            return new UserResponseResult(UserStatus.PATTERN_ERROR, "邮箱格式有误");
        }
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            if (mapper.queryUserByEmail(email) == null){
                return new UserResponseResult(UserStatus.SUCCESS, "可用");
            }else {
                return new UserResponseResult(UserStatus.USER_EXIST, "邮箱已被注册");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new UserResponseResult(UserStatus.FATAL, "错误：内部错误");
    }

    @Override
    public UserResponseResult isCodeTrue(boolean truth){
        return truth ? new UserResponseResult(UserStatus.SUCCESS, "可用")
                : new UserResponseResult(UserStatus.WRONG_VERIFY_CODE, "验证码错误");
    }
}
