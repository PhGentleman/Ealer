package org.ph.ealer.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpSession;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.ph.ealer.bean.Picture;
import org.ph.ealer.bean.Tag;
import org.ph.ealer.bean.User;
import org.ph.ealer.service.PictureService;
import org.ph.ealer.service.TagService;
import org.ph.ealer.status.PictureStatus;
import org.ph.ealer.utils.FileUtil;
import org.ph.ealer.utils.OssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PictureController {
    @Autowired
    PictureService pictureService;
    @Autowired
    TagService tagService;
    Logger logger = LoggerFactory.getLogger(PictureController.class);

    // 上传页
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadPage(HttpSession session, Model model){
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/";
        }
        model.addAttribute("uid", currentUser.getUid())
                .addAttribute("username", currentUser.getUsername());
        return "upload";
    }

    // 上传处理
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadPicture(HttpSession session, Model model,
                                @RequestPart("file") MultipartFile file,
                                String picName, @RequestParam("tagNames") List<String> tagNames){
        if (file == null) {
            model.addAttribute("msg", "错误：无文件");
            return "error";
        }
        long uid = ((User) session.getAttribute("currentUser")).getUid();
        String path = uid+"/"+ FileUtil.renameFile(file.getOriginalFilename());

        // 处理标签
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames){
            tags.add(new Tag(tagName));
        }
        // 上传数据库
        Picture picture = new Picture(picName, path, uid, tags);
        pictureService.uploadPicture(picture);
        // 上传OSS
        try (InputStream inputStream = file.getInputStream(); OssUtil ossUtil = new OssUtil()){
            ossUtil.upload(path, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("msg", "success");
        return "upload";
    }

    //传入预测模型预测标签
    @RequestMapping("/tagPredict")
    @ResponseBody
    public List<String> tagPredict(Model model, @RequestPart("file") MultipartFile file){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:5000/upload");
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        List<String> tags = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()){
            multipartEntityBuilder.addBinaryBody("picture", inputStream,
                    ContentType.DEFAULT_BINARY, URLEncoder.encode(file.getOriginalFilename(), "UTF-8"));
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse closeableHttpResponse = client.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();

            if (entity != null){
                String res = EntityUtils.toString(entity, "UTF-8");
                if (!res.trim().isEmpty()){
                    if (res.equals("none")){
                        logger.info("未预测出标签");
                        tags.add("none");
                        return tags;
                    }
                    if (res.endsWith(",")){
                        logger.info(res);
                        String[] pTags = res.split(",");
                        for (String tag:pTags) tags.add(tag+"(predicted)");
                    }else {
                        logger.error("Flask出错");
                    }
                }else {
                    logger.error("Flask返回为空");
                }
            }else {
                logger.error("Entity返回为空");
                model.addAttribute("msg", "fatal");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tags;
    }

    @RequestMapping(value = "/picture/rename", method = RequestMethod.POST)
    @ResponseBody
    public String rename(HttpSession session, @RequestParam long pid, @RequestParam String picName){
        User currentUser = (User) session.getAttribute("currentUser");
        PictureStatus pictureStatus = pictureService.renamePicture(pid, picName, currentUser.getUid());
        return pictureStatus.getDesc();
    }

    @RequestMapping(value = "/picture/addTag", method = RequestMethod.POST)
    @ResponseBody
    public String addTag(HttpSession session, @RequestParam long pid, @RequestParam String tagName){
        User currentUser = (User) session.getAttribute("currentUser");
        PictureStatus pictureStatus = tagService.addTag(pid, tagName, currentUser.getUid());
        return pictureStatus.getDesc();
    }

    @RequestMapping(value = "/picture/removeTag", method = RequestMethod.POST)
    @ResponseBody
    public String removeTag(HttpSession session, @RequestParam long pid, @RequestParam String tagName){
        User currentUser = (User) session.getAttribute("currentUser");
        PictureStatus pictureStatus = tagService.removeTag(pid, tagName, currentUser.getUid());
        return pictureStatus.getDesc();
    }

    @RequestMapping(value = "/picture/deletePic", method = RequestMethod.POST)
    @ResponseBody
    public String deletePic(HttpSession session, @RequestParam String picture){
        logger.info(picture);
        User currentUser = (User) session.getAttribute("currentUser");
        Picture pic = JSON.parseObject(picture, Picture.class);
        PictureStatus pictureStatus = pictureService.deletePicture(pic, currentUser.getUid());
        return pictureStatus.getDesc();
    }
}
