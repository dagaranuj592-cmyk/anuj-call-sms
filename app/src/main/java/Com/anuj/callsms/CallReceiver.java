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

        // Jab incoming call aaye
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {

            incomingNumber = intent.getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER
            );
        }

        // Jab call end ho
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {

            if (incomingNumber != null && !incomingNumber.isEmpty()) {

                // ON/OFF switch ki setting check karo
                SharedPreferences preferences =
                        context.getSharedPreferences(
                                "sms_settings",
                                Context.MODE_PRIVATE
                        );

                boolean smsEnabled =
                        preferences.getBoolean("sms_enabled", true);

                // Agar SMS OFF hai to kuch mat karo
                if (!smsEnabled) {
                    incomingNumber = null;
                    return;
                }

                String message =
                        "Namaste! Anuj Confectionary mein call karne ke liye dhanyavaad. "
                        + "Aapki call receive ho gayi hai. "
                        + "Kisi bhi order ya jankari ke liye isi number par sampark karein.";

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
}
