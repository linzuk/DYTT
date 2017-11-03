package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;

/**
 * 电影
 */
public class NewestFilmFragment extends RefreshRecyclerFragment implements BaseFilmInfoIView {

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

    public static NewestFilmFragment newInstance(String type, String title) {
        Bundle args = new Bundle();
        NewestFilmFragment fragment = new NewestFilmFragment();
        fragment.setArguments(args);
        fragment.setType(type);
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected RefreshRecyclerPresenter initRefreshRecyclerPresenter() {
        return new NewestFilmPresenterFilm(getBaseActivity(), this, this, getType(), getTitle());
    }
}
