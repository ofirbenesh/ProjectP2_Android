package com.example.projectp2_android.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectp2_android.CallBack;
import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.R;
import com.example.projectp2_android.SignUpValidator;
import com.example.projectp2_android.entities.User;
import com.example.projectp2_android.entities.GlobalVariables;
import com.example.projectp2_android.viewmodels.SignUpViewModel;
import com.example.projectp2_android.viewmodels.UserViewModel;
import com.example.projectp2_android.webservices.UserAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUp extends AppCompatActivity implements CallBack {

    private EditText fullnameET;
    private EditText usernameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText confpasswordET;
    private ImageView imageViewProfile;
    private Uri profilePictureUri;
    private UserViewModel userViewModel;
    private SignUpViewModel signupViewModel;
    private boolean isSignupSuccessful;
    private String imageBase64;
    private UserAPI userApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.userApi = new UserAPI();
        this.userApi.setCallback(this);

        init();

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
            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }

        @Override protected void onActivityResult(int requestCode, int resultCode,
                                                  @Nullable @org.jetbrains.annotations.Nullable Intent data) {

            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = null;
                Bitmap imageBitmap = null;
                if (requestCode == 0) {
                    // Camera
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    imageViewProfile.setImageBitmap(imageBitmap);
                } else if (requestCode == 1) {
                    // Gallery
                    selectedImageUri = data.getData();
                    imageViewProfile.setImageURI(selectedImageUri);
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (imageBitmap != null) {
                    imageBase64 = MyApplication.bitmapToBase64(imageBitmap);

                    // Update profilePictureUri with the URI of the new image
                    profilePictureUri = selectedImageUri;
                }
            }
        }

//        public User saveUser(EditText username, EditText password) {
//            User newUser = new User(username.getText().toString(),
//                    password.getText().toString());
//            return newUser;
//        }

        private void init() {
            fullnameET = findViewById(R.id.fullname);
            usernameET = findViewById(R.id.username);
            emailET = findViewById(R.id.email);
            passwordET = findViewById(R.id.password);
            confpasswordET = findViewById(R.id.confpassword);

            // onclick to create the new account
            Button signUpButton = findViewById(R.id.btn_signup2);
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateSignUp();
                }
            });
        }

        private void validateSignUp() {
            if (SignUpValidator.isValidSignUpForm(fullnameET,
                    usernameET, passwordET, confpasswordET)) {
                // Proceed with sign-up
                this.userViewModel = new UserViewModel();

                String fullname = fullnameET.getText().toString();
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String email = emailET.getText().toString();
//                String profileImageViewString = profilePictureUri.toString();
                this.userApi.addUser(fullname, email, username, password, imageBase64);
            } else {
                Toast.makeText(this, "Please Fill All Fields Correctly", Toast.LENGTH_SHORT).show();
            }
    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onSuccess(String token) {
        Toast.makeText(SignUp.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFail() {
        Toast.makeText(SignUp.this, "Email or username is already taken.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void userIsReturned(User user) {

    }
}