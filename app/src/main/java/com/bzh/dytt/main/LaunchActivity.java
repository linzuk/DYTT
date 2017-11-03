package com.bzh.dytt.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bzh.common.dto.SerializableMap;
import com.bzh.common.utils.DeviceUtils;
import com.bzh.common.utils.SPUtils;
import com.bzh.data.repository.Repository;
import com.bzh.data.repository.RetrofitManager;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by linzuk on 2017/11/3.
 */
public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //后台处理耗时任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 耗时任务
                RetrofitManager.initBaseUrl();
                initConfig();
            }
        }).start();
    }

    private void initConfig() {
        LaunchActivity.ConfigSubscriber configSubscriber = new LaunchActivity.ConfigSubscriber();
        Repository.getInstance().getConfig(DeviceUtils.getUniqueId(this))
                .doOnSubscribe(configSubscriber)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(configSubscriber);
    }

    private class ConfigSubscriber implements Action0, Action1<Map<String, String>> {
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
            startMainActivity(config);
        }
    }

    private void startMainActivity(final Map<String, String> config) {
        // 耗时任务结束，启动 MainActivity
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //跳转至 MainActivity
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                intent.putExtra("config", new SerializableMap(config));
                startActivity(intent);
                //结束当前的 Activity
                LaunchActivity.this.finish();
            }
        });
    }
}
