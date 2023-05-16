package org.ph.ealer.status;

public enum UserStatus {
    SUCCESS(0, "Success"),
    NO_SUCH_USER_ERROR(-1, "No such user"),
    WRONG_PASSWORD(-2, "Wrong password"),
    WRONG_VERIFY_CODE(-3, "Wrong verify code"),
    EMPTY_ERROR(-4, "String is empty"),
    PATTERN_ERROR(-5, "String is not in pattern"),
    USER_EXIST(-6, "User is already exist in database"),
    FATAL(-10, "Fatal");

    private final int status;
    private final String desc;

    UserStatus(int status, String desc) {
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
