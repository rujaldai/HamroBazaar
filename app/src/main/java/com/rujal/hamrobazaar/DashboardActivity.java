package com.rujal.hamrobazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView, recyclerViewSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewSecond = findViewById(R.id.recyclerViewSecond);

        List<Advertisement> trendingAds = new ArrayList<>();
        trendingAds.add(new Advertisement("Car", "400000", R.drawable.car, "Brand New"));
        trendingAds.add(new Advertisement("Bike", "400000", R.drawable.bike, "Brand New"));
        trendingAds.add(new Advertisement("Car", "400000", R.drawable.car, "Brand New"));
        trendingAds.add(new Advertisement("Car", "400000", R.drawable.car, "Brand New"));

        AdvertisementAdapter trendingAdsAdapter = new AdvertisementAdapter(this, trendingAds);
        recyclerView.setAdapter(trendingAdsAdapter);
        recyclerView.setLayoutManager(
                (new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)));


        List<Advertisement> listedAds = new ArrayList<>();
        listedAds.add(new Advertisement("Car", "400000", R.drawable.car, "Brand New"));
        listedAds.add(new Advertisement("Bike", "400000", R.drawable.bike, "Brand New"));
        listedAds.add(new Advertisement("Car", "400000", R.drawable.car, "Brand New"));
        listedAds.add(new Advertisement("Car", "400000", R.drawable.car, "Brand New"));
        listedAds.add(new Advertisement("Car", "400000", R.drawable.car, "Brand New"));

        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(this, listedAds);
        recyclerViewSecond.setAdapter(advertisementAdapter);
        recyclerViewSecond.setLayoutManager(
                (new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)));


        int images[] = {R.drawable.yamaha, R.drawable.car, R.drawable.bike, R.drawable.house, R.drawable.furnitures, R.drawable.music};


        for (int image : images) {
            slideImages(image);
        }
    }

    public void slideImages(int image) {
        ViewFlipper vflipper = findViewById(R.id.vflipper);


        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        vflipper.addView(imageView);
        vflipper.setFlipInterval(4000);
        vflipper.setAutoStart(true);

        //animation
        vflipper.setInAnimation(this, android.R.anim.slide_in_left);
        vflipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }
}