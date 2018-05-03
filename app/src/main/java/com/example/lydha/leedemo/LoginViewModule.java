package com.example.lydha.leedemo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.example.arch_lib.BaseAndroidViewModel;
import com.example.arch_lib.ModuleCall;
import com.example.arch_lib.ModuleCallBack;
import com.example.arch_lib.ModuleResult;
import com.example.lydha.leedemo.entity.LoginInfoBean;
import com.example.lydha.leedemo.module.UserModule;

/**
 * Created by Lee on 2018/5/2.
 */

public class LoginViewModule extends BaseAndroidViewModel {

    public final MutableLiveData<ModuleResult<LoginInfoBean>> loginResult = new MutableLiveData<>();

    public LoginViewModule(Application application) {
        super(application);
    }

    public void login(String username, String password) {
        ModuleCall<LoginInfoBean> loginCall = getModule(UserModule.class).login(username, password);
        loginCall.enqueue(new ModuleCallBack<LoginInfoBean>() {
            @Override
            public void onModuleCallBack(ModuleResult<LoginInfoBean> result) {
                loginResult.setValue(result);
            }
        });
    }


    public Boolean isLogin() {
        return getModule(UserModule.class).isLogin();
    }

    public String getUserName() {
        return getModule(UserModule.class).getUserName();
    }
}
