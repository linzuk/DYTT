package com.bzh.dytt.detail;

import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzh.common.utils.AesKit;
import com.bzh.common.utils.SPUtils;
import com.bzh.data.film.DetailEntity;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.FragmentArgs;
import com.bzh.dytt.base.basic.FragmentContainerActivity;
import com.bzh.dytt.base.basic_pageswitch.PageFragment;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;

import org.jsoup.helper.StringUtil;

import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class DetailFragment extends PageFragment implements IDetailView {

    public static final String FILM_ID = "FILM_ID";

    public static void launch(BaseActivity from, String id) {
        FragmentArgs fragmentArgs = new FragmentArgs();
        fragmentArgs.add(FILM_ID, id);
        FragmentContainerActivity.launch(from, DetailFragment.class, fragmentArgs);
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.film_poster)
    ImageView filmPoster;

    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    // 预览图
    @Bind(R.id.film_detail_preview)
    ImageView film_detail_preview;

    // 内容
    @Bind(R.id.film_detail_content)
    TextView film_detail_content;

    // 视频播放器
    @Bind(R.id.film_detail_film)
    JCVideoPlayerStandard film_detail_film;

    // 输入电影票控件
    @Bind(R.id.input_ticket_layout)
    LinearLayout input_ticket_layout;
    @Bind(R.id.tv_ticket_tip)
    TextView tv_ticket_tip;
    @Bind(R.id.tv_ticket_goto_buy)
    TextView tv_ticket_goto_buy;
    @Bind(R.id.et_ticket)
    EditText et_ticket;
    @Bind(R.id.iv_film_preview)
    ImageView iv_film_preview;

    // 隐藏观影券图层
    private void hideInputTicketLayout() {
        input_ticket_layout.setVisibility(View.GONE);
    }

    // 显示视频图层
    private void showInputTicketLayout() {
        input_ticket_layout.setVisibility(View.VISIBLE);
    }

    // 隐藏视频图层
    private void hideVideoLayout() {
        film_detail_film.setVisibility(View.GONE);
    }

    // 显示视频图层
    private void showVideoLayout() {
        film_detail_film.setVisibility(View.VISIBLE);
    }

    private DetailPresenter detailPresenter;

    @Override
    protected PagePresenter initPresenter() {
        detailPresenter = new DetailPresenter(getBaseActivity(), this, this);
        return detailPresenter;
    }

    @Override
    public void initFab() {
        fab.setOnClickListener(detailPresenter);
    }

    public void setText(TextView textView, LinearLayout layout, String str) {
        textView.setText(TextUtils.isEmpty(str) ? "" : str);
        layout.setVisibility(TextUtils.isEmpty(str) ? View.GONE : View.VISIBLE);
    }

    /**
     * 点击确定后执行
     */
    @OnClick(R.id.btn_ticket_ok)
    public void onClickBtnTicketOk(View v) {
        String ticket = et_ticket.getText().toString();
        if (!StringUtil.isBlank(ticket)) {
            SPUtils.putShareData("TICKET", ticket);
            detailPresenter.initFragmentConfig();
        }
    }

    @Override
    public void setFilmDetail(DetailEntity detailEntity) {
        // TODO 设置电影详情页面数据
        collapsingToolbar.setTitle(detailEntity.getTitle());
        film_detail_content.setText(detailEntity.getContent());

        String videoUrl = AesKit.decryptAES(detailEntity.getVideo());

        film_detail_film.setUp(videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, detailEntity.getTitle());
        film_detail_film.thumbImageView.setImageURI(Uri.parse(detailEntity.getImage()));

        Glide.with(this)
                .load(detailEntity.getCover())
                .into(filmPoster);

        Glide.with(this)
                .load(detailEntity.getPreview())
                .into(film_detail_preview);

        Glide.with(this)
                .load(detailEntity.getImage())
                .into(iv_film_preview);

        // TODO 设置页面是否已经支付的效果
        if (detailEntity.getTicketOk() || detailEntity.getFree()) {
            hideInputTicketLayout(); // 隐藏观影券图层
            showVideoLayout();
        } else {
            hideVideoLayout(); // 隐藏视频图层
            showInputTicketLayout();
            Date expireTime = detailEntity.getExpireTime();
            if (null == expireTime) {
                tv_ticket_tip.setText("您尚未购买观影券");
            } else {
                tv_ticket_tip.setText("您的观影券已经过期");
            }
            tv_ticket_goto_buy.setText(Html.fromHtml("<div>请输入观影券，没有观影券点击<a href='"+SPUtils.getShareData("shop_url")+"'>[前往购买]</a></div>"));
            tv_ticket_goto_buy.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }


    @Override
    public void initToolbar() {
        getBaseActivity().setSupportActionBar(toolbar);
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getBaseActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_film_detail;
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v) {
        detailPresenter.onClick(v);
    }
}
