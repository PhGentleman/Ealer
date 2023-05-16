package org.ph.ealer.service;

import org.apache.ibatis.session.SqlSession;
import org.ph.ealer.bean.Tag;
import org.ph.ealer.mapper.PictureMapper;
import org.ph.ealer.status.PictureStatus;
import org.ph.ealer.utils.MyBatisUtil;

import java.util.HashSet;
import java.util.List;

public interface TagService {
    //创建新标签
    PictureStatus createTag(String tagName);

    //删除标签(暂时不用）
    PictureStatus deleteTag(long tid);

    //为指定图像添加标签
    PictureStatus addTag(long pid, String tagName, long uid);

    //为指定图像添加多个标签
    public PictureStatus addTags(long pid, long uid, List<Tag> tags);

    //为指定图像移除标签
    PictureStatus removeTag(long pid, String tagName, long uid);

}
