package com.rujal.hamrobazaar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.rujal.hamrobazaar.api.UserApi;
import com.rujal.hamrobazaar.model.ImageResponse;
import com.rujal.hamrobazaar.model.SignUpResponse;
import com.rujal.hamrobazaar.model.User;
import com.rujal.hamrobazaar.url.Url;

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
    private TextView tvRegisterError;
    private Button btnRegister;
    private ImageView profilePic;
    private String imagePath;
    private String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profilePic = findViewById(R.id.profilePic);
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        etFullName = findViewById(R.id.etFullName);
        etRePassword = findViewById(R.id.etRePassword);
        etPhone = findViewById(R.id.etPhone);
        etMobilePhone = findViewById(R.id.etMobile);
        etStreetName = findViewById(R.id.etStreetName);
        etLocation = findViewById(R.id.etAreaLocation);
        etAddress3 = findViewById(R.id.etAddress3);
        btnRegister = findViewById(R.id.btnRegister);
        tvRegisterError = findViewById(R.id.tvRegisterError);

        profilePic.setOnClickListener(i -> browseImage());
        btnRegister.setOnClickListener(i -> {
                if (etPassword.getText().toString().equalsIgnoreCase(etRePassword.getText().toString())) {
                    if (validate()) {
                        if (imagePath != null) {
                            saveImageOnly();
                            signUp();
                        }
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
        });
    }

    private boolean validate() {
        tvRegisterError.setError("");
        if (etEmail.getText().toString().isEmpty() || etFullName.getText().toString().isEmpty()  ) {
            tvRegisterError.setText("Please input all fields");
            return false;
        } else if (etMobilePhone.getText().toString().length() != 10  ) {
            etMobilePhone.setError("10 numbers required");
            return false;
        } else if (etPhone.getText().toString().length() != 7) {
            etPhone.setError("7 numbers required");
            return false;
        } else if (etPassword.getText().toString().length() < 8) {
            etPassword.setError("8 characters required");
            return false;
        }
        return true;
    }

    private void browseImage() {

        int MY_READ_EXTERNAL_REQUEST = 1;
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_EXTERNAL_REQUEST);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_READ_EXTERNAL_REQUEST);
        }
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
            profilePic.setImageURI(data.getData());
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
            imageName = imageResponseResponse.body().getPath();
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
                    Toast.makeText(RegisterActivity.this, "Error: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "Registered sucessfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
