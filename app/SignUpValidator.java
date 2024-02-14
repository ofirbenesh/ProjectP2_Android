import android.widget.EditText;

public class SignUpValidator {

    public static boolean isValidSignUpForm(EditText firstNameEditText, EditText surnameEditText,
                                            EditText emailEditText, EditText passwordEditText,
                                            EditText confirmPasswordEditText) {
        // Check if any field is empty
        if (isEmptyField(firstNameEditText) || isEmptyField(surnameEditText) ||
                isEmptyField(emailEditText) || isEmptyField(passwordEditText) ||
                isEmptyField(confirmPasswordEditText)) {
            return false;
        }

        // Check if passwords match
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            return false;
        }

        // All validations passed
        return true;
    }

    private static boolean isEmptyField(EditText editText) {
        String text = editText.getText().toString().trim();
        return text.isEmpty();
    }
}