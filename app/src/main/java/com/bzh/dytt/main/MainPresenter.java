package com.bzh.dytt.main;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Toast;

import com.bzh.common.utils.DeviceUtils;
import com.bzh.common.utils.SPUtils;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IActivityPresenter;
import com.bzh.dytt.film.FilmMainFragment;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-21<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class MainPresenter implements IActivityPresenter, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainPresenter";

    public static final String FILM = "film";

    private final BaseActivity baseActivity;
    private final MainIView iMainView;
    private InnerPageAdapter innerPageAdapter;
    private ArrayList<String> items;
    private Map<String, BaseFragment> fragments;
    private Map<String, String> config;

    public MainPresenter(BaseActivity baseActivity, MainIView iMainView) {
        this.baseActivity = baseActivity;
        this.iMainView = iMainView;
        fragments = new HashMap<>();
        items = new ArrayList<>();
        items.add(FILM);      // 电影
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 从服务器获取配置信息
        ConfigSubscriber configSubscriber = new ConfigSubscriber();
        Repository.getInstance().getConfig(DeviceUtils.getUniqueId(baseActivity))
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
        public void call(Map<String, String> map) {
            config = map;
            updateConfigStatus();
        }
    }

    private void updateConfigStatus() {
        iMainView.initToolbar(config.get("tool_bar"));
        iMainView.initDrawerToggle();
        iMainView.setNavigationItemSelectedListener(this);
        innerPageAdapter = new InnerPageAdapter(baseActivity.getSupportFragmentManager());
        iMainView.initContainer(innerPageAdapter, items.size());
        iMainView.setHeadView(config.get("head_view"));
        for (String key : config.keySet()) {
            String value = config.get(key);
            SPUtils.putShareData(key, value);
        }
        // 显示一句话
        String show_a_toast = config.get("show_a_toast");
        if (!StringUtil.isBlank(show_a_toast)) {
            Toast.makeText(baseActivity, show_a_toast, Toast.LENGTH_LONG);
        }
        // 检查更新
        String version = getVersionName();
        if (null != version && !getVersionName().equalsIgnoreCase(config.get("version"))) {
            // TODO: 更新APP
            new AlertDialog.Builder(baseActivity)
                    .setTitle("检测到新版本")
                    .setMessage("是否立即更新？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.setData(Uri.parse(config.get("app_download_url")));
                            baseActivity.startActivity(intent);
                        }
                    })
                    .setNegativeButton("否", null)
                    .show();
        }
    }

    private String getVersionName() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = baseActivity.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(baseActivity.getPackageName(),0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_copy:
                ClipboardManager cm = (ClipboardManager) baseActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(SPUtils.getShareData("app_download_url"));
                Toast.makeText(baseActivity, "已经复制app下载地址，赶快分享给你到好基友吧 ^_^", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_buy:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(SPUtils.getShareData("shop_url")));
                baseActivity.startActivity(intent);
                break;
        }
        iMainView.closeDrawer();
        return true;
    }

    private class InnerPageAdapter extends FragmentPagerAdapter {

        public InnerPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = fragments.get(items.get(position));
            if (fragment == null) {
                fragment = newFragment(items.get(position));
                fragments.put(items.get(position), fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    private BaseFragment newFragment(String item) {
        switch (item) {
            case FILM:
                return FilmMainFragment.newInstance();
        }
        throw new RuntimeException("没有指定类型的Fragment");
    }
}
