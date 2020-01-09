package com.rujal.hamrobazaar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class LoginDialog extends AppCompatDialogFragment {

    EditText etEmail, etPassword;
    Button btnLogin, btnForget, btnSignUp;

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

        btnSignUp.setOnClickListener(i -> {
            System.out.println("Test sign up clicked");
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        return builder.create();
    }

}
