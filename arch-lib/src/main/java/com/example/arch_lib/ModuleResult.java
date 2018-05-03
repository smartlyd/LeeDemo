package com.example.arch_lib;

/**
 * Created by Lee on 2018/5/2.
 * <p>
 * module 异步回调结果
 */

public class ModuleResult<T> {
    public final T data;
    public final Throwable error;

    public ModuleResult(T data, Throwable e) {
        this.data = data;
        this.error = e;
    }
}
