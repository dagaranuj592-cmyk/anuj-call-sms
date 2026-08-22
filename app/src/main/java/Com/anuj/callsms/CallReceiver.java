package com.anuj.callsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import java.util.ArrayList;

public class CallReceiver extends BroadcastReceiver {

    private static String incomingNumber = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            return;
        }

        String state = intent.getStringExtra(
                TelephonyManager.EXTRA_STATE
        );

        // Incoming call
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {

            incomingNumber = intent.getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER
            );

            return;
        }

        // Call ended
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {

            if (incomingNumber == null || incomingNumber.isEmpty()) {
                return;
            }

            SharedPreferences preferences =
                    context.getSharedPreferences(
                            "sms_settings",
                            Context.MODE_PRIVATE
                    );

            boolean smsEnabled =
                    preferences.getBoolean("sms_enabled", true);

            if (!smsEnabled) {
                incomingNumber = null;
                return;
            }

            String message =
                
        "Welcome to Anuj Confectionary "
        + "https://anujconfectionaryorderingsystem-1.vercel.app/";
                   
            try {

                SmsManager smsManager =
                        SmsManager.getDefault();

                ArrayList<String> parts =
                        smsManager.divideMessage(message);

                smsManager.sendMultipartTextMessage(
                        incomingNumber,
                        null,
                        parts,
                        null,
                        null
                );

            } catch (Exception e) {
                e.printStackTrace();
            }

            incomingNumber = null;
        }
    }
}
