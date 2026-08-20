package com.anuj.callsms;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.pm.PackageManager;

public class MainActivity extends Activity {

    private static final int PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.SEND_SMS
                    },
                    PERMISSION_REQUEST
            );
        }
    }
}
