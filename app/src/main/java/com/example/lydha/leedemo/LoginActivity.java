package com.example.lydha.leedemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arch_lib.ModuleResult;
import com.example.lydha.leedemo.base.BaseActivity;
import com.example.lydha.leedemo.entity.LoginInfoBean;

/**
 * Created by Lee on 2018/5/2.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private LoginViewModule mLoginViewModel;
    private Button mLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        // step0 获取相关的viewModel
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModule.class);

        // step1 获取view
        mLoginBtn = (Button) findViewById(R.id.loginBtn);

        // step2 设置监听
        mLoginBtn.setOnClickListener(this);

        // setp3 绑定数据
        mLoginViewModel.loginResult.observe(this, mLoginObserver);
    }

    private Observer<ModuleResult<LoginInfoBean>> mLoginObserver = new Observer<ModuleResult<LoginInfoBean>>() {
        @Override
        public void onChanged(@Nullable ModuleResult<LoginInfoBean> result) {
            Toast.makeText(getApplicationContext(), "data=" + result.data + " e=" + result.error, Toast.LENGTH_SHORT).show();
        }
    };


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                mLoginViewModel.login("fusang", "abc123456");
                break;

        }
    }


}
