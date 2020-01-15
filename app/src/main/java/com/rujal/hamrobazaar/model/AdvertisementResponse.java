package com.rujal.hamrobazaar.model;

import com.rujal.hamrobazaar.Advertisement;

import java.util.List;

public class AdvertisementResponse {

    private String status;
    private String message;
    private List<Advertisement> advertisements;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
