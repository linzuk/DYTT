package com.bzh.data.film;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.basic.DataStoreController;
import com.bzh.data.ticket.TicketValidateEntity;

import java.util.ArrayList;
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
    public Observable<ArrayList<BaseInfoEntity>> getFileList(final String category, @IntRange(from = 1, to = 2147483647) int pi, final String deviceId) {
        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getFilmList(pi, category, deviceId));
    }

    @Override
    public Observable<DetailEntity> getFilmDetail(final String viewkey, final String ticket, final String deviceId) {
        return DataStoreController.getInstance().getNewWorkDetailObservable(iFilmService.getFilmDetail(viewkey, ticket, deviceId));
    }

    @Override
    public Observable<TicketValidateEntity> validateTicket(final String ticket, final String deviceId) {
        return DataStoreController.getInstance().validateTicketObservable(iFilmService.validateTicket(ticket, deviceId));
    }

    @Override
    public Observable<Map<String, String>> getConfig(final String deviceId) {
        return DataStoreController.getInstance().getConfigObservable(iFilmService.getConfig(deviceId));
    }

    @Override
    public Observable<String> downloadFilm(String viewkey, String deviceId) {
        return DataStoreController.getInstance().downloadFilmObservable(iFilmService.downloadFilm(viewkey, deviceId));
    }
}
