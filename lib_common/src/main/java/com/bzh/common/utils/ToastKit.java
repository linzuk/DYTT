package com.bzh.common.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by linzuk on 2018/3/18.
 */

public class ToastKit {

    public static void showToast(final Activity activity, final String message) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
