package com.bzh.common.context;

import android.support.multidex.MultiDexApplication;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-17<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class GlobalContext extends MultiDexApplication {

    private static GlobalContext _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = this;
    }

    public static GlobalContext getInstance() {
        return _context;
    }
}
