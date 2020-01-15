package com.rujal.hamrobazaar.controller;

import com.rujal.hamrobazaar.api.UserApi;
import com.rujal.hamrobazaar.model.SignUpResponse;
import com.rujal.hamrobazaar.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginController {

    public Response<SignUpResponse> login(String email, String password) throws IOException {
        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<SignUpResponse> usersCall = userApi.checkUser(email, password);

        return usersCall.execute();


    }
}
