package org.ph.ealer.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.ph.ealer.bean.Page;
import org.ph.ealer.bean.Picture;
import org.ph.ealer.bean.User;
import org.ph.ealer.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    PictureService pictureService;
    Logger logger = LoggerFactory.getLogger(PageController.class);

    //默认首页显示数
    private final static long indexPageRecords = 16;
    private final static long userPageRecords = 12;

    //首页
    @RequestMapping("/")
    public String index(){
        return "redirect:/works/1";
    }

    @RequestMapping({ "/works/{currentPage}"})
    public String index(HttpSession session, Model model, @PathVariable long currentPage){
        insertCurrentUser(session, model);

        if (currentPage <= 0) currentPage = 1;

        List<Picture> pictures = pictureService.getPictures(-1, currentPage, indexPageRecords);
        long totalRecords = pictureService.countPictures(-1);

        Page page = new Page(indexPageRecords, currentPage, totalRecords, pictures);
        model.addAttribute("page", page);
        return "index";
    }

    //登录页
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpSession session, Model model){
        //检验cookie是否有登录信息
        Cookie[] cookies = request.getCookies();
        String telOrEmail = "";
        String password = "";
        String rem = "checked";
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("telOrEmail")) {
                telOrEmail = cookie.getValue();
            }else if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }else if (cookie.getName().equals("rem")) {
                rem = "";
            }
        }
        model.addAttribute("telOrEmail", telOrEmail)
                .addAttribute("password", password)
                .addAttribute("rem", rem);

        String lastPage = request.getHeader("Referer");
        if (lastPage != null){
            lastPage = lastPage.split("Prototype")[1];
            logger.info("lastPage: " + lastPage);
            session.setAttribute("lastPage", lastPage);
        }
        return "login";
    }

    //注册页
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    //个人作品页
    @RequestMapping("/user")
    public String works(HttpSession session){
        User currentUser = (User) session.getAttribute("currentUser");
        return "redirect:/user/" + currentUser.getUid() + "/1";
    }

    @RequestMapping("/user/{uid}")
    public String works(@PathVariable long uid){
        return "redirect:/user/" + uid + "/1";
    }

    @RequestMapping(value = "/user/{uid}/{currentPage}")
    public String works(HttpSession session, Model model, @PathVariable long uid, @PathVariable long currentPage){
        long totalRecords = pictureService.countPictures(uid);
        List<Picture> pictures = pictureService.getPictures(uid, currentPage, 12);
        Page page = new Page(userPageRecords, currentPage, totalRecords, pictures);
        model.addAttribute("page", page);
        model.addAttribute("userId", uid);
        insertCurrentUser(session, model);

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null){
            long currentUserUid = currentUser.getUid();
            if (currentUserUid == uid){
                return "user";
            }
        }
        return "visitor";
    }

    //注入当前用户
    private void insertCurrentUser(HttpSession session, Model model){
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null){
            String username = currentUser.getUsername();
            long uid = currentUser.getUid();
            model.addAttribute("username", username)
                    .addAttribute("uid", uid);
        }else {
            model.addAttribute("uid", -1);
        }
    }
}
