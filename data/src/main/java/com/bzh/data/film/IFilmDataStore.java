package com.bzh.data.film;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.ticket.TicketValidateEntity;

import java.util.ArrayList;
import java.util.List;
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

    Observable<ArrayList<BaseInfoEntity>> getDomestic(@IntRange(from = 1, to = 2147483647) int pi);

    Observable<ArrayList<BaseInfoEntity>> getNewest(@IntRange(from = 1, to = 2147483647) final int pi);

    Observable<ArrayList<BaseInfoEntity>> getEuropeAmerica(@IntRange(from = 1, to = 2147483647) int pi);

    Observable<ArrayList<BaseInfoEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 2147483647) int pi);

    Observable<DetailEntity> getFilmDetail(final String filmStr);

    Observable<TicketValidateEntity> validateTicket(final String ticket, final String deviceId);

    Observable<Map<String, String>> getConfig();
}
