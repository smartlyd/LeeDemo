package com.example.lydha.leedemo.module;

import com.example.arch_lib.BaseModule;
import com.example.arch_lib.ModuleCall;
import com.example.arch_lib.ProxyTarget;
import com.example.lydha.leedemo.entity.LoginInfoBean;
import com.example.lydha.leedemo.moduleimpl.UserModuleImpl;

/**
 * Created by Lee on 2018/5/2.
 */
@ProxyTarget(UserModuleImpl.class)
public interface UserModule extends BaseModule {

    String getUserName();

    boolean isLogin();

    ModuleCall<LoginInfoBean> login(String userName, String password);


}
