package com.bzh.dytt.film;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bzh.common.utils.SPUtils;
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


    public FilmMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {
        super(baseActivity, baseFragment, filmMainIView);
    }

    @Override
    public BaseFragment newFragment(StripTabItem stripTabItem) {
        return NewestFilmFragment.newInstance(stripTabItem.getType(), stripTabItem.getTitle());
    }

    @Override
    public ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        String json = SPUtils.getShareData("film_category");
        JSONArray array = JSONArray.parseArray(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String type = obj.getString("type");
            String title = obj.getString("title");
            items.add(new StripTabItem(type, title));
        }
        return items;
    }
}
