package com.example.teamup.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;
import com.parse.SignUpCallback;


import com.example.teamup.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


//----------------------------------------------------------------------------------
//  The activity that allows a user to Register as a user
//----------------------------------------------------------------------------------
public class UserRegistrationActivity extends AppCompatActivity {

    private final String SIGNUP = "SIGNUP";
    private final String ERROR = "ERROR";
    private EditText etfname;
    private EditText etlname;
    private EditText etemail;
    private EditText etpassword;
    private EditText etusername;
    //private EditText etphonenum;
    private Button btn_Cancel;
    private Button btn_Submit;

    //----------------------------------------------------------------------------------
    //  Set view
    //----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        etfname = findViewById(R.id.userProf_fname);
        etlname = findViewById(R.id.userProf_lname);
        etemail = findViewById(R.id.userProf_email);
        etpassword = findViewById(R.id.userProf_password);
        etusername = findViewById(R.id.userProf_username);
        //etphonenum = findViewById(R.id.userReg_phone);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        btn_Submit = findViewById(R.id.btn_Update);

        //----------------------------------------------------------------------------------
        //  Listening for a click on the Sign Up button
        //----------------------------------------------------------------------------------
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserRegistrationActivity.this, "In Submit Click", Toast.LENGTH_SHORT).show();
                // Get the text from form
                String fname = etfname.getText().toString();
                String lname = etlname.getText().toString();

                String name = fname + " " + lname;
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
                String username = etusername.getText().toString();
                //String phonenum = PhoneNumberUtils.formatNumber(etphonenum.getText().toString());

                verify(fname, lname, email, username, password);

            }
        });

        //----------------------------------------------------------------------------------
        //  Listening for a click on the Cancel button
        //----------------------------------------------------------------------------------
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });

    }

    //----------------------------------------------------------------------------------
    //  Verifying that the users credentials are unique
    //----------------------------------------------------------------------------------
    private void verify(final String fname,final String lname,final String email,final String username,final String password) {
        if (username.length()==0 || password.length()==0 || fname.length()==0 || lname.length()==0 || email.length()==0){
            Toast.makeText(UserRegistrationActivity.this, "Fields cannot be left blank", Toast.LENGTH_SHORT).show();
        }
        else {

            //Query to check user input against object (username)
            ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("User");
            userQuery.whereEqualTo("username", username);
            userQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    Log.d("ASA TEST", " " + objects.size());
                    for(int i = 0; i < objects.size(); i++){
                        Log.d("users", "Retrieved " + objects.get(i).get("username") + " users");
                    }
                    register(fname, lname, username, email, password);
                    /*if (objects.size() != 0) {
                        Toast.makeText(UserRegistrationActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                        //Log.d("users", "Retrieved " + objects.get(0).get("username"));
                        //Log.d("users", "Retrieved " + objects.size() + " users");
                    }
                    else{
                        register(fname, lname, username, email, password);
                    }*/
                }
            });
        }
    }

    //----------------------------------------------------------------------------------
    //  Registering a new user
    //----------------------------------------------------------------------------------
    private void register(String fname, String lname, String username, String email, String password) {

        ParseUser User = new ParseUser();
        User.setUsername(username);
        User.setPassword(password);
        User.setEmail(email);
        User.put("fname", fname);
        User.put("lname", lname);
        //parseUser.put(user.KEY_PHONENUM, phonenum);

        User.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                    if (e != null){
                        Log.e(ERROR, "Error in signing up user");
                        e.printStackTrace();
                        return;
                    }
                    Log.d(SIGNUP, "Sign Up Successful");
                    etfname.setText("");
                    etlname.setText("");
                    etusername.setText("");
                    etpassword.setText("");
                    etemail.setText("");
                    //etphonenum.setText("");
            }
        });

//        User.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null){
//                    Log.e(ERROR, "Error in signing up user");
//                    e.printStackTrace();
//                    return;
//                }
//                Log.d(SIGNUP, "Sign Up Successful");
//                etfname.setText("");
//                etlname.setText("");
//                etusername.setText("");
//                etpassword.setText("");
//                etemail.setText("");
//                //etphonenum.setText("");
//
//            }
//      });

        ProgressDialog dialog = ProgressDialog.show(UserRegistrationActivity.this, "",
                "Loading. Please wait...", true);
        goLoginActivity();
    }

    //----------------------------------------------------------------------------------
    //  Functionality that allows a user to navigate to the Login page
    //  after successfully registering
    //----------------------------------------------------------------------------------
    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        Toast.makeText(UserRegistrationActivity.this, "Log into your new account", Toast.LENGTH_SHORT).show();
        Log.d("WORKING", "It's working");
        finish();
    }
}