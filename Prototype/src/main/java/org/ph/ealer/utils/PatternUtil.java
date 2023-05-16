package org.ph.ealer.utils;

import org.ph.ealer.bean.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
    public static boolean isEmail(String str) {
        String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
        return str.matches(expr);
    }

    public static boolean isTel(String tel) {
        Pattern pattern = Pattern
                .compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        Matcher matcher = pattern.matcher(tel);
        return matcher.matches();
    }

    public static boolean isUsername(String username){
        int len = username.length();
        return len >= 3 && len <=16;
    }

    public static boolean isPassword(String password){
        int len = password.length();
        return len >= 8 && len <=16;
    }

    public static boolean isUserLegal(User user){
        return isUsername(user.getUsername()) && isPassword(user.getPassword())
                &&isTel(String.valueOf(user.getTel())) && isEmail(user.getEmail());
    }
}
