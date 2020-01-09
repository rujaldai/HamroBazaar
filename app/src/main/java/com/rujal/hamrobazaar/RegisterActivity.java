package com.rujal.hamrobazaar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etFullName, etPassword, etRePassword, etPhone, etMobilePhone, etStreetName, etLocation, etAddress3;
    private Button btnRegister;
    private ImageView profilePic;
    private String imagePath;
    private String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profilePic = findViewById(R.id.profilePic);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasswordRegister);
        etFullName = findViewById(R.id.etFullName);
        etRePassword = findViewById(R.id.etRePassword);
        etPhone = findViewById(R.id.etPhone);
        etMobilePhone = findViewById(R.id.etMobile);
        etStreetName = findViewById(R.id.etStreetName);
        etLocation = findViewById(R.id.etAddress2);
        etAddress3 = findViewById(R.id.etAddress2);
        btnRegister = findViewById(R.id.btnRegister);

        profilePic.setOnClickListener(i -> browseImage());
        btnRegister.setOnClickListener(i -> {
                if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                    if (validate()) {
                        saveImageOnly();
                        signUp();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
        });
    }

    private boolean validate() {
        boolean status = true;
        if (etMobilePhone.getText().toString().length() == 9 && etPhone.getText().toString().length() == 6) {
            etPhone.setError("7 number required");
            etMobilePhone.setError("10 number require");
            status = false;
        }
        return status;
    }

    private void browseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
            Uri uri = data.getData();
            profilePic.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);

        }

    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        UserApi usersAPI = Url.getInstance().create(UserApi.class);
        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

        CustomStrictMode.strictMode();
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void signUp() {
        String email = etEmail.getText().toString();
        String fullName = etFullName.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String mobile = etMobilePhone.getText().toString();
        String streetname = etStreetName.getText().toString();
        String location = etLocation.getText().toString();
        String address3 = etAddress3.getText().toString();

        User users = new User(email, fullName, password, phone, mobile, streetname, location, address3, imageName);
        UserApi usersAPI = Url.getInstance().create(UserApi.class);
        Call<SignUpResponse> signUpCall = usersAPI.registerUser(users);

        signUpCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "Register sucessfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
