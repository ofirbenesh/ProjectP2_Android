package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.example.projectp2_android.webservices.UserAPI;

import org.json.JSONObject;



public class MainActivity extends AppCompatActivity implements CallBack {
    private UserAPI userApi;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.userApi = new UserAPI();
        this.userApi.setCallback(this);

        // to create new account move to sign up page
        Button btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });

        // when token for login received from Server
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getMyStringLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String updatedValue) {
                Log.d("Token has been received", "New String Value: " + updatedValue);
            }
        });

        // onclick to log in app
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEditText = findViewById(R.id.user_name);
                EditText passwordEditText = findViewById(R.id.password);
                userApi.loginUser(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), viewModel);
            }
        });


        Button moveButton = findViewById(R.id.moveBtn);
        moveButton.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Feed.class);
            startActivity(i);
        });
    }

    @Override
    public void onSuccess(String token) {
        runOnUiThread(() -> {
            Intent intent = new Intent(MainActivity.this, Feed.class);
            MyApplication.loggedUser = ((EditText) findViewById(R.id.user_name)).getText().toString();
            MyApplication.loggerUserToken = token;

            String userId = getUserIdFromToken(token);
            userApi.getUserById(userId);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onFail() {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
        });
    }
    public String getUserIdFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String base64Payload = parts[1];
                byte[] decodedBytes = Base64.decode(base64Payload, Base64.URL_SAFE);
                String payload = new String(decodedBytes, "UTF-8");
                JSONObject jsonPayload = new JSONObject(payload);
                MyApplication.loggedUserID = jsonPayload.getString("id");
                return jsonPayload.getString("id"); // Use "id" to match your token structure
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Token is invalid or userId not found
    }
}