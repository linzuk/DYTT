package com.bzh.data.film;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.ticket.TicketValidateEntity;

import java.util.ArrayList;
import java.util.Map;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-18<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IFilmDataStore {

    Observable<ArrayList<BaseInfoEntity>> getFileList(final String category, @IntRange(from = 1, to = 2147483647) int pi, final String deviceId);

    Observable<DetailEntity> getFilmDetail(final String viewkey, final String ticket, final String deviceId);

    Observable<TicketValidateEntity> validateTicket(final String ticket, final String deviceId);

    Observable<Map<String, String>> getConfig(final String deviceId);

    Observable<String> downloadFilm(final String viewkey, final String deviceId);

    Observable<Map<String, String>> signUp(final String deviceId, final String inviteCode);
}
