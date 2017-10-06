package com.bzh.dytt.film;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bzh.common.utils.UIUtils;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerView;
import com.bzh.dytt.detail.DetailFragment;
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
                viewHolder.setText(R.id.tv_film_name, item.getTitle());
                viewHolder.setText(R.id.tv_film_publish_time, item.getPublishDate());
                final ImageView imageView = viewHolder.getView(R.id.img_file_image);
                Glide.with(getContext())
                        .load(item.getImage())
                        .asBitmap()
                        .into(new BitmapImageViewTarget(imageView) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                super.setResource(resource);
//                                int width = resource.getWidth();
//                                int height = resource.getHeight();
//                                float ratio = width * 1.0F / height;
//                                float targetHeight = UIUtils.getScreenWidth() * 1.0F / ratio;

//                                ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                                params.height = (int) targetHeight;
//                                imageView.setLayoutParams(params);
                                imageView.setImageBitmap(resource);
                            }
                        });
            }
        };
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        BaseInfoEntity baseInfoEntity = getCommonAdapter().getData().get(position);
        DetailFragment.launch(getBaseActivity(), baseInfoEntity.getId());
    }
}
