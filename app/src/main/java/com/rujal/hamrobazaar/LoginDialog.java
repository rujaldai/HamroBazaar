package com.rujal.hamrobazaar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.rujal.hamrobazaar.controller.LoginController;
import com.rujal.hamrobazaar.model.SignUpResponse;
import com.rujal.hamrobazaar.url.Url;

import java.io.IOException;

import retrofit2.Response;

public class LoginDialog extends AppCompatDialogFragment {

    EditText etEmail, etPassword;
    Button btnLogin, btnForget, btnSignUp;
    TextView tvErrorMessage;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login, null);
        builder.setView(view)
                .setTitle("Login");
//                .setPositiveButton("Don't have account?", (dialog, which) -> {
//                    Intent intent = new Intent(getContext(), RegisterActivity.class);
//                    getContext().startActivity(intent);
//                })
//                .setNegativeButton("Cancel", (dialog, which) -> {
//                    Intent intent = new Intent(getContext(), DashboardActivity.class);
//                    getContext().startActivity(intent);
//                });

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnForget = view.findViewById(R.id.btnForgot);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnLogin = view.findViewById(R.id.btnRegister);

        tvErrorMessage = view.findViewById(R.id.tvErrorLoginMessage);
        tvErrorMessage.setText("");

        btnSignUp.setOnClickListener(i -> {
            System.out.println("Test sign up clicked");
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(i -> login());

        return builder.create();
    }


    private void login()  {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        LoginController loginController = new LoginController();

        CustomStrictMode.strictMode();
        try {
            Response<SignUpResponse> loginResponse = loginController.login(email, password);
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("200")) {

                Url.token += loginResponse.body().getToken();
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                if (loginResponse.body().getImagePath() != null && !loginResponse.body().getImagePath().isEmpty()) {
                    intent.putExtra("imagePath", loginResponse.body().getImagePath());
                }
                startActivity(intent);
            } else {
                tvErrorMessage.setText("Invalid username or password");
                etEmail.requestFocus();
            }
        } catch (IOException ex) {
            tvErrorMessage.setText("Unable to login");
            etEmail.requestFocus();
            Log.d("Login Error", ex.getLocalizedMessage());
        }

    }





}
