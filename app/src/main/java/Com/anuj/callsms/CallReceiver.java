package com.anuj.callsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            return;
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {

            Cursor cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    new String[]{
                            CallLog.Calls.NUMBER
                    },
                    null,
                    null,
                    CallLog.Calls.DATE + " DESC"
            );

            if (cursor != null && cursor.moveToFirst()) {

                String number = cursor.getString(
                        cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER)
                );

                cursor.close();

                if (number != null && !number.isEmpty()) {

                    String message =
                            "Namaste, aapki call ke liye dhanyavaad.";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(
                            number,
                            null,
                            message,
                            null,
                            null
                    );
                }
            }
        }
    }
}
