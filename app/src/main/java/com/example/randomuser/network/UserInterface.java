package com.example.randomuser.network;

import com.example.randomuser.model.ApiResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserInterface {
//    @GET("/api/")
//    Call<ApiResponse> fetchUsers(@Query("results") int results,
//                                 @Query("nat") String nat);

    @GET("/api/")
    Single<ApiResponse> fetchUsers(@Query("results") int results,
                                   @Query("nat") String nat);
}
