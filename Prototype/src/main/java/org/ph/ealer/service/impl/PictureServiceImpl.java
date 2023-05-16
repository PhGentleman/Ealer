package org.ph.ealer.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.ph.ealer.bean.Picture;
import org.ph.ealer.bean.Tag;
import org.ph.ealer.mapper.PictureMapper;
import org.ph.ealer.service.PictureService;
import org.ph.ealer.service.TagService;
import org.ph.ealer.status.PictureStatus;
import org.ph.ealer.utils.MyBatisUtil;
import org.ph.ealer.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService{
    @Autowired
    TagService tagService;

    // 获取所有或用户图像总数
    public long countPictures(long uid){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper mapper = sqlSession.getMapper(PictureMapper.class);
            if (uid == -1){
                return mapper.countAllPictures();
            }else {
                return mapper.countUserPictures(uid);
            }
        }
    }

    // 获取所有或用户图像
    public List<Picture> getPictures(long uid, long currentPage, long pageRecords){
        // 计算起始与记录数
        long offset = (currentPage - 1) * pageRecords;
        long rows = pageRecords;
        List<Picture> pictures;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession(); OssUtil ossUtil = new OssUtil()){
            PictureMapper mapper = sqlSession.getMapper(PictureMapper.class);
            if (uid == -1){
                pictures = mapper.queryAllPictures(offset, rows);
            }else {
                pictures = mapper.queryUserPictures(uid, offset, rows);
            }
            pictures.forEach(picture -> {
                String path = picture.getPicLoc();
                picture.setCompletePath(ossUtil.completeShow(path));
                picture.setThumbnailPath((ossUtil.thumbnailShow(path)));
            });
        }
        return pictures;
    }

    // 上传图像
    // Picture(String picName, String picLoc, long uid, List<Tag> tags)
    // Tag(String tagName)
    public PictureStatus uploadPicture(Picture picture){
        long res;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper mapper = sqlSession.getMapper(PictureMapper.class);
            res = mapper.uploadPicture(picture);
            // 把标签也上传
            List<Tag> tags = picture.getTags();
            if (tags != null){
                long pid = picture.getPid();
                tagService.addTags(pid, -1, tags);
            }
        }
        if (res == 1) return PictureStatus.SUCCESS;
        else return PictureStatus.FATAL;
    }

    // 验证指定图像是否为指定用户
    public static boolean isOwner(PictureMapper pictureMapper, long uid, long pid){
        long trueUid = pictureMapper.queryOwner(pid);
        return uid == trueUid;
    }

    // 删除指定图像
    public PictureStatus deletePicture(Picture picture, long uid){
        int res;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            long pid = picture.getPid();
            if (isOwner(pictureMapper, uid, pid)){
                // 也删除相关tag映射
                List<Tag> tags = picture.getTags();
                tags.forEach((tag) -> {
                    Long tid = pictureMapper.queryTag(tag.getTagName());
                    pictureMapper.removeTag(pid, tid);
                });
                res = pictureMapper.deletePicture(pid);
                if (res == 1) return PictureStatus.SUCCESS;
                else return PictureStatus.FATAL;
            }else {
                return PictureStatus.WRONG_OWNER;
            }
        }
    }

    // 重命名指定图像
    public PictureStatus renamePicture(long pid, String picName, long uid){
        int res;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            if (isOwner(pictureMapper, uid, pid)){
                res = pictureMapper.renamePicture(pid, picName);
                if (res == 1) return PictureStatus.SUCCESS;
                else return PictureStatus.FATAL;
            }else {
                return PictureStatus.WRONG_OWNER;
            }
        }
    }


}
