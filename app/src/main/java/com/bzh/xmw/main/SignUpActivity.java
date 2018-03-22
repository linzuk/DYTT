package com.bzh.xmw.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bzh.common.utils.DeviceUtils;
import com.bzh.common.utils.SPUtils;
import com.bzh.common.utils.ToastKit;
import com.bzh.data.repository.Repository;
import com.bzh.xmw.R;
import com.bzh.xmw.base.ComService;

import org.jsoup.helper.StringUtil;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linzuk on 2018/3/22.
 * 输入推荐码的页面
 */

public class SignUpActivity extends Activity {

    private EditText txtInviteCode;
    private Button btnEnterApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtInviteCode = (EditText) findViewById(R.id.txt_invite_code);
        btnEnterApp = (Button) findViewById(R.id.btn_enter_app);

        btnEnterApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inviteCode = txtInviteCode.getText().toString();
                SignUpActivity.SignUpSubscriber signUpSubscriber = new SignUpActivity.SignUpSubscriber();
                Repository.getInstance().signUp(DeviceUtils.getUniqueId(SignUpActivity.this), inviteCode)
                        .doOnSubscribe(signUpSubscriber)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(signUpSubscriber);
            }
        });
    }

    private class SignUpSubscriber implements Action0, Action1<Map<String, String>> {
        @Override
        public void call() {

        }

        @Override
        public void call(Map<String, String> config) {
            for (String key : config.keySet()) {
                String value = config.get(key);
                SPUtils.putShareData(key, value);
            }
            // 最后一个动作结束之后启动主页
            String errorMessage = config.get("error_message");
            if (StringUtil.isBlank(errorMessage)) {
                ComService.goToActivity(SignUpActivity.this, MainActivity.class);
            } else {
                ToastKit.showToast(SignUpActivity.this, errorMessage);
            }
        }
    }

    private void startMainActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //跳转至 MainActivity
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                //结束当前的 Activity
                SignUpActivity.this.finish();
            }
        });
    }

}
