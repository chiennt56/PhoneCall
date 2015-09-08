package com.example.chiennt.broadcastincomingcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by chiennt on 9/7/2015.
 */
public class IncomingCallReceiver extends BroadcastReceiver{

    private Context mContext;
    private Intent mIntent;


    @Override
    public void onReceive(Context context, final Intent intent) {
        try {
            mContext = context;
//            mIntent = intent;
//            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            int events = PhoneStateListener.LISTEN_CALL_STATE;
//            tm.listen(phoneStateListener, events);
//            Intent i = new Intent();
//            i.setClassName("com.example.chiennt.broadcastincomingcall", "com.example.chiennt.broadcastincomingcall.MainActivity");
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

            Handler incomingCallHandler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(mContext, MainActivity.class);
                    intent1.putExtras(intent);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent1);
                }
            };

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                    || state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                Log.d("Ringing", "Phone is ringing");
                incomingCallHandler.postDelayed(runnable,1000);

            }
        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }


    }

//    private void startActivity(){
//        Intent intent = new Intent(IncomingCallReceiver.this, MainActivity.class);
//    }

    private final PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            String callState = "UNKNOWN";
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    callState = "IDLE";
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // -- check international call or not.
                    if (incomingNumber.startsWith("00")) {
                        Toast.makeText(mContext, "International Call- " + incomingNumber, Toast.LENGTH_LONG).show();
                        callState = "International - Ringing (" + incomingNumber+ ")";
                    } else {
                        Toast.makeText(mContext, "Local Call - " + incomingNumber, Toast.LENGTH_LONG).show();
                        callState = "Local - Ringing (" + incomingNumber + ")";

                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    String dialingNumber = mIntent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    if (dialingNumber.startsWith("00")) {
                        Toast.makeText(mContext,"International - " + dialingNumber,Toast.LENGTH_LONG).show();
                        callState = "International - Dialing (" + dialingNumber+ ")";
                    } else {
                        Toast.makeText(mContext, "Local Call - " + dialingNumber,Toast.LENGTH_LONG).show();
                        callState = "Local - Dialing (" + dialingNumber + ")";
                    }
                    break;
            }
            Log.i(">>>Broadcast", "onCallStateChanged " + callState);
            super.onCallStateChanged(state, incomingNumber);
        }
    };


}
