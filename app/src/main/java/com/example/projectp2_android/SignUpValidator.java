package com.example.projectp2_android;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpValidator {

    public static boolean isValidSignUpForm(EditText fullName,  EditText userName,
                                            EditText passwordEditText,
                                            EditText confirmPasswordEditText) {
        // Check if any field is empty
        if (isEmptyField(fullName) || isEmptyField(userName) ||
                isEmptyField(passwordEditText) ||
                isEmptyField(confirmPasswordEditText)) {
            return false;
        }

        // Check if passwords match
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            return false;
        }
        if (!isPasswordValid(password)) {
            return false;
        }

        // All validations passed
        return true;
    }

    private static boolean isEmptyField(EditText editText) {
        String text = editText.getText().toString().trim();
        return text.isEmpty();
    }

    // check if password matching
    public static boolean isPasswordValid(String password) {
        // Regex pattern for the password requirements
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create matcher object
        Matcher matcher = pattern.matcher(password);

        // Return whether the password matches the pattern
        return matcher.matches();
    }

}
