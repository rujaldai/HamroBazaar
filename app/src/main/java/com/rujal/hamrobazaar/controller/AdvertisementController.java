package com.rujal.hamrobazaar.controller;

import com.rujal.hamrobazaar.Advertisement;
import com.rujal.hamrobazaar.api.AdvertisementApi;
import com.rujal.hamrobazaar.model.AdvertisementResponse;
import com.rujal.hamrobazaar.url.Url;

import java.io.IOException;

import retrofit2.Call;

public class AdvertisementController {

    public Call<AdvertisementResponse> getAdvertisements() {
        AdvertisementApi advertisementApi = Url.getInstance().create(AdvertisementApi.class);
        Call<AdvertisementResponse> advertisementCall = advertisementApi.getAll();


        return advertisementCall;

    }

    public static Call<AdvertisementResponse> addAdvertisement(Advertisement advertisement) {
        AdvertisementApi advertisementApi = Url.getInstance().create(AdvertisementApi.class);
        Call<AdvertisementResponse> advertisementCall = advertisementApi.addAdvertisement(advertisement);
        try {
            advertisementCall.execute();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return advertisementCall;

    }
}
