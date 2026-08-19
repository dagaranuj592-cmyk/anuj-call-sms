package com.anuj.callsms;

import android.content.*;
import android.telephony.SmsManager;

public class CallReceiver extends BroadcastReceiver {
    private static boolean ringing=false;
    private static String number=null;

    @Override public void onReceive(Context c, Intent i) {
        if(!"android.intent.action.PHONE_STATE".equals(i.getAction())) return;
        String state=i.getStringExtra("state");

        if("RINGING".equals(state)) {
            ringing=true;
            number=i.getStringExtra("incoming_number");
        } else if("IDLE".equals(state) && ringing && number!=null) {
            String target=number;
            ringing=false; number=null;
            String msg=c.getSharedPreferences("settings",0).getString("message",
                "Thank you for calling Anuj Confectionary.");
            try { SmsManager.getDefault().sendTextMessage(target,null,msg,null,null); }
            catch(Exception ignored) {}
        }
    }
}
