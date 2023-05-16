package org.ph.ealer.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.codehaus.jettison.util.FastStack;
import org.ph.ealer.bean.Tag;
import org.ph.ealer.mapper.PictureMapper;
import org.ph.ealer.service.PictureService;
import org.ph.ealer.service.TagService;
import org.ph.ealer.status.PictureStatus;
import org.ph.ealer.utils.MyBatisUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    //创建新标签（暂时不用）
    public PictureStatus createTag(String tagName){
        int res;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            res = pictureMapper.createTag(new Tag(tagName));
            if (res == 1) return PictureStatus.SUCCESS;
            else return PictureStatus.FATAL;
        }
    }

    //删除标签（暂时不用）
    public PictureStatus deleteTag(long tid){
        int res = 0;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            res = pictureMapper.deleteTag(tid);
            if (res == 1) return PictureStatus.SUCCESS;
            else return PictureStatus.FATAL;
        }
    }

    //为指定图像添加标签
    public PictureStatus addTag(long pid, String tagName, long uid){
        Long tid;
        int res = 0;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            if (PictureService.isOwner(pictureMapper, uid, pid)){
                //先查询有无该标签
                tid = pictureMapper.queryTag(tagName);
                if (tid != null){
                    res = pictureMapper.addTag(pid, tid);
                }else {
                    Tag newTag = new Tag(tagName);
                    res = pictureMapper.createTag(newTag);
                    pictureMapper.addTag(pid, newTag.getTid());
                }
            }else {
                return PictureStatus.WRONG_OWNER;
            }
        }
        if (res != 0) return PictureStatus.SUCCESS;
        else return PictureStatus.FATAL;
    }

    //为指定图像添加多个标签
    public PictureStatus addTags(long pid, long uid, List<Tag> tags){
        Long tid;
        int res = 0;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            if (uid == -1 || PictureService.isOwner(pictureMapper, uid, pid)){
                //先查询有无该标签
                List<Long> tagIds = new ArrayList<>();
                for (Tag tag : tags){
                    tid = pictureMapper.queryTag(tag.getTagName());
                    if (tid != null){
                        tagIds.add(tid);
                    }else {
                        res = pictureMapper.createTag(tag);
                        tagIds.add(tag.getTid());
                    }
                }
                pictureMapper.addTags(pid, tagIds);
            }else {
                return PictureStatus.WRONG_OWNER;
            }
        }
        if (res != 0) return PictureStatus.SUCCESS;
        else return PictureStatus.FATAL;
    }

    //为指定图像移除标签
    public PictureStatus removeTag(long pid, String tagName, long uid){
        int res;
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()){
            PictureMapper pictureMapper = sqlSession.getMapper(PictureMapper.class);
            Long tid = pictureMapper.queryTag(tagName);
            if (tid == null){
                return PictureStatus.FATAL;
            }
            if (PictureService.isOwner(pictureMapper, uid, pid)){
                res = pictureMapper.removeTag(pid, tid);
                if (res == 1) return PictureStatus.SUCCESS;
                else return PictureStatus.FATAL;
            }else {
                return PictureStatus.WRONG_OWNER;
            }
        }
    }

}
