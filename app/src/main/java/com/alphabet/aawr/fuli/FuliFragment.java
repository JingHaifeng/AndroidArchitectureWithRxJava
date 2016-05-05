package com.alphabet.aawr.fuli;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphabet.aawr.R;
import com.alphabet.aawr.data.FuliData;
import com.alphabet.aawr.detail.FuliDetailActivity;
import com.alphabet.aawr.fuli.widget.ViewPagerLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by alphabet on 5/2/16.
 */
public class FuliFragment extends Fragment implements FuliContract.View {

    private FuliContract.Presenter mPresenter;

    private boolean isLoading;

    private boolean isAllCompeleted;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading)
    ContentLoadingProgressBar mLoadingView;

    @BindView(R.id.empty_tv)
    TextView mEmptyTv;

    private Adapter mAdapter;
    private Unbinder mUnbinder;
    private LinearLayoutManager mLinearLayoutManager;

    public FuliFragment() {

    }

    public static FuliFragment newInstance() {
        return new FuliFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fuli, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        mLinearLayoutManager = new ViewPagerLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = getLastVisiblePosition(recyclerView);
                if (!isAllCompeleted && !isLoading && lastItem + 1 == mAdapter.getItemCount()) {
                    mPresenter.loadNextPage();
                }
            }
        });
        mAdapter = new Adapter(this, mEmptyTv);
        mRecyclerView.setAdapter(mAdapter);

        mEmptyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadPage(1);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void setPresenter(FuliContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean indicator) {
        isLoading = indicator;
        if (isLoading) {
            mLoadingView.show();
            if (mEmptyTv.getVisibility() == View.VISIBLE) {
                mEmptyTv.setVisibility(View.GONE);
            }
        } else {
            mLoadingView.hide();
        }

        Logger.d("setLoadingIndicator : " + indicator);
    }

    @Override
    public void setDataList(List<FuliData> fuliDataList) {
        mAdapter.setFuliDatas(fuliDataList);
    }

    @Override
    public void allCompleted(boolean completed) {
        isAllCompeleted = completed;
        Logger.d("allCompleted : " + completed);
    }

    @Override
    public void showMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void startFuliDetailActivity(final View transitView, final FuliData fuliData) {
//        Glide.with(this).load(fuliData.url).listener(new RequestListener<String, GlideDrawable>() {
//            @Override
//            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                Toast.makeText(getContext(), fuliData.url + " load failed", Toast.LENGTH_SHORT);
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        Intent intent = FuliDetailActivity.newIntent(getContext(), fuliData);
        ActivityOptionsCompat optionsCompat
                = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(), transitView, FuliDetailActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(getActivity(), intent,
                    optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
//                return false;
//            }
//        });

//        Glide.with(getActivity())
//                .load(fuliData.url)
//                .asBitmap()
//                .dontAnimate()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .listener(new RequestListener<String, Bitmap>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                        Logger.d("onException");
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        Logger.d("onResourceReady");
//                        return false;
//                    }
//                });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.pic)
        ImageView mPic;

        FuliData mFuliData;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            Logger.d("ItemViewHolder created");
        }

        @Override
        public void onClick(View v) {
            Logger.d("ViewHolder onClick per");
            if (mFuliData != null) {
                startFuliDetailActivity(mPic, mFuliData);
            }
        }
    }

    private class Adapter extends RecyclerView.Adapter<ItemViewHolder> {

        private List<FuliData> mFuliDatas;
        private Fragment mFragment;
        private View mEmptyView;

        public Adapter(Fragment fragment, View emptyView) {
            mFuliDatas = new ArrayList<>();
            mFragment = fragment;
            mEmptyView = emptyView;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fuli, parent, false);
            return new ItemViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            FuliData fuliData = mFuliDatas.get(position);
            holder.mFuliData = fuliData;
            Glide.with(mFragment)
                    .load(fuliData.url)
                    .into(holder.mPic);
        }

        @Override
        public int getItemCount() {
            return mFuliDatas.size();
        }

        public void setFuliDatas(List<FuliData> fuliDatas) {
            mFuliDatas = fuliDatas;
            mEmptyView.setVisibility(fuliDatas.isEmpty() ? View.VISIBLE : View.GONE);
            notifyDataSetChanged();
        }

    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition(RecyclerView recyclerView) {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = recyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }
}
