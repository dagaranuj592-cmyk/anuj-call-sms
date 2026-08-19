package com.anuj.callsms;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    EditText message;
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(24,24,24,24);

        TextView title = new TextView(this);
        title.setText("📞 Anuj Confectionary\nCall ke baad automatic SMS");
        title.setTextSize(22);
        box.addView(title);

        message = new EditText(this);
        message.setText(getPreferences(0).getString("message",
            "Thank you for calling Anuj Confectionary. Order ke liye hamara online menu use karein."));
        message.setHint("Call ke baad bhejne wala SMS");
        box.addView(message);

        Button save = new Button(this);
        save.setText("SAVE MESSAGE");
        box.addView(save);
        save.setOnClickListener(v -> {
            getPreferences(0).edit().putString("message",message.getText().toString()).apply();
            getSharedPreferences("settings",0).edit().putString("message",message.getText().toString()).apply();
            Toast.makeText(this,"Message saved",Toast.LENGTH_SHORT).show();
        });

        TextView info = new TextView(this);
        info.setText("\nFlow:\nIncoming SIM call → call end → SMS to caller");
        box.addView(info);
        setContentView(box);

        if(android.os.Build.VERSION.SDK_INT>=23)
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG,Manifest.permission.SEND_SMS},10);
    }
}
