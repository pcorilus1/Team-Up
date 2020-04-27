package com.example.teamup.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamup.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

//----------------------------------------------------------------------------------
// The activity that allows a user to Login
// Also a feature to take a user to a registration activity
//----------------------------------------------------------------------------------
public class LoginActivity extends AppCompatActivity {

    private final String LOGGED = "LOGGED";
    private EditText etUsername;
    private EditText etPassword;
    private TextView btn_SignUp;
    private Button btnSignIn;
    private ImageView imvIcon;
    //----------------------------------------------------------------------------------
    // Set the view
    //----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);
        ParseUser.getCurrentUser().logOut();

        etUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);
        imvIcon = findViewById(R.id.login_icon);
        btnSignIn = findViewById(R.id.signin_btn);
        btn_SignUp = findViewById(R.id.btn_SignUp);

        //----------------------------------------------------------------------------------
        // Listen for a click on the Sign In Button
        //----------------------------------------------------------------------------------
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                Log.d("LOGIN", "Info: " + username + " " + password);

                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Fields cannot be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    login(username, password);
                }
            }
        });

        //----------------------------------------------------------------------------------
        // Listen for a click on the Sign Up Button
        //----------------------------------------------------------------------------------
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserRegistration();
            }
        });

    }

    //----------------------------------------------------------------------------------
    // Functionality for the Sign Up Button
    // Takes user to the User Registration Activity to register
    //----------------------------------------------------------------------------------
    private void goUserRegistration() {
        Intent i = new Intent(this, UserRegistrationActivity.class);
        startActivity(i);
        Log.d("WORKING", "It's working");
        finish();
    }

    //----------------------------------------------------------------------------------
    // Functionality for the Sign In Button
    // This function logs in the the user
    // This function validates the user credentials
    //----------------------------------------------------------------------------------
    private void login(final String username, String password){
        // TODO: navigate to new activity if the user has signed properly
        Log.i("Test", "LOGGING IN " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null){
                    goMainActivity(user.getObjectId());
                } else {
                    int length = Toast.LENGTH_SHORT;
                    String message = "LOG IN FAIL";

                    Toast.makeText(getApplicationContext(), message, length).show();
                    Log.e("Test", "ERROR: Username " + username + " not found");
                    e.printStackTrace();
                    return;
                }
            }

        });
    }

    //----------------------------------------------------------------------------------
    // This function takes the user to the Main Activity after they were
    // successfully logged in
    //----------------------------------------------------------------------------------
    private void goMainActivity(String id) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_USER_ID, id);
        startActivity(intent);
        //finish();
    }
}

