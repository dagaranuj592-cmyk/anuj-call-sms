package com.anuj.callsms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.telecom.Call;
import android.telecom.CallScreeningService;

public class CallScreeningService extends android.telecom.CallScreeningService {

    @Override
    public void onScreenCall(Call.Details callDetails) {

        Uri handle = callDetails.getHandle();

        if (handle != null) {

            String phoneNumber = handle.getSchemeSpecificPart();

            if (phoneNumber != null && !phoneNumber.isEmpty()) {

                SharedPreferences prefs =
                        getSharedPreferences("call_data", MODE_PRIVATE);

                prefs.edit()
                        .putString("last_call_number", phoneNumber)
                        .apply();
            }
        }

        CallResponse response = new CallResponse.Builder()
                .setDisallowCall(false)
                .setRejectCall(false)
                .setSilenceCall(false)
                .build();

        respondToCall(callDetails, response);
    }
}
