package com.example.hindahmed.anti_cancer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.hindahmed.anti_cancer.LoginActivity.mAuth;

public class Sign_up_Activity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mEmail;
    private EditText mRePassword;
    private EditText mName;
    private EditText mPassword;
    private RadioButton male_Radio_b ;
    private RadioButton female_Radio_b ;
    private View mProgressView;
    private View mLoginFormView;
    String email, password, Repassword, Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_);
        mName = (EditText) findViewById(R.id.m_name);
        mEmail = (AutoCompleteTextView) findViewById(R.id.m_email);
        mPassword = (EditText) findViewById(R.id.m_password);
        mRePassword = (EditText) findViewById(R.id.m_Repassword);
        mProgressView = findViewById(R.id.login_progress);
        male_Radio_b = (RadioButton) findViewById(R.id.male);
        female_Radio_b = (RadioButton) findViewById(R.id.female);

        if(male_Radio_b.isChecked()){
            female_Radio_b.setChecked(false);
        }
        if (female_Radio_b.isChecked()){
            male_Radio_b.setChecked(false);
        }

//        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });

    }
    public void Sign_up_button(View v) {
        // Reset errors.
        mName.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);
        mRePassword.setError(null);


        // Store values at the time of the login attempt.
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
        Repassword = mRePassword.getText().toString();
        Name =mName.getText().toString();
        boolean cancel = false;
        boolean mcancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        if (!TextUtils.isEmpty(Repassword) && !isPasswordValid(Repassword)) {
            mRePassword.setError(getString(R.string.error_invalid_password));
            focusView = mRePassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(Name)) {
            mName.setError(getString(R.string.error_field_required));
            focusView = mName;
            cancel = true;
        }
        if(female_Radio_b.isChecked()||male_Radio_b.isChecked()){
            mcancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if(mcancel) {
//                if (password.equals(Repassword)) {
//                    showProgress(true);
                    Sign_up();
//                } else {
//                    mRePassword.setText("");
//                    Toast.makeText(Sign_up_Activity.this, "Password Not Match", Toast.LENGTH_LONG).show();
//                }
            }else {
                Toast.makeText(Sign_up_Activity.this, "Please choose Female or Male ", Toast.LENGTH_LONG).show();

            }

        }


    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */



    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)


    private void Sign_up() {
        if (isInternetOn()) {
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Login", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            Intent i = new Intent(Sign_up_Activity.this,Public_Activity.class);
                            startActivity(i);

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(Sign_up_Activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        } else {
            Toast.makeText(Sign_up_Activity.this, "Please check internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}
