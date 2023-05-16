package org.ph.ealer.mapper;

import org.apache.ibatis.annotations.Param;
import org.ph.ealer.bean.Picture;
import org.ph.ealer.bean.Tag;

import java.util.List;

public interface PictureMapper {
    // 获取所有图像总数
    long countAllPictures();

    // 获取范围内所有图像
    List<Picture> queryAllPictures(@Param("offset")long offset, @Param("rows")long rows);

    // 获取指定用户图像总数
    long countUserPictures(@Param("uid") long uid);

    // 获取指定用户的图像
    List<Picture> queryUserPictures(@Param("uid") long uid, @Param("offset") long offset, @Param("rows") long rows);

    // 查询图像所有者
    long queryOwner(long pid);

    // 上传图像
    int uploadPicture(Picture picture);

    // 删除指定图像
    int deletePicture(long pid);

    // 重命名指定图像
    int renamePicture(@Param("pid") long pid, @Param("picName") String picName);

    // 创建新标签
    int createTag(Tag tag);

    // 删除指定标签
    int deleteTag(long tid);

    // 查询指定标签
    Long queryTag(String tagName);

    // 为图像添加标签
    int addTag(@Param("pid") long pid, @Param("tid") long tid);

    // 为图像添加一批标签
    int addTags(@Param("pid") long pid, @Param("tagIds") List<Long> tagIds);

    // 为图像删除标签
    int removeTag(@Param("pid") long pid, @Param("tid") long tid);

}
