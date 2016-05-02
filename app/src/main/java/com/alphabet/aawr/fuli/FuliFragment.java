package com.alphabet.aawr.fuli;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.alphabet.aawr.R;
import com.alphabet.aawr.data.FuliData;
import com.bumptech.glide.Glide;

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

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading)
    ContentLoadingProgressBar mLoadingView;

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
        mLinearLayoutManager = new LinearLayoutManager(getContext(),
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
                if (lastItem + 1 == mAdapter.getItemCount()) {
                    mPresenter.loadNextPage();
                }
            }
        });
        mAdapter = new Adapter(this);
        mRecyclerView.setAdapter(mAdapter);
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
        } else {
            mLoadingView.hide();
        }
    }

    @Override
    public void setDataList(List<FuliData> fuliDataList) {
        mAdapter.setFuliDatas(fuliDataList);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pic)
        ImageView mPic;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ItemViewHolder> {

        private List<FuliData> mFuliDatas;

        private Fragment mFragment;


        public Adapter(Fragment fragment) {
            mFuliDatas = new ArrayList<>();
            mFragment = fragment;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fuli, parent, false);
            return new ItemViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            FuliData fuliData = mFuliDatas.get(position);
            Glide.with(mFragment)
                    .load(fuliData.url)
                    .placeholder(null)
                    .into(holder.mPic);
        }

        @Override
        public int getItemCount() {
            return mFuliDatas.size();
        }

        public void setFuliDatas(List<FuliData> fuliDatas) {
            mFuliDatas = fuliDatas;
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