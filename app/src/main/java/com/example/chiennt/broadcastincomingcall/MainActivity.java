package com.example.chiennt.broadcastincomingcall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Log.d("IncomingCallActivity: onCreate: ", "flag2");


            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

            Log.d("IncomingCallActivity: onCreate: ", "flagy");

            setContentView(R.layout.activity_main);

            Log.d("IncomingCallActivity: onCreate: ", "flagz");

            String number = getIntent().getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);

            TextView text = (TextView) findViewById(R.id.incomingNumber);
            text.setText(number);


        } catch (Exception e) {
            Log.d("Exception", e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
