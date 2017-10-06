package com.bzh.data.film;

import android.support.annotation.IntRange;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　电影<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IFilmService {

    // 获取电影列表
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/listVideo")
    Observable<ResponseBody> getFilmList(@Query("pi") @IntRange(from = 1, to = 2147483647) int pi, @Query("type") String type);

    // 获取电影详情
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/getVideo")
    Observable<ResponseBody> getFilmDetail(@Query("id") String filmId);

    // 验证电影券能不能用
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/validateTicket")
    Observable<ResponseBody> validateTicket(@Query("ticket") String ticket, @Query("device_id") String deviceId);

    // 获取配置信息
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/getConfig")
    Observable<ResponseBody> getConfig();
}
