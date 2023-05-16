package org.ph.ealer.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.ph.ealer.bean.User;
import org.ph.ealer.service.UserService;
import org.ph.ealer.status.UserResponseResult;
import org.ph.ealer.status.UserStatus;
import org.ph.ealer.utils.VerifyCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /*
    * 登录控制
    * */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, String telOrEmail, String password, String rem){
        UserResponseResult result = userService.login(telOrEmail, password);
        if (result.getCode() == (UserStatus.SUCCESS)){
            User currentUser = (User)result.getData();
            session.setAttribute("currentUser", currentUser);
            Cookie[] cookies = request.getCookies();
            //记住用户
            if (rem != null){
                Cookie telOrEmailCookie = new Cookie("telOrEmail", telOrEmail);
                Cookie passwordCookie = new Cookie("password", password);
                response.addCookie(telOrEmailCookie);
                response.addCookie(passwordCookie);
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("rem")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }else {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("telOrEmail")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    } else if (cookie.getName().equals("password")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
                Cookie remCookie = new Cookie("rem", "false");
                response.addCookie(remCookie);
            }
        }else {
            //model.addAttribute("msg", result.getMsg());
            return result.getMsg();
        }
        return "success";
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpSession session){
        //返回上页
        String lastPage = (String) session.getAttribute("lastPage");
        if (lastPage == null || lastPage.equals("/register")){
            return "redirect:/";
        }else {
            return "redirect:" + lastPage;
        }
    }

    /*
    * 注册控制
    * */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpSession session, Model model, User user, String userCode){
        if (!VerifyCodeUtil.codeVerify(session, userCode)){
            model.addAttribute("msg", "验证码出错");
            return "error";
        }
        UserResponseResult result = userService.register(user);
        if (result.getCode() == UserStatus.SUCCESS){
            User currentUser = (User)result.getData();
            session.setAttribute("currentUser", currentUser);
        }else {
            model.addAttribute("msg", result.getMsg());
            return "error";
        }
        return "redirect:/";
    }

    /*
    * 登出控制
    * */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        if (session.getAttribute("currentUser") != null){
            session.setAttribute("currentUser", null);
        }
        return "redirect:/";
    }
}
