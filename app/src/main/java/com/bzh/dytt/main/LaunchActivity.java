package com.bzh.dytt.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bzh.common.utils.UrlKit;
import com.bzh.data.repository.RetrofitManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;


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
                // 耗时任务结束，启动 MainActivity
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //跳转至 MainActivity
                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(intent);
                        //结束当前的 Activity
                        LaunchActivity.this.finish();
                    }
                });
            }
        }).start();
    }
}
