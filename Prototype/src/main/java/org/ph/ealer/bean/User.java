package org.ph.ealer.bean;

public class User {
    private long uid;
    private String username;
    private String password;
    private long tel;
    private String email;

    public User(long uid, String username, String password, long tel, String email) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.email = email;
    }

    public User(String username, String password, long tel, String email) {
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.email = email;
    }

    public User(long uid, String username){
        this.uid = uid;
        this.username = username;
    }

    public User(){

    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel=" + tel +
                ", email='" + email + '\'' +
                '}';
    }
}
