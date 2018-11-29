package dpaw.com.storagetrac.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dpaw.com.storagetrac.R;

public class RegisterAcitivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    /**
     * Auth object
     */
    private FirebaseAuth _auth;

    /**
     * Reference to the email text field
     */
    private EditText emailText;

    /**
     * The password text field
     */
    private EditText passText;

    /**
     * The password repeat text field
     */
    private EditText passRepeatText;

    /**
     * Email error text
     */
    private TextView emailError;

    /**
     * Password error text
     */
    private TextView passError;

    /**
     * Password error text
     */
    private TextView passRepeatError;

    /**
     * Sign in error text
     */
    private TextView signInError;

    /**
     * String representing an error for enmpty string
     */
    private String missingError;

    /**
     * String representing an error for short passwords
     */
    private String lengthError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _auth = FirebaseAuth.getInstance();

        // fields
        emailText = findViewById(R.id.emailField);
        passText = findViewById(R.id.passwordField);
        passRepeatText = findViewById(R.id.passwordField2);

        // text errors
        emailError = findViewById(R.id.emailError);
        passError = findViewById(R.id.passwordError);
        passRepeatError = findViewById(R.id.passwordError2);
        signInError = findViewById(R.id.loginError);

        // error strings
        missingError = getResources().getString(R.string.error_field_required);
        lengthError = getResources().getString(R.string.error_invalid_password);
    }

    /**
     * Attempts to register a new account with the field values
     */
    public void registerWithFieldValues(View view) {
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();
        String passRepeat = passRepeatText.getText().toString();

        // basic email format verification
        if (email.equals("")) {
            emailError.setVisibility(View.VISIBLE);
            return;
        } else {
            emailError.setVisibility(View.INVISIBLE);
        }

        // basic password format verification
        if (pass.equals("")) {
            passError.setText(missingError);
            passError.setVisibility(View.VISIBLE);
            return;
        } else {
            passError.setVisibility(View.INVISIBLE);
        }

        if (pass.length() < 8) {
            passError.setText(lengthError);
            passError.setVisibility(View.VISIBLE);
            return;
        } else {
            passError.setVisibility(View.INVISIBLE);
        }

        // check if
        if (!passRepeat.equals(pass)) {
            passRepeatError.setVisibility(View.VISIBLE);
            return;
        } else {
            passRepeatError.setVisibility(View.INVISIBLE);
        }

        // attempt to register
        createAccount(email, pass);
    }

    /**
     * Attempts to create a new account
     * @param email the email associated with the account
     * @param password the password associated with the account
     */
    private void createAccount(String email, String password) {
        _auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = _auth.getCurrentUser();
                        Toast.makeText(RegisterAcitivity.this, "New account successfully created!.",
                                Toast.LENGTH_SHORT).show();

                        // close this activity
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterAcitivity.this, "Failed to create an account.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
    }
}
