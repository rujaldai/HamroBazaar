package com.rujal.hamrobazaar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView, recyclerViewSecond;
    private ImageView login;
    private ViewFlipper vFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewSecond = findViewById(R.id.recyclerViewSecond);

        login = findViewById(R.id.login);

        login.setOnClickListener(i -> new LoginDialog().show(getSupportFragmentManager(), "Login"));

        List<Advertisement> trendingAds = Arrays.asList(
                new Advertisement("Car", "400000", R.drawable.car, "Brand New"),
                new Advertisement("Bike", "400000", R.drawable.bike, "Brand New"),
                new Advertisement("Car", "400000", R.drawable.car, "Brand New"),
                new Advertisement("Car", "400000", R.drawable.car, "Brand New"));

        AdvertisementAdapter trendingAdsAdapter = new AdvertisementAdapter(this, trendingAds);
        recyclerView.setAdapter(trendingAdsAdapter);
        recyclerView.setLayoutManager(
                (new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)));


        List<Advertisement> listedAds = Arrays.asList(
                new Advertisement("Car", "400000", R.drawable.car, "Brand New"),
                new Advertisement("Bike", "400000", R.drawable.bike, "Brand New"),
                new Advertisement("Car", "400000", R.drawable.car, "Brand New"),
                new Advertisement("Car", "400000", R.drawable.car, "Brand New"));

        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(this, listedAds);
        recyclerViewSecond.setAdapter(advertisementAdapter);
        recyclerViewSecond.setLayoutManager(
                (new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)));


        int[] images = {R.drawable.bike, R.drawable.house, R.drawable.yamaha, R.drawable.car, R.drawable.furnitures, R.drawable.music};


        for (int image : images) {
            slideImages(image);
        }
    }

    public void slideImages(int image) {
        vFlipper = findViewById(R.id.vFlipper);

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        vFlipper.addView(imageView);
        vFlipper.setFlipInterval(4000);
        vFlipper.setAutoStart(true);

        //animation
        vFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        vFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return false;
    }
}