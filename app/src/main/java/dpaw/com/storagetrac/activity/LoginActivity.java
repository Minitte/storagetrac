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

public class LoginActivity extends AppCompatActivity {

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
     * Email error text
     */
    private TextView emailError;

    /**
     * Password error text
     */
    private TextView passError;

    /**
     * Sign in error text
     */
    private TextView signInError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _auth = FirebaseAuth.getInstance();

        // fields
        emailText = findViewById(R.id.emailField);
        passText = findViewById(R.id.passwordField);

        // text errors
        emailError = findViewById(R.id.emailError);
        passError = findViewById(R.id.passwordError);
        signInError = findViewById(R.id.loginError);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = _auth.getCurrentUser();
    }

    /**
     * Attempts to login with emailField value and passwordField value
     */
    public void loginWithFieldValues(View view) {
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();

        // basic email format verification
        if (email.equals("")) {
            emailError.setVisibility(View.VISIBLE);
            return;
        } else {
            emailError.setVisibility(View.INVISIBLE);
        }

        // basic password format verification
        if (pass.equals("")) {
            passError.setVisibility(View.VISIBLE);
            return;
        } else {
            passError.setVisibility(View.INVISIBLE);
        }

        loginAccount(email, pass);
    }

    /**
     * Attempts to login the account with the credentials
     * @param email the email associated with the account
     * @param password the password associated with the account
     */
    private void loginAccount(String email, String password) {
        _auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = _auth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Successfully logged in!",
                                    Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            signInError.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }
}
