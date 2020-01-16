package com.rujal.hamrobazaar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rujal.hamrobazaar.controller.AdvertisementController;
import com.rujal.hamrobazaar.model.AdvertisementResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView, recyclerViewSecond;
    private ImageView login;
    private ViewFlipper vFlipper;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getIntent().getStringExtra("imagePath") != null) {
            setProfilePicture(getIntent().getStringExtra("imagePath"));
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewSecond = findViewById(R.id.recyclerViewSecond);

        login = findViewById(R.id.login);

        login.setOnClickListener(i -> new LoginDialog().show(getSupportFragmentManager(), "Login"));

        CustomStrictMode.strictMode();

        // add advertisements into database
        addAdvertisements();

        //fetch from database
        List<Advertisement> advertisements = getAllAdvertisements();
        System.out.println("ADvertose,emt jere \n\n\n\n\n\n\n\n");
        advertisements.forEach(System.out::println);
        List<Advertisement> trendingAds = advertisements.stream()
                .filter(i -> "trending".equalsIgnoreCase(i.getType()))
                .distinct()
                .collect(Collectors.toList());
        List<Advertisement> listedAds = advertisements.stream()
                .filter(i -> "listed".equalsIgnoreCase(i.getType()))
                .distinct()
                .collect(Collectors.toList());

        AdvertisementAdapter trendingAdsAdapter = new AdvertisementAdapter(this, trendingAds);
        recyclerView.setAdapter(trendingAdsAdapter);
        recyclerView.setLayoutManager(
                (new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)));

        AdvertisementAdapter advertisementAdapter = new AdvertisementAdapter(this, listedAds);
        recyclerViewSecond.setAdapter(advertisementAdapter);
        recyclerViewSecond.setLayoutManager(
                (new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)));


        int[] images = {R.drawable.bike, R.drawable.house, R.drawable.yamaha, R.drawable.car, R.drawable.furnitures, R.drawable.music};


        for (int image : images) {
            slideImages(image);
        }
    }

    private List<Advertisement> getAllAdvertisements() {
        AdvertisementController advertisementController = new AdvertisementController();

        Call<AdvertisementResponse> response = advertisementController.getAdvertisements();

        try {
            Response<AdvertisementResponse> advertisementResponse = response.execute();
            AdvertisementResponse advertisement = advertisementResponse.body();
            if (advertisement != null) {
                return advertisement.getAdvertisements();
            } else {
                return new ArrayList<>();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new ArrayList<>();
    }

    private void addAdvertisements() {
        List<Advertisement> advertisements = Arrays.asList(
                new Advertisement("Furniture", "400000", String.valueOf(R.drawable.furnitures), "Used", "Trending"),
                new Advertisement("House", "900000", String.valueOf(R.drawable.house), "Brand New", "Listed"),
                new Advertisement("Car", "10000", String.valueOf(R.drawable.car), "Used", "Trending"),
                new Advertisement("Scooter", "50000", String.valueOf(R.drawable.bike), "Like New", "Listed"),
                new Advertisement("Furniture", "400000", String.valueOf(R.drawable.furnitures), "Used", "Trending"),
                new Advertisement("House", "900000", String.valueOf(R.drawable.house), "Brand New", "Listed"),
                new Advertisement("Car", "10000", String.valueOf(R.drawable.car), "Used", "Listed"),
                new Advertisement("Scooter", "50000", String.valueOf(R.drawable.bike), "Like New", "Listed"));

        advertisements.forEach(AdvertisementController::addAdvertisement);
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
        return true;
    }

    public void setProfilePicture(String path) {
        File imgFile = new File(path);

        System.out.println(imgFile.canRead());
        System.out.println(imgFile.getAbsolutePath());

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = findViewById(R.id.login);

            myImage.setImageBitmap(myBitmap);

        }
    }

}