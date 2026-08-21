package com.anuj.callsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

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

            // Check Automatic SMS ON/OFF setting
            SharedPreferences preferences =
                    context.getSharedPreferences(
                            "sms_settings",
                            Context.MODE_PRIVATE
                    );

            boolean smsEnabled =
                    preferences.getBoolean("sms_enabled", true);

            // If SMS is OFF, don't send
            if (!smsEnabled) {
                incomingNumber = null;
                return;
            }

            // Short SMS with Digital Menu link
            String message =
                    "Namaste 🙏 Anuj Confectionary\n"
                    + "Aapki call receive ho gayi hai.\n\n"
                    + "https://anujconfectionarydigitalmenu.vercel.app/";

            try {

                SmsManager smsManager =
                        SmsManager.getDefault();

                smsManager.sendTextMessage(
                        incomingNumber,
                        null,
                        message,
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
