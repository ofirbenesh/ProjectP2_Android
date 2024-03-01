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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectp2_android.R;
import com.example.projectp2_android.SignUpValidator;
import com.example.projectp2_android.User;
import com.example.projectp2_android.entities.GlobalVariables;

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
                    if (profilePictureUri != null) {
                        intent.putExtra("img",profilePictureUri);
                    }
                    GlobalVariables.userName = username.getText().toString();
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
                if (requestCode == 0) {
                    // Camera
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageViewProfile.setImageBitmap(imageBitmap);
                } else if (requestCode == 1) {
                    // Gallery
                    selectedImageUri = data.getData();
                    imageViewProfile.setImageURI(selectedImageUri);
                }

                // Update profilePictureUri with the URI of the new image
                profilePictureUri = selectedImageUri;
            }
        }

        public User saveUser(EditText username, EditText password) {
            User newUser = new User(username.getText().toString(),
                    password.getText().toString());
            return newUser;
        }


}