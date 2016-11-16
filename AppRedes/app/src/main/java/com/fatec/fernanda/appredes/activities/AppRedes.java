package com.fatec.fernanda.appredes.activities;

import com.firebase.client.Firebase;

/**
 * Created by Fernanda on 03/09/2016.
 */


public class AppRedes extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
