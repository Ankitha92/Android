package com.example.thingsspeak;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
   /* private static final String TAG = "UsingThingspeakAPI";
    private static final String THINGSPEAK_CHANNEL_ID = "1248974";
    private static final String THINGSPEAK_API_KEY = "10Q473AQV6JHTLGU"; //GARBAGE KEY
    private static final String THINGSPEAK_API_KEY_STRING = "api_key";

    /* Be sure to use the correct fields for your own app*/
   /* private static final String THINGSPEAK_FIELD1 = "field1";
    private static final String THINGSPEAK_FIELD2 = "field2";
    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update?";
    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
    private static final String THINGSPEAK_FEEDS_LAST = "/feeds.json?"*/;

   // public String tag_json_obj = "json_obj_req";
    TextView t1, t2;
    ProgressBar progressBar;
    private Button b2, b3, b4;
    boolean isvalidPostData = false;
    EditText e1;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b2 = (Button) findViewById(R.id.button1);
        // b3 = (Button) findViewById(R.id.button_set);
        e1 = (EditText) findViewById(R.id.edit_text_input);
        t2 = (TextView) findViewById(R.id.text_view_countdown);
        final TimePicker tp = (TimePicker) findViewById(R.id.time);

        progressBar = findViewById(R.id.progressBar);
        Intent intent = getIntent();
        final String write_api = intent.getStringExtra("value1");

        t1 = (TextView) findViewById(R.id.text);
        t1.setText("w_api" + write_api);
        t1.setVisibility(View.INVISIBLE);

        b2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.set(cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH),
                        tp.getHour(),
                        tp.getMinute(),
                        0);
                String timetransfer = e1.getText().toString();
                Alarm_set(cal.getTimeInMillis(), write_api, timetransfer);
            }
        });
    }

    private void Alarm_set(long timeInMillis, String write_api,String e1) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        intent.putExtra("value1", write_api);
        intent.putExtra("value",e1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Motor is ON", Toast.LENGTH_LONG).show();
        b2.setBackgroundColor(Color.GREEN);
    }

}