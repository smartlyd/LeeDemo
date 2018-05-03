package com.example.lydha.leedemo.moduleimpl;

import com.example.arch_lib.BaseModuleImpl;
import com.example.lydha.leedemo.entity.LoginInfoBean;

import io.reactivex.Observable;


/**
 * Created by Lee on 2018/5/2.
 */

public class UserModuleImpl extends BaseModuleImpl {

    public String getUserName() {
        return "lee";
    }

    public boolean isLogin() {
        return true;
    }


    public Observable<LoginInfoBean> login(String name, String pwd) {
        // 模拟实现
        LoginInfoBean result = new LoginInfoBean();
        result.success = true;
        result.userName = name;

        LoginInfoBean result2 = new LoginInfoBean();
        result2.success = false;
        result2.userName = pwd;
        return Observable.just(result, result2);

    }

}
