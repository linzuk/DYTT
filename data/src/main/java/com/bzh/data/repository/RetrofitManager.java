package com.bzh.data.repository;

import android.content.Context;

import com.bzh.common.context.GlobalContext;
import com.bzh.common.utils.SPUtils;
import com.bzh.common.utils.UrlKit;
import com.bzh.data.film.IFilmService;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class RetrofitManager {

    private final IFilmService filmService;
    private static RetrofitManager retrofitManager;

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    public static String initBaseUrl() {
        String baseUrl = SPUtils.getShareData("BASE_URL");
        if (null == baseUrl || "".equals(baseUrl)) {
            try {
                Future<String> future = EXECUTOR.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Document doc = Jsoup.connect("http://www.jianshu.com/users/4fb989f0f0dd/timeline")
                                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                                .get();
                        Element div = doc.body().getElementsByClass("js-intro").get(0);
                        return div.text().trim();
                    }
                });
                String encodeBaseUrl = future.get();
                baseUrl = UrlKit.decodeUrl(encodeBaseUrl);
                SPUtils.putShareData("BASE_URL", baseUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baseUrl;
    }

    private RetrofitManager(Context context) {

        int cacheSize = 10 * 1024 * 1024;

        String cachePath;
        if (null == context) {
            cachePath = "cache.dytt";
        } else {
            cachePath = context.getCacheDir().getAbsolutePath() + File.separator + "cache.dytt";
        }
        Cache cache = new Cache(new File(cachePath), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        initBaseUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPUtils.getShareData("BASE_URL"))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();


        filmService = retrofit.create(IFilmService.class);
    }

    public static RetrofitManager getInstance() {
        RetrofitManager tmp = retrofitManager;
        if (tmp == null) {
            synchronized (RetrofitManager.class) {
                tmp = retrofitManager;
                if (tmp == null) {
                    if (GlobalContext.getInstance() != null) {
                        tmp = new RetrofitManager(GlobalContext.getInstance());
                    } else {
                        tmp = new RetrofitManager(null);
                    }
                    retrofitManager = tmp;
                }
            }
        }
        return tmp;
    }

    public IFilmService getFilmService() {
        return filmService;
    }

}
