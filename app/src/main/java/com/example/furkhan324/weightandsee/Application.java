package com.example.furkhan324.weightandsee;


import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.parse.Parse;


public class Application extends android.app.Application {


    @Override
    public void onCreate() {

        super.onCreate();
        FontsOverride.overrideFont(getApplicationContext(), "SERIF", "fonts/bariol.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        Firebase.setAndroidContext(this);
        // setting up firebase in application context
        FacebookSdk.sdkInitialize(this);
Parse.initialize(this,"cD3dJXVjK3TYHrJS2wFtBc9UCP5QT83gkVX9qrXe", "8YeiELEAumGjM2rzUMrWClwRlcEmczSwayvkCxh8");


    }


}
