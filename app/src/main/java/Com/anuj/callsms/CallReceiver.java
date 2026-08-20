package com.anuj.callsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            return;
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            // Incoming call started
        }

        if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            // Call answered / active
        }

        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            // Call ended
        }
    }
}
