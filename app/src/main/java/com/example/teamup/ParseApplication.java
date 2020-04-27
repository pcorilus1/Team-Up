package com.example.teamup;

import android.app.Application;

import com.example.teamup.ParseClasses.Messages;
import com.example.teamup.ParseClasses.User;
import com.example.teamup.ParseClasses.Friends;
import com.example.teamup.ParseClasses.Groups;
import com.parse.Parse;
import com.parse.ParseObject;


//----------------------------------------------------------------------------------
// Initialize Parse
//----------------------------------------------------------------------------------
public class ParseApplication extends Application {

    //private ParseObject cart;

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Friends.class);
        ParseObject.registerSubclass(Groups.class);
        ParseObject.registerSubclass(Messages.class);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("team-up") // should correspond to APP_ID env variable
                .clientKey("teamUpMoving")  // set explicitly unless clientKey is explicitly configured on Parse server
                //.clientBuilder(builder)
                .server("http://team-up-java.herokuapp.com/parse").build());


    }

}