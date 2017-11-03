package com.bzh.dytt.film;

import com.bzh.common.utils.DeviceUtils;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerView;

import java.util.ArrayList;

import rx.Observable;

/**
 * 电影
 */
public class NewestFilmPresenterFilm extends BaseFilmInfoPresenter {

    private String type;
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NewestFilmPresenterFilm(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView, String type, String title) {
        super(baseActivity, baseFragment, iView);
        setType(type);
        setTitle(title);
    }

    public Observable<ArrayList<BaseInfoEntity>> getRequestListDataObservable(String nextPage) {
        return Repository.getInstance().getFileList(getType(), Integer.valueOf(nextPage), DeviceUtils.getUniqueId(getBaseActivity()));
    }

    @Override
    public String getMaxPage() {
        return 1000 + "";
    }
}
