package com.example.projectp2_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SignUp extends AppCompatActivity {

    private EditText fullname;
    private EditText username;
    private EditText password;
    private EditText confpassword;
    private ImageView imageViewProfile;
    private Uri profilePictureUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize EditText fields
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confpassword = findViewById(R.id.confpassword);

        Button signUpButton = findViewById(R.id.btn_signup2);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SignUpValidator.isValidSignUpForm(fullname,
                        username, password, confpassword)) {
                    // Proceed with sign-up
                    Toast.makeText(SignUp.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                    User currUser = saveUser(username, password);
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    intent.putExtra("user",currUser);
                    startActivity(intent);
                } else {
                    // Display error message if form is invalid
                    Toast.makeText(SignUp.this, "All fields are required, password must match and contain at least 8 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonUploadPhoto = findViewById(R.id.buttonUploadPhoto);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        buttonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }

        });
    }

        public void showFileChooser() {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            i.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(Intent.createChooser(i, "select a picture"), 100);
            } catch (Exception exception) {
                Toast.makeText(this, "no file manager", Toast.LENGTH_SHORT).show();
            }
        }

        @Override protected void onActivityResult(int requestCode, int resultCode,
                                                  @Nullable @org.jetbrains.annotations.Nullable Intent data) {

            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                Uri selected_img = data.getData();

                profilePictureUri = selected_img;
                imageViewProfile.setImageURI(selected_img);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        public User saveUser(EditText username, EditText password) {
            User newUser = new User(username.getText().toString(),
                    password.getText().toString(), profilePictureUri);
            return newUser;
        }


}