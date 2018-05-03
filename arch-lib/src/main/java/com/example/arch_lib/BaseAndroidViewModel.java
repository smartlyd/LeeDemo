package com.example.arch_lib;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

/**
 * Created by Lee on 2018/5/2.
 */

public class BaseAndroidViewModel extends AndroidViewModel {

    private ModuleDelegate mDelegate = new ModuleDelegate();


    public BaseAndroidViewModel(Application application) {
        super(application);
    }


    protected <T extends BaseModule> T getModule(Class<T> moduleClass) {
        return mDelegate.getModule(moduleClass);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
