package com.bzh.dytt.detail;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bzh.common.utils.DeviceUtils;
import com.bzh.common.utils.SPUtils;
import com.bzh.data.film.DetailEntity;
import com.bzh.data.repository.Repository;
import com.bzh.data.ticket.TicketValidateEntity;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-4-3<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DetailPresenter extends PagePresenter implements View.OnClickListener {


    private final IDetailView filmDetailView;
    private String id;
    private DetailEntity detailEntity;
    private TicketValidateEntity ticketValidateEntity;

    public DetailPresenter(BaseActivity baseActivity, BaseFragment baseFragment, IDetailView filmDetailView) {
        super(baseActivity, baseFragment, filmDetailView);
        this.filmDetailView = filmDetailView;
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == android.R.id.home) {
            getBaseActivity().finish();
        } else if (v.getId() == R.id.fab) {
            // TODO 点击下载按钮处理逻辑
            Toast.makeText(getBaseActivity(), "哎呀，别点了，点坏了要负责哦～", Toast.LENGTH_SHORT).show();
//            if (detailEntity != null && detailEntity.getDownloadUrls().size() > 0) {
//                if (detailEntity.getDownloadUrls().size() == 1) {
//                    ThunderHelper.getInstance(getBaseActivity()).onClickDownload(v, detailEntity.getDownloadUrls().get(0));
//                } else {
//                    new MaterialDialog.Builder(getBaseActivity())
//                            .title("选择下载连接")
//                            .items(detailEntity.getDownloadNames())
//                            .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
//                                @Override
//                                public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
//                                    ThunderHelper.getInstance(getBaseActivity()).onClickDownload(v, detailEntity.getDownloadUrls().get(which));
//                                    return true;
//                                }
//                            })
//                            .positiveText("下载")
//                            .show();
//                }
//            }
        }
    }

    @Override
    public void initFragmentConfig() {
        if (null != baseFragment.getArguments()) {
            // 初始化电影详情数据
            id = baseFragment.getArguments().getString(DetailFragment.FILM_ID);
            if (!TextUtils.isEmpty(id)) {
                FilmDetailTaskSubscriber detailTaskSubscriber = new FilmDetailTaskSubscriber();
                Repository.getInstance().getFilmDetail(id)
                        .doOnSubscribe(detailTaskSubscriber)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(detailTaskSubscriber);
            }

            // 初始化电影券校验数据
            validateTicket();
        }
        filmDetailView.initToolbar();
        filmDetailView.initFab();
    }

    // 初始化电影券校验数据
    public void validateTicket() {
        TicketValidateTaskSubscriber ticketTaskSubscriber = new TicketValidateTaskSubscriber();
        Repository.getInstance().validateTicket(SPUtils.getShareData("TICKET", ""), DeviceUtils.getUniqueId(getBaseActivity()))
                .doOnSubscribe(ticketTaskSubscriber)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ticketTaskSubscriber);
    }

    private class FilmDetailTaskSubscriber extends AbstractTaskSubscriber<DetailEntity> {
        @Override
        public void onSuccess(DetailEntity detailEntity) {
            super.onSuccess(detailEntity);
            DetailPresenter.this.detailEntity = detailEntity;
            updateFileDetailStatus();
        }
    }

    private class TicketValidateTaskSubscriber extends AbstractTaskSubscriber<TicketValidateEntity> {
        @Override
        public void onSuccess(TicketValidateEntity ticketValidateEntity) {
            super.onSuccess(ticketValidateEntity);
            DetailPresenter.this.ticketValidateEntity = ticketValidateEntity;
            updateTicketValidateStatus();
        }
    }

    private void updateFileDetailStatus() {
        filmDetailView.setFilmDetail(detailEntity);
    }

    private void updateTicketValidateStatus() {
        filmDetailView.setTicketValidateEntity(ticketValidateEntity);
    }
}
