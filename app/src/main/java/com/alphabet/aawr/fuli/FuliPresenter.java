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

import java.util.ArrayList;
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

    public FuliPresenter(@NonNull FuliContract.View fuliView) {
        mFuliView = fuliView;
        mFuliView.setPresenter(this);
        mCache = new ArrayList<>();
        mSubscriptions = new CompositeSubscription();
        mGankApi = ApiManager.getInstance().getGankApi();
    }

    @Override
    public void loadPage(int page) {
        mPage = page;

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
                        mFuliView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<FuliData> fuliDataList) {
                        mCache.addAll(fuliDataList);
                        mFuliView.setDataList(new ArrayList<>(mCache));
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void loadNextPage() {
        loadPage(++mPage);
    }

    @Override
    public void subscribe() {
        if (mCache.isEmpty()) {
            loadPage(1);
        }
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

}
