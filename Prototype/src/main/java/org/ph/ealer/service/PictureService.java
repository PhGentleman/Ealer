package org.ph.ealer.service;

import org.ph.ealer.bean.Picture;
import org.ph.ealer.bean.Tag;
import org.ph.ealer.mapper.PictureMapper;
import org.ph.ealer.status.PictureStatus;

import java.util.HashSet;
import java.util.List;

public interface PictureService {
    // 获取所有或用户图像总数
    public long countPictures(long uid);

    //获取所有或指定用户图像
    List<Picture> getPictures(long uid, long currentPage, long pageRecords);

    //上传图像
    PictureStatus uploadPicture(Picture picture);

    //验证指定图像是否为指定用户
    static boolean isOwner(PictureMapper pictureMapper, long uid, long pid){
        long trueUid = pictureMapper.queryOwner(pid);
        return uid == trueUid;
    }

    //删除指定图像
    PictureStatus deletePicture(Picture picture, long uid);

    //重命名指定图像
    PictureStatus renamePicture(long pid, String picName, long uid);

}
