package com.develop.machinecomm.Machinecomm.dto;

import java.io.Serializable;

public class LogDTO implements Serializable {

    private String message;
    private boolean logstatus;
    private String user;
    private String userId;
    private String userRFID;
    private String userStatus;
    private boolean userSubscribed;
    private String userRec;
    private String userPicture;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLogstatus() {
        return logstatus;
    }

    public void setLogstatus(boolean logstatus) {
        this.logstatus = logstatus;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = String.valueOf(userId);
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isUserSubscribed() {
        return userSubscribed;
    }

    public void setUserSubscribed(boolean userSubscribed) {
        this.userSubscribed = userSubscribed;
    }

    public String getUserRec() {
        return userRec;
    }

    public void setUserRec(String userRec) {
        this.userRec = userRec;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
}