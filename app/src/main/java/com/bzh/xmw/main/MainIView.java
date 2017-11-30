package com.bzh.xmw.main;

import android.support.v4.view.PagerAdapter;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-21<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface MainIView {

    void setHeaderViewBackground(String url);

    void setHeadView(String url);

    void initToolbar(String title);

    void setTitle(String title);

    void initDrawerToggle();

    void initContainer(PagerAdapter pagerAdapter, int limit);

    void setCurrentItem(int item);

    void closeDrawer();

    void setNavigationItemSelectedListener(MainPresenter mainA);
}
