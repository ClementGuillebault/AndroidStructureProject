package com.cygest.androidstructureproject.network;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Put your function to call api here.
 */
public interface ApiService {

    /**
     * Exemple of GET request
     * @param obj integer your data
     * @return Single<Object>
     */
    @GET("/your-endpoint-here")
    Single<Object> getData(@Query("obj") int obj);

    /**
     * Example of POST request with multipart.
     * @param data Object your data
     * @return Single<Object>
     */
    @Multipart
    @POST("/your-endpoint-here")
    Single<Object> getData(@Part("data") Object data);
}
