package com.rujal.hamrobazaar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginController {

    public boolean validateLoginCredentials(String email, String password) {
        UserApi userApi = Url.getInstance().create(UserApi.class);
        Call<SignUpResponse> usersCall = userApi.checkUser(email, password);

        try {
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Login Successful!")) {

                Url.token += loginResponse.body().getToken();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
