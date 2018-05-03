package com.example.arch_lib;

/**
 * Created by Lee on 2018/5/2.
 * <p>
 * 异步回调接口
 */

public interface ModuleCallBack<T> {

    void onModuleCallBack(ModuleResult<T> result);

}
