package org.ph.ealer.bean;

import java.net.URL;
import java.util.List;

public class Picture {
    private long pid;
    private String picName;
    private String picLoc;
    private long uid;
    private int picView;
    private int picLike;
    private int picFav;
    private User owner;
    private List<Tag> tags;
    private String picDesc;
    private URL completePath;
    private URL thumbnailPath;


    public Picture(long pid, String picName, String picLoc, long uid, int picView, int picLike, int picFav, User owner, List<Tag> tags, String picDesc) {
        this.pid = pid;
        this.picName = picName;
        this.picLoc = picLoc;
        this.uid = uid;
        this.picView = picView;
        this.picLike = picLike;
        this.picFav = picFav;
        this.owner = owner;
        this.tags = tags;
        this.picDesc = picDesc;
    }

    public Picture(String picName, String picLoc, long uid, List<Tag> tags) {
        this.picName = picName;
        this.picLoc = picLoc;
        this.uid = uid;
        this.tags = tags;
    }

    public Picture(){

    }

    public long getPid() {
        return pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }
    public String getPicName() {
        return picName;
    }
    public void setPicName(String picName) {
        this.picName = picName;
    }
    public String getPicLoc() {
        return picLoc;
    }
    public void setPicLoc(String picLoc) {
        this.picLoc = picLoc;
    }
    public long getUid() {
        return uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }
    public int getPicView() {
        return picView;
    }
    public void setPicView(int picView) {
        this.picView = picView;
    }
    public int getPicLike() {
        return picLike;
    }
    public void setPicLike(int picLike) {
        this.picLike = picLike;
    }
    public int getPicFav() {
        return picFav;
    }
    public void setPicFav(int picFav) {
        this.picFav = picFav;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public List<Tag> getTags() {
        return tags;
    }
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public String getPicDesc() {
        return picDesc;
    }
    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc;
    }
    public URL getCompletePath() {
        return completePath;
    }
    public void setCompletePath(URL completePath) {
        this.completePath = completePath;
    }
    public URL getThumbnailPath() {
        return thumbnailPath;
    }
    public void setThumbnailPath(URL thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "pid=" + pid +
                ", picName='" + picName + '\'' +
                ", picLoc='" + picLoc + '\'' +
                ", uid=" + uid +
                ", picView=" + picView +
                ", picLike=" + picLike +
                ", picFav=" + picFav +
                ", owner=" + owner.toString() +
                ", tags=" + tags +
                ", picDesc='" + picDesc + '\'' +
                ", completePath=" + completePath +
                ", thumbnailPath=" + thumbnailPath +
                '}';
    }
}
