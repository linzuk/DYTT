package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;

/**
 * 欧美
 */
public class VRFilmFragment extends RefreshRecyclerFragment implements BaseFilmInfoIView {

    public static VRFilmFragment newInstance() {
        Bundle args = new Bundle();
        VRFilmFragment fragment = new VRFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected RefreshRecyclerPresenter initRefreshRecyclerPresenter() {
        return new VRFilmPresenterFilm(getBaseActivity(), this, this);
    }
}
