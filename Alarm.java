package com.example.thingsspeak;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;

public class Alarm extends BroadcastReceiver {

    boolean isvalidData = false;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        /*MediaPlayer mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.start();*/
        this.context = context;
        final String write_api = intent.getStringExtra("value1");
        final String e1 = intent.getStringExtra("value");
        Log.e("ankitha", "String e:" + write_api);
        Log.e("ankitha", "editext e:" + e1);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url1 = (("https://api.thingspeak.com/update?api_key=") + write_api);
        Log.e("ankitha", "url e:" + url1);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ankitha", "OnRespons e: " + response);
                if(response.length()>0 && response!=null){
                    isvalidData=true;
                    onSentInput(isvalidData,write_api,e1);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ankitha", "error e:" + error);
                error.printStackTrace();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("field1", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void onSentInput(boolean isvalidData,String write_api,String e1){
        int millisInput = Integer.parseInt(e1)*60;
        Intent intent = new Intent(this.context, MyBroadcastReceiver.class);
        intent.putExtra("value1", write_api);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.context.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + (millisInput*1000), 10000
                , pendingIntent);

        Toast.makeText(this.context, "Alarm will set in " + e1+ " minutes", Toast.LENGTH_LONG).show();
    }
}
