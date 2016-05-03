package com.alphabet.aawr.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alphabet on 5/2/16.
 */
public class ApiManager {

    private volatile static ApiManager sInstance = new ApiManager();
    public static final long TIME_OUT = 5; //SECONDS

    private OkHttpClient mOkHttpClient;
    private GankApi mGankApi;

    private ApiManager() {
        mOkHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    public static ApiManager getInstance() {
        return sInstance;
    }

    public synchronized GankApi getGankApi() {
        if (mGankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(GankApi.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mGankApi = retrofit.create(GankApi.class);
        }
        return mGankApi;
    }
}
