package org.ph.ealer.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
    public boolean mkdir(String dir_path){
        File file = new File(dir_path);
        if (!file.isDirectory() && !file.exists()){
            file.mkdirs();
            System.out.println(file.getAbsolutePath());
            return true;
        }
        return false;
    }

    public static String renameFile(String fileName) {
        SimpleDateFormat fmdate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmdate.format(new Date()) + fileName.substring(fileName.lastIndexOf('.'));
    }

}
