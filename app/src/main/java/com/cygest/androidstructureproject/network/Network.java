package com.cygest.androidstructureproject.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create retrofit instance
 */
public class Network {

    public static final String URL = "your-api-url-here";

    private static ApiService instance;


    public static ApiService getInstance() {
        ApiService apiService = instance;

        OkHttpClient client = new OkHttpClient.Builder()
            /* For Authorization */
            .addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                .header("Authorization", "Your-authorization-here").build()))
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();

        if (apiService != null) {
            return apiService;
        }

        /* Singleton */
        synchronized (ApiService.class) {
            if (instance == null) {
                instance = new Retrofit.Builder().baseUrl(URL)
                    /* Gson converter for serialization / deserialization */
                    .addConverterFactory(GsonConverterFactory.create())
                    /* RxJava3 integration */
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client)
                    .build()
                    .create(ApiService.class);
            }
            return instance;
        }
    }
}
