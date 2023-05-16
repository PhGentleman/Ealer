package org.ph.ealer.bean;

public class Tag {
    private long tid;
    private String tagName;
    private long tagUsed;
    private long tagView;

    public Tag(long tid, String tagName, long tagUsed, long tagView) {
        this.tid = tid;
        this.tagName = tagName;
        this.tagUsed = tagUsed;
        this.tagView = tagView;
    }

    public Tag(long tid, String tagName) {
        this.tid = tid;
        this.tagName = tagName;
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(){

    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public long getTagUsed() {
        return tagUsed;
    }

    public void setTagUsed(long tagUsed) {
        this.tagUsed = tagUsed;
    }

    public long getTagView() {
        return tagView;
    }

    public void setTagView(long tagView) {
        this.tagView = tagView;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tid=" + tid +
                ", tagName='" + tagName + '\'' +
                ", tagUsed=" + tagUsed +
                ", tagView=" + tagView +
                '}';
    }
}
