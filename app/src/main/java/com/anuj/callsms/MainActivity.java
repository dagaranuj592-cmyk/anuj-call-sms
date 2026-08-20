package com.anuj.callsms;

import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    private static final int ROLE_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        requestCallScreeningRole();
    }

    private void requestCallScreeningRole() {

        if (android.os.Build.VERSION.SDK_INT >= 29) {

            RoleManager roleManager = getSystemService(RoleManager.class);

            if (roleManager != null &&
                    roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING) &&
                    !roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {

                Intent intent =
                        roleManager.createRequestRoleIntent(
                                RoleManager.ROLE_CALL_SCREENING
                        );

                startActivityForResult(intent, ROLE_REQUEST_CODE);
            }
        }
    }
}
