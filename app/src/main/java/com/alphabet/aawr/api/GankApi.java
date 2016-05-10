package com.alphabet.aawr.api;

import com.alphabet.aawr.daily.data.BaseData;
import com.alphabet.aawr.daily.data.DailyData;
import com.alphabet.aawr.daily.data.HistoryData;
import com.alphabet.aawr.daily.data.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by alphabet on 5/2/16.
 */
public interface GankApi {
    public static final String BASE_URL = "http://gank.io/api/";
    public static final int PAGE_SIZE = 5;

    @GET("data/福利/" + GankApi.PAGE_SIZE + "/{page}")
    Observable<HttpResult<BaseData>> getDailyPageData(@Path("page") int page);

    @GET("day/history")
    Observable<HistoryData> getHistoryData();

    @GET("day/{year}/{month}/{day}")
    Observable<DailyData> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
