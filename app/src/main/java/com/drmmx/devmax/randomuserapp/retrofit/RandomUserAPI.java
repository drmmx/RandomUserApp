package com.drmmx.devmax.randomuserapp.retrofit;


import com.drmmx.devmax.randomuserapp.model.RandomUser;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserAPI {

    @GET("api/")
    Observable<RandomUser> getUsers(@Query("seed") String seed,
                                    @Query("inc") String inc,
                                    @Query("page") int page,
                                    @Query("results") int results);
}
