package com.bzh.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bzh.common.context.D;

import java.util.List;

/**
 * Created by linzuk on 2018/3/18.
 */

public class ShareKit {

    /**
     * 上下文
     */
    private Activity activity;

    /**
     * 文本类型
     *
     */
    public static int TEXT = 0;

    /**
     * 图片类型
     */
    public static int DRAWABLE = 1;

    public ShareKit(Activity activity) {
        this.activity = activity;
    }

    private void startActivity(Intent intent) {
        // 开始分享
        activity.startActivityForResult(intent, D.REQ_CODE_START_QQ);
        SPUtils.putLongShareData(D.START_SHARE_TIME, System.currentTimeMillis());
    }

    /**
     * 分享到QQ好友
     *  @param msgTitle
     *            (分享标题)
     * @param msgText
     *            (分享内容)
     * @param type
     *            (分享类型)
     * @param drawable
     */
    public boolean shareQQFriend(String msgTitle, String msgText, int type,
                                 Bitmap drawable) {

        return shareMsg("com.tencent.mobileqq",
                "com.tencent.mobileqq.activity.JumpActivity", "QQ", msgTitle,
                msgText, type, drawable);
    }


    /**
     * 分享到微信好友
     *  @param msgTitle
     *            (分享标题)
     * @param msgText
     *            (分享内容)
     * @param type
     *            (分享类型)
     * @param drawable
     */
    public boolean shareWeChatFriend(String msgTitle, String msgText, int type,
                                     Bitmap drawable) {

        return shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI", "微信",
                msgTitle, msgText, type, drawable);
    }

    /**
     * 分享到微信朋友圈(分享朋友圈一定需要图片)
     *  @param msgTitle
     *            (分享标题)
     * @param msgText
     *            (分享内容)
     * @param drawable
     */
    public boolean shareWeChatFriendCircle(String msgTitle, String msgText,
                                           Bitmap drawable) {

        return shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI",
                "微信", msgTitle, msgText, ShareKit.DRAWABLE, drawable);
    }

    /**
     * 点击分享的代码
     *  @param packageName
     *            (包名,跳转的应用的包名)
     * @param activityName
     *            (类名,跳转的页面名称)
     * @param appname
     *            (应用名,跳转到的应用名称)
     * @param msgTitle
     *            (标题)
     * @param msgText
     *            (内容)
     * @param type
     */
    @SuppressLint("NewApi")
    private boolean shareMsg(String packageName, String activityName,
                             String appname, String msgTitle, String msgText, int type,
                             Bitmap drawable) {

        if (!isInstall(packageName, appname)) return false;

        Intent intent = new Intent("android.intent.action.SEND");
        if (type == ShareKit.TEXT) {
            intent.setType("text/plain");
        } else if (type == ShareKit.DRAWABLE) {
            intent.setType("image/*");
//          BitmapDrawable bd = (BitmapDrawable) drawable;
//          Bitmap bt = bd.getBitmap();
            final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                    activity.getContentResolver(), drawable, null, null));
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }

        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        if (!packageName.isEmpty()) {
            intent.setComponent(new ComponentName(packageName, activityName));
            startActivity(intent);
        } else {
            startActivity(Intent.createChooser(intent, msgTitle));
        }
        return true;
    }

    // 检查是否已经安装了要跳转的 App
    private boolean isInstall(String packageName, String appname) {
        if (!packageName.isEmpty() && !isAvilible(activity, packageName)) {// 判断APP是否存在
            Toast.makeText(activity, "请先安装" + appname, Toast.LENGTH_SHORT)
                    .show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断相对应的APP是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if ((pinfo.get(i)).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /****************
     * 参考文章：http://blog.csdn.net/foolfalcon/article/details/42469435
     *
     * 发起添加群流程。群号：文明开车(691880093) 的 key 为： uWaX2rFw6X7MpIaP4NSDijLmeGax0f1Z
     * 调用 joinQQGroup(uWaX2rFw6X7MpIaP4NSDijLmeGax0f1Z) 即可发起手Q客户端申请加群 文明开车(691880093)
     *
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String mqqopensdkapi, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(mqqopensdkapi + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

}
