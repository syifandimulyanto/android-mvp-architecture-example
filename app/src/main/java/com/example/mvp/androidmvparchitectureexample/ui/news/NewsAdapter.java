package com.example.mvp.androidmvparchitectureexample.ui.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mvp.androidmvparchitectureexample.R;
import com.example.mvp.androidmvparchitectureexample.data.local.entities.ArticleEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ali DOUIRI on 25/04/2018.
 * my.alidouiri@gmail.com
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleHolder> {

    private static final String TAG = NewsAdapter.class.getSimpleName();

    private List<ArticleEntity> mItems;
    private Context mContext;
    private final OnItemClickListener mListener;

    public NewsAdapter(Context context, List<ArticleEntity> items, OnItemClickListener listener) {

        this.mContext = context;
        this.mItems = items;
        this.mListener = listener;
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        RelativeLayout mContainer;
        @BindView(R.id.thumbnail)
        ImageView mThumbnail;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.progress)
        ProgressBar mProgress;

        public ArticleHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        return new ArticleHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {

        final ArticleEntity mItem = mItems.get(position);

        if (mItem != null)
        {

            if (mItem.getTitle() != null)
                holder.mTitle.setText(mItem.getTitle());

            if (mItem.getDescription() != null)
                holder.mDescription.setText(mItem.getDescription());

            if (mItem.getUrlToImage() != null)
            {

                Glide
                        .with(mContext)
                        .load(mItem.getUrlToImage())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                if (holder.mProgress != null)
                                    holder.mProgress.setVisibility(View.GONE);

                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                if (holder.mProgress != null)
                                    holder.mProgress.setVisibility(View.GONE);

                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .crossFade()
                        .into(holder.mThumbnail);
            }else{

                holder.mProgress.setVisibility(View.GONE);

                Glide
                        .with(mContext)
                        .load(R.drawable.news_placeholder)
                        .into(holder.mThumbnail);


            }

        }

        holder.mContainer.setOnClickListener(view -> mListener.onItemClick(mItem));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<ArticleEntity> mItems) {

        if (mItems != null && !mItems.isEmpty()) {

            this.mItems = mItems;
            notifyDataSetChanged();
        }

    }

    public interface OnItemClickListener {
        void onItemClick(ArticleEntity item);
    }
}

