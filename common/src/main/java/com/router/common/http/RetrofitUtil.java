package com.router.common.http;

import com.router.common.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * retrofit 工具封装
 */
public class RetrofitUtil {
    private static RetrofitUtil retrofitUtil;
    private Retrofit retrofit;

    private RetrofitUtil(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
//                .addInterceptor(new RequestIntercept(new GlobeHttpHandler() {
//                    @Override
//                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
//                        return response;
//                    }
//
//                    @Override
//                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
//                        return request;
//                    }
//                }))
//                .addInterceptor(new TokenInterceptor())
//                .cache(cache)
                ;
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitUtil get() {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
