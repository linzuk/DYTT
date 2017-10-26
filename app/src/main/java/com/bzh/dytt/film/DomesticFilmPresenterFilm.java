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
 * 动漫
 */
public class DomesticFilmPresenterFilm extends BaseFilmInfoPresenter {

    public DomesticFilmPresenterFilm(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {
        super(baseActivity, baseFragment, iView);
    }

    public Observable<ArrayList<BaseInfoEntity>> getRequestListDataObservable(String nextPage) {
        return Repository.getInstance().getFileList("/categories/hentai", Integer.valueOf(nextPage), DeviceUtils.getUniqueId(getBaseActivity()));
    }

    @Override
    public String getMaxPage() {
        return 500 + "";
    }
}
