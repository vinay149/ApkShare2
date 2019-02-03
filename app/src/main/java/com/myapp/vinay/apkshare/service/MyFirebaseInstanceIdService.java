package com.myapp.vinay.apkshare.service;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.myapp.vinay.apkshare.R;
import com.myapp.vinay.apkshare.MainActivity;

import java.util.prefs.Preferences;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences=getApplicationContext ().getSharedPreferences (getString (R.string.device_token),MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit ();
        editor.putString ("device_fcmtoken",refreshedToken);
        editor.commit ();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        MainActivity.Companion.sendTokenToServer(refreshedToken);
    }
}
