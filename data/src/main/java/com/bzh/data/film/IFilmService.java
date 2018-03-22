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
    @GET("/api/rr162/listVideo")
    Observable<ResponseBody> getFilmList(@Query("pi") @IntRange(from = 1, to = 2147483647) int pi, @Query("category") String category, @Query("device_id") String deviceId);

    // 获取电影详情
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/rr162/getVideo")
    Observable<ResponseBody> getFilmDetail(@Query("viewkey") String viewkey, @Query("ticket") String ticket, @Query("device_id") String deviceId);

    // 验证电影券能不能用
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/validateTicketV2")
    Observable<ResponseBody> validateTicket(@Query("ticket") String ticket, @Query("device_id") String deviceId);

    // 获取配置信息
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/getConfigV2")
    Observable<ResponseBody> getConfig(@Query("device_id") String deviceId);

    // 设备请求下载电影
    // 获取配置信息
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/downloadFilm")
    Observable<ResponseBody> downloadFilm(@Query("device_id") String deviceId, @Query("viewkey") String viewkey);

    // 用户登入
    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/api/vc/signUp")
    Observable<ResponseBody> signUp(@Query("device_id") String deviceId, @Query("invite_code") String inviteCode);
}
