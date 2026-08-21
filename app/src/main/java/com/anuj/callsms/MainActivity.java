package com.anuj.callsms;

import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final int ROLE_REQUEST_CODE = 200;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("sms_settings", MODE_PRIVATE);

        Switch smsSwitch = findViewById(R.id.smsSwitch);
        Button menuButton = findViewById(R.id.menuButton);
        TextView statusText = findViewById(R.id.statusText);

        // Saved ON/OFF setting
        boolean smsEnabled = preferences.getBoolean("sms_enabled", true);
        smsSwitch.setChecked(smsEnabled);

        if (smsEnabled) {
            statusText.setText("System Active");
        } else {
            statusText.setText("System OFF");
        }

        // ON / OFF switch
        smsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            preferences.edit()
                    .putBoolean("sms_enabled", isChecked)
                    .apply();

            if (isChecked) {
                statusText.setText("System Active");
            } else {
                statusText.setText("System OFF");
            }
        });

        // Open digital menu
        menuButton.setOnClickListener(v -> {

            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://anujconfectionarydigitalmenu.vercel.app/")
            );

            startActivity(browserIntent);
        });

        // Existing Call Screening role
        requestCallScreeningRole();
    }

    private void requestCallScreeningRole() {

        if (android.os.Build.VERSION.SDK_INT >= 29) {

            RoleManager roleManager =
                    getSystemService(RoleManager.class);

            if (roleManager != null &&
                    roleManager.isRoleAvailable(
                            RoleManager.ROLE_CALL_SCREENING) &&
                    !roleManager.isRoleHeld(
                            RoleManager.ROLE_CALL_SCREENING)) {

                Intent intent =
                        roleManager.createRequestRoleIntent(
                                RoleManager.ROLE_CALL_SCREENING
                        );

                startActivityForResult(
                        intent,
                        ROLE_REQUEST_CODE
                );
            }
        }
    }
}
