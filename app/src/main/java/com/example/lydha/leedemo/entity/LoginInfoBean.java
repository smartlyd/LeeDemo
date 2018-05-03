package com.example.lydha.leedemo.entity;

import com.example.lydha.leedemo.base.BaseBean;

/**
 * Created by Lee on 2018/5/2.
 */

public class LoginInfoBean extends BaseBean {

    public String userName;

    public boolean success;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
