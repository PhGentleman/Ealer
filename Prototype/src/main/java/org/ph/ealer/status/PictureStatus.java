package org.ph.ealer.status;

public enum PictureStatus {
    REQUEST_FOR_THUMBNAIL(4, "Request for thumbnail"),
    REQUEST_FOR_COMPLETE(3, "Request for complete picture"),
    ALL_REQUEST(2, "Request for all"),
    USER_REQUEST(1, "Request for user"),
    SUCCESS(0, "Success"),
    WRONG_OWNER(-1, "Not the picture owner"),
    FATAL(-10, "Fatal");

    private final int status;
    private final String desc;

    PictureStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    public int getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
}
