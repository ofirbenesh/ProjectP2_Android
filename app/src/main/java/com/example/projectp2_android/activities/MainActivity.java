package com.example.projectp2_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectp2_android.R;
import com.example.projectp2_android.User;

public class MainActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(v -> {
             Intent i = new Intent(this, SignUp.class);
             startActivity(i);
        });

        Button btn_login = findViewById(R.id.btn_login);
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                User user = (User) intent.getSerializableExtra("user");
                Uri img = (Uri) intent.getParcelableExtra("img");
                String savedUsername = user.getUserName();
                String savedPassword = user.getUserPassword();
                if (user_name.getText().toString().equals(savedUsername) && password.getText().toString().equals(savedPassword)) {
                    // Login successful
                    Intent i = new Intent(MainActivity.this, Feed.class);
                    i.putExtra("user",user);
                    i.putExtra("img", img);
                    startActivity(i);
                } else {
                    // Invalid credentials
                    Toast.makeText(MainActivity.this, "User Doesn't exist in system", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Button moveButton = findViewById(R.id.moveBtn);
        moveButton.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Feed.class);
            User user = new User("Ofir Benesh", "12345678");
            i.putExtra("user",user);
            startActivity(i);
        });
    }
}