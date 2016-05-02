package com.alphabet.aawr.api;

import com.alphabet.aawr.data.FuliData;
import com.alphabet.aawr.data.HttpResult;
import com.alphabet.aawr.data.TestData;

import retrofit2.Call;
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
    Call<TestData> getFuliDataTest(@Path("page") int page);

    @GET("data/福利/" + GankApi.PAGE_SIZE + "/{page}")
    Observable<TestData> getFuliDataTest2(@Path("page") int page);

    @GET("data/福利/" + GankApi.PAGE_SIZE + "/{page}")
    Observable<HttpResult<FuliData>> getFuliPageData(@Path("page") int page);
}
