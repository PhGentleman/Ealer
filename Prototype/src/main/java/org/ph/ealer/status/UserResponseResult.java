package org.ph.ealer.status;

public class UserResponseResult<T> {
    private UserStatus code;
    private String msg;
    private T Data;

    public UserResponseResult(UserStatus code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        Data = data;
    }

    public UserResponseResult(UserStatus code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public UserStatus getCode() {
        return code;
    }

    public void setCode(UserStatus code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
