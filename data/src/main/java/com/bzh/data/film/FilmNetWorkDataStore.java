package com.bzh.data.film;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.basic.DataStoreController;
import com.bzh.data.ticket.TicketValidateEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class FilmNetWorkDataStore implements IFilmDataStore {

    private final IFilmService iFilmService;

    public FilmNetWorkDataStore(IFilmService iFilmService) {
        this.iFilmService = iFilmService;
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getDomestic(@IntRange(from = 1, to = 2147483647) int pi) {
        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getFilmList(pi, "comic"));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewest(@IntRange(from = 1, to = 2147483647) final int pi) {
        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getFilmList(pi, "newest"));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmerica(@IntRange(from = 1, to = 2147483647) int pi) {
        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getFilmList(pi, "europe"));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 2147483647) int pi) {
        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getFilmList(pi, "japan"));
    }

    @Override
    public Observable<DetailEntity> getFilmDetail(final String filmId) {
        return DataStoreController.getInstance().getNewWorkDetailObservable(iFilmService.getFilmDetail(filmId));
    }

    @Override
    public Observable<TicketValidateEntity> validateTicket(final String ticket, final String deviceId) {
        return DataStoreController.getInstance().validateTicketObservable(iFilmService.validateTicket(ticket, deviceId));
    }

    @Override
    public Observable<Map<String, String>> getConfig() {
        return DataStoreController.getInstance().getConfigObservable(iFilmService.getConfig());
    }
}
