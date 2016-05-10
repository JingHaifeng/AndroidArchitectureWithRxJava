package com.alphabet.aawr.daily;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.alphabet.aawr.api.ApiManager;
import com.alphabet.aawr.api.GankApi;
import com.alphabet.aawr.daily.data.DailyData;
import com.alphabet.aawr.daily.data.HistoryData;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by alphabet on 5/2/16.
 */
public class DailyPresenter implements DailyContract.Presenter {

    private static final int PAGE_SIZE = 5;

    private CompositeSubscription mSubscriptions;

    private DailyContract.View mView;

    private List<DailyData> mCache;

    private SparseArray<DayIndex> mDayIndexSparseArray;

    private GankApi mGankApi;

    private int mPage = 0;

    private int mMaxPage;

    private int mMaxSize;

    private boolean isAllCompelted = false;

    public DailyPresenter(@NonNull DailyContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCache = new ArrayList<>();
        mSubscriptions = new CompositeSubscription();
        mGankApi = ApiManager.getInstance().getGankApi();
    }

    @Override
    public void loadPage(final int page) {
        if (mDayIndexSparseArray == null || mDayIndexSparseArray.size() == 0) {
            loadHistory();
            return;
        }

        if (page < 0 || page > mMaxPage) {
            Logger.e("invalid page");
            return;
        }
        Logger.d("begin load page : " + page);
        mView.setLoadingIndicator(true);
        Observable.merge(getPageList(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyData>() {
                    private List<DailyData> mDailyDatas;

                    @Override
                    public void onCompleted() {
                        mCache.addAll(mDailyDatas);
                        mView.setDataList(new ArrayList<>(mCache));
                        mPage = page;
                        if (mPage == mMaxPage - 1) {
                            isAllCompelted = true;
                        } else {
                            isAllCompelted = false;
                            mView.showMessage("所有数据已加载");
                        }
                        mView.allCompleted(isAllCompelted);
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage("e:" + e.getMessage());
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onNext(DailyData dailyData) {
                        if (mDailyDatas == null) {
                            mDailyDatas = new ArrayList<DailyData>();
                        }
                        mDailyDatas.add(dailyData);
                    }
                });
    }

    private List<Observable<DailyData>> getPageList(int page) {
        int offset = page * PAGE_SIZE;
        int start = offset;
        int end = start + PAGE_SIZE - 1;
        List<Observable<DailyData>> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            DayIndex index = mDayIndexSparseArray.get(i);
            list.add(mGankApi.getDailyData(index.year, index.month, index.day));
        }
        return list;
    }

    @Override
    public void loadNextPage() {
        loadPage(mPage + 1);
    }

    @Override
    public void subscribe() {

        if (mCache.isEmpty()) {
            // 首先获取当前的历史
            loadPage(mPage);
        } else {
            mView.setDataList(new ArrayList<DailyData>(mCache));
        }
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    static class DayIndex {
        int year;
        int month;
        int day;

        public DayIndex(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    /**
     * 加载历史列表
     */
    private void loadHistory() {
        Subscription subscription = mGankApi.getHistoryData()
                .map(new Func1<HistoryData, SparseArray<DayIndex>>() {
                    @Override
                    public SparseArray<DayIndex> call(HistoryData historyData) {
                        SparseArray<DayIndex> dayIndexList = new SparseArray<DayIndex>();
                        String[] index;
                        int i = 0;
                        for (String s : historyData.mDateList) {
                            index = s.split("-");
                            dayIndexList.put(i++, new DayIndex(Integer.valueOf(index[0]),
                                    Integer.valueOf(index[1]),
                                    Integer.valueOf(index[2])
                            ));
                        }
                        return dayIndexList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SparseArray<DayIndex>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("e:" + e.getMessage());
                        mView.setDataList(new ArrayList<DailyData>(mCache));
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onNext(SparseArray<DayIndex> dayIndices) {
                        Logger.d("onNext");
                        mDayIndexSparseArray = dayIndices;
                        mMaxSize = mDayIndexSparseArray.size();
                        mMaxPage = mMaxSize / PAGE_SIZE + (mMaxPage % PAGE_SIZE) > 0 ? 1 : 0;
                        mPage = 0;
                        loadPage(mPage);
                    }
                });

        mSubscriptions.add(subscription);
    }
}
