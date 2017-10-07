package com.bzh.dytt.film;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerView;

import java.util.ArrayList;

import rx.Observable;

/**
 * 欧美
 */
public class EAFilmPresenterFilm extends BaseFilmInfoPresenter {


    public EAFilmPresenterFilm(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {
        super(baseActivity, baseFragment, iView);
    }

    public Observable<ArrayList<BaseInfoEntity>> getRequestListDataObservable(String nextPage) {
        return Repository.getInstance().getEuropeAmerica(Integer.valueOf(nextPage));
    }

    @Override
    public String getMaxPage() {
        return 147 + "";
    }
}
