package com.bzh.xmw.film;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.xmw.R;
import com.bzh.xmw.base.basic.BaseActivity;
import com.bzh.xmw.base.basic.BaseFragment;
import com.bzh.xmw.base.refresh_recyclerview.RefreshRecyclerPresenter;
import com.bzh.xmw.base.refresh_recyclerview.RefreshRecyclerView;
import com.bzh.xmw.detail.DetailFragment;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExRecyclerView;
import com.bzh.recycler.ExViewHolder;

import java.util.ArrayList;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public abstract class BaseFilmInfoPresenter extends RefreshRecyclerPresenter<BaseInfoEntity, ArrayList<BaseInfoEntity>> implements SwipeRefreshLayout.OnRefreshListener, ExCommonAdapter.OnItemClickListener, ExRecyclerView.OnLoadMoreListener {

    public BaseFilmInfoPresenter(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {
        super(baseActivity, baseFragment, iView);
    }

    @Override
    public ExCommonAdapter<BaseInfoEntity> getExCommonAdapter() {
        return new ExCommonAdapter<BaseInfoEntity>(getBaseActivity(), R.layout.item_film) {
            @Override
            protected void convert(ExViewHolder viewHolder, BaseInfoEntity item) {
                // TODO 列表页面填充数据
                viewHolder.setText(R.id.tv_film_name, item.getVideoTitle());
                ImageView tag2 = viewHolder.getView(R.id.iv_item_tag2);

                tag2.setVisibility(View.GONE);

                if (item.getFree()) { // 免费
                    tag2.setImageResource(R.mipmap.ic_free);
                    tag2.setVisibility(View.VISIBLE);
                } else { // VIP
                    tag2.setImageResource(R.mipmap.ic_vip);
                    tag2.setVisibility(View.VISIBLE);
                }

                ImageView imageView = viewHolder.getView(R.id.img_file_image);
                Glide.with(getContext())
                        .load(item.getImageUrl())
                        .into(imageView);
            }
        };
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        BaseInfoEntity baseInfoEntity = getCommonAdapter().getData().get(position);
        DetailFragment.launch(getBaseActivity(), baseInfoEntity.getViewkey());
    }
}
