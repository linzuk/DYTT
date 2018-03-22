package com.bzh.xmw.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.bzh.common.context.D;
import com.bzh.common.utils.SPUtils;
import com.bzh.common.utils.ShareKit;
import com.bzh.common.utils.ToastKit;
import com.bzh.log.Util;

import org.jsoup.helper.StringUtil;

/**
 * Created by linzuk on 2018/3/18.
 */

public class ComService {

    private static AlertDialog tipShareDialog = null;

    public static boolean shareToQQ(Activity activity) {
        // 获取分享话术
        String shareText = SPUtils.getShareData("share_text");
        if (StringUtil.isBlank(shareText)) {
            shareText = "这个app能让你兴奋一整天！\n" +
                    "下载地址：http://t.cn/RWjbeGC";
        }
        shareText += "\n邀请码：" + SPUtils.getShareData("invite_code", "34579231");
        // 分享到QQ
        return new ShareKit(activity).shareWeChatFriend("分享给微信好友", shareText, ShareKit.TEXT, null);
//        return new ShareKit(activity).shareQQFriend("分享给QQ好友", shareText, ShareKit.TEXT, null);
    }

    public static boolean joinQQGroup(Activity activity) {
        // 获取连接  http://qun.qq.com/join.html
        String api = SPUtils.getShareData("join_qq_api");
        String key = SPUtils.getShareData("join_qq_key");
        if (StringUtil.isBlank(api)) api = "mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D";
        if (StringUtil.isBlank(key)) key = "uWaX2rFw6X7MpIaP4NSDijLmeGax0f1Z";
        // 加入QQ群
        return new ShareKit(activity).joinQQGroup(api, key);
    }

    public static int getShareCount() {
        String today = Util.todayString();
        String shareKey = "share_count_" + today;
        return SPUtils.getIntShareData(shareKey, 0);
    }

    public static int shareCountPlusOne() {
        String today = Util.todayString();
        String shareKey = "share_count_" + today;
        int shareCount = getShareCount() + 1;
        SPUtils.putIntShareData(shareKey, shareCount);
        return shareCount;
    }

    public static boolean checkShare(final Activity activity) {
        int needShareCount = ComService.getNeedShareCount();
        int todayShareCount = ComService.getShareCount();
        if (todayShareCount < needShareCount) {
            cancelShareDialog();
            tipShareDialog = getDialogInstance(activity, "每日分享任务")
                    .setMessage("您尚未完成今日分享任务，今日已分享"+todayShareCount+"次，还需分享"+(needShareCount-todayShareCount)+"次")
                    .setPositiveButton("立即分享", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean ok = ComService.shareToQQ(activity);
                            if (!ok) {
                                checkShare(activity);
                            }
                        }
                    })
                    .show();
            return false; // 返回 false：不允许看，返回 true：允许看
        } else {
            cancelShareDialog();
            tipShareDialog = getDialogInstance(activity, "vip系统")
                    .setMessage("对不起，您当前非vip会员，无法使用vip功能，加群免费获取vip解锁版")
                    .setPositiveButton("加群", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean ok = ComService.joinQQGroup(activity);
                            if (!ok) {
                                checkShare(activity);
                            }
                        }
                    })
                    .show();
            return false; // 返回 false：不允许看，返回 true：允许看
        }
    }

    private static AlertDialog.Builder getDialogInstance(Activity activity, String titleString) {
        TextView title = new TextView(activity);
        title.setText(titleString);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(23);
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(activity);
        normalDialog.setCustomTitle(title);
        normalDialog.setCancelable(false);
        return normalDialog;
    }

    public static void checkShareTime(Activity activity) {
        int needShareCount = ComService.getNeedShareCount();
        long startShareTime = SPUtils.getLongShareData(D.START_SHARE_TIME, 0);
        SPUtils.putLongShareData(D.START_SHARE_TIME, 0);
        if (startShareTime > 0) {
            long time = System.currentTimeMillis() - startShareTime;
            Log.i("lzk", time + "毫秒");
            if (time < 5000) {
                ToastKit.showToast(activity, "取消分享或者分享同一个群是没用的哦！");
            } else {
                int shareCount = shareCountPlusOne();
                if (shareCount <= needShareCount) ToastKit.showToast(activity, "成功分享"+shareCount+"次");
            }
        }
    }

    public static void cancelShareDialog() {
        if (null != tipShareDialog) {
            tipShareDialog.cancel();
            tipShareDialog = null;
        }
    }

    public static int getNeedShareCount() {
        String needShareCount = SPUtils.getShareData("need_share_count", "5");
        return Integer.parseInt(needShareCount);
    }

    public static void goToActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
