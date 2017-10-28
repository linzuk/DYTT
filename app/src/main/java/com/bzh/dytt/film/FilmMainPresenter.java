package com.bzh.dytt.film;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;
import com.bzh.dytt.base.tablayoutview.TabLayoutFragment;
import com.bzh.dytt.base.tablayoutview.TabLayoutPresenter;

import java.util.ArrayList;

/**
 * main
 */
public class FilmMainPresenter extends TabLayoutPresenter implements IFragmentPresenter {

    public static final String NEWEST_FILM = "newest_film";
    public static final String JAPAN_SOUTH_KOREA_FILM = "japan_south_korea_film";
    public static final String EUROPE_AMERICA_FILM = "europe_america_film";
    public static final String COMIC_FILM = "comic_film";
    public static final String VR_FILM = "vr_film";



    public FilmMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {
        super(baseActivity, baseFragment, filmMainIView);
    }

    @Override
    public BaseFragment newFragment(StripTabItem stripTabItem) {
        switch (stripTabItem.getType()) {
            case NEWEST_FILM:
                return NewestFilmFragment.newInstance();
            case JAPAN_SOUTH_KOREA_FILM:
                return JSKFilmFragment.newInstance();
            case EUROPE_AMERICA_FILM:
                return EAFilmFragment.newInstance();
            case COMIC_FILM:
                return DomesticFilmFragment.newInstance();
            case VR_FILM:
                return VRFilmFragment.newInstance();
        }
        return NewestFilmFragment.newInstance();
    }

    @Override
    public ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        items.add(new StripTabItem(NEWEST_FILM, "热门"));
        items.add(new StripTabItem(JAPAN_SOUTH_KOREA_FILM, "日韩"));
        items.add(new StripTabItem(EUROPE_AMERICA_FILM, "欧美"));
        items.add(new StripTabItem(COMIC_FILM, "动漫"));
        items.add(new StripTabItem(VR_FILM, "VR"));
        return items;
    }
}
