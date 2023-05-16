package org.ph.ealer.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.ph.ealer.service.ValidationService;
import org.ph.ealer.utils.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


@Controller()
public class ValidationController {
    @Autowired
    private ValidationService validationService;

    @RequestMapping(value = "/validation", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String validation(HttpSession session, @RequestParam ("type") String type, @RequestParam("value") String value){
        if (value.isEmpty()){
            return "请输入";
        }
        return switch (type) {
            case "username" -> validationService.isUsernameExist(value).getMsg();
            case "password" -> validationService.isPasswordLegal(value).getMsg();
            case "tel" -> validationService.isTelExist(Long.parseLong(value)).getMsg();
            case "email" -> validationService.isEmailExist(value).getMsg();
            case "code" -> validationService.isCodeTrue(VerifyCodeUtil.codeVerify(session, value)).getMsg();
            default -> "错误：类型有误";
        };
    }

    @RequestMapping("/getVerifyImage")
    @ResponseBody
    public void getVerifyImage(HttpSession session, HttpServletResponse response){
        VerifyCodeUtil vcu = new VerifyCodeUtil();
        BufferedImage image = vcu.createImage();
        String verifyCode = vcu.getText();
        session.setAttribute("verifyCode", verifyCode);
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
