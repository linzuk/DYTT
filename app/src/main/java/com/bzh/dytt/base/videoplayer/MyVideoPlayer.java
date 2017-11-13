package com.bzh.dytt.base.videoplayer;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bzh.dytt.R;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by linzuk on 2017/11/6.
 */

public class MyVideoPlayer extends JZVideoPlayerStandard {

    private ImageView btnRelay;
    private ImageView btnForward;

    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        btnRelay = (ImageView) findViewById(R.id.btn_relay);
        btnForward = (ImageView) findViewById(R.id.btn_forward);
        btnRelay.setOnClickListener(this);
        btnForward.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_video_player;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.btn_relay == v.getId()) {
            int progress = bottomProgressBar.getProgress() - 1;
            if (progress < 0) {
                progress = 0;
            }
            int seek = (int) ((1.0 * progress /  100) * getDuration());
            JZMediaManager.instance().mediaPlayer.seekTo(seek);
            bottomProgressBar.setProgress(progress);
        } else if (R.id.btn_forward == v.getId()) {
            int progress = bottomProgressBar.getProgress() + 1;
            if (progress > 100) {
                progress = 100;
            }
            int seek = (int) ((1.0 * progress /  100) * getDuration());
            JZMediaManager.instance().mediaPlayer.seekTo(seek);
            bottomProgressBar.setProgress(progress);
        }
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            btnRelay.setVisibility(View.VISIBLE);
            btnForward.setVisibility(View.VISIBLE);
        } else {
            btnRelay.setVisibility(View.INVISIBLE);
            btnForward.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro,
                                        int thumbImg, int bottomPro) {
        super.setAllControlsVisiblity(topCon, bottomCon, startBtn, loadingPro, thumbImg, bottomPro);
        // 跟随播放暂停图标显示
        btnRelay.setVisibility(startBtn);
        btnForward.setVisibility(startBtn);
    }

    @Override
    public void dissmissControlView() {
        if (currentState != CURRENT_STATE_NORMAL
                && currentState != CURRENT_STATE_ERROR
                && currentState != CURRENT_STATE_AUTO_COMPLETE) {
            if (getContext() != null && getContext() instanceof Activity) {
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 添加
                        btnRelay.setVisibility(View.INVISIBLE);
                        btnForward.setVisibility(View.INVISIBLE);
                        // super
                        bottomContainer.setVisibility(View.INVISIBLE);
                        topContainer.setVisibility(View.INVISIBLE);
                        startButton.setVisibility(View.INVISIBLE);
                        if (clarityPopWindow != null) {
                            clarityPopWindow.dismiss();
                        }
                        if (currentScreen != SCREEN_WINDOW_TINY) {
                            bottomProgressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onStateError() {
        super.onStateError();
        Toast.makeText(getContext(), "在线播放失败，请下载观看！", Toast.LENGTH_LONG).show();
    }


}
