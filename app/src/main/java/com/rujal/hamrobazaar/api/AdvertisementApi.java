package com.rujal.hamrobazaar.api;

import com.rujal.hamrobazaar.Advertisement;
import com.rujal.hamrobazaar.model.AdvertisementResponse;
import com.rujal.hamrobazaar.model.SignUpResponse;
import com.rujal.hamrobazaar.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AdvertisementApi {

    @POST("advertisement/add")
    Call<AdvertisementResponse> addAdvertisement(@Body Advertisement advertisement);

    @GET("advertisement/get")
    Call<AdvertisementResponse> getAll();

}
