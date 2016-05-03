package com.alphabet.aawr.fuli;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.alphabet.aawr.api.ApiManager;
import com.alphabet.aawr.api.GankApi;
import com.alphabet.aawr.data.FuliData;
import com.alphabet.aawr.data.HttpResult;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by alphabet on 5/2/16.
 */
public class FuliPresenter implements FuliContract.Presenter {

    private CompositeSubscription mSubscriptions;

    private FuliContract.View mFuliView;

    private List<FuliData> mCache;

    private GankApi mGankApi;

    private int mPage;

    private boolean isAllCompelted;

    public FuliPresenter(@NonNull FuliContract.View fuliView) {
        mFuliView = fuliView;
        mFuliView.setPresenter(this);
        mCache = new ArrayList<>();
        mSubscriptions = new CompositeSubscription();
        mGankApi = ApiManager.getInstance().getGankApi();
    }

    @Override
    public void loadPage(final int page) {
        Logger.d("begin load page : " + page);
        mFuliView.setLoadingIndicator(true);
        Subscription subscription = mGankApi.getFuliPageData(page)
                .map(new Func1<HttpResult<FuliData>, List<FuliData>>() {
                    @Override
                    public List<FuliData> call(HttpResult<FuliData> dataHttpResult) {
                        return dataHttpResult.results;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FuliData>>() {
                    @Override
                    public void onCompleted() {
                        mPage = page;
                        Logger.d("Completed!");
                        mFuliView.setLoadingIndicator(false);
                        mFuliView.showMessage("加载成功:)");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("e:" + e.getMessage());
                        mFuliView.setDataList(new ArrayList<>(mCache));
                        mFuliView.setLoadingIndicator(false);
                        mFuliView.showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(List<FuliData> fuliDataList) {
                        if (fuliDataList.isEmpty() || fuliDataList.size() < GankApi.PAGE_SIZE) {
                            isAllCompelted = true;
                        } else {
                            isAllCompelted = false;
                        }
                        for (FuliData fuliData : fuliDataList) {
                            Logger.d(fuliData.toString());
                        }
                        mCache.addAll(fuliDataList);
                        mFuliView.allCompleted(isAllCompelted);
                        mFuliView.setDataList(new ArrayList<>(mCache));
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void loadNextPage() {
        loadPage(mPage + 1);
    }

    @Override
    public void subscribe() {
        if (mCache.isEmpty()) {
            loadPage(1);
        } else {
            mFuliView.setDataList(new ArrayList<>(mCache));
        }
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

}
