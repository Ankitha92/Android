package com.example.thingsspeak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String write_api = intent.getStringExtra("value1");
        Log.e("ankitha","value e:"+write_api);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url1 = (("https://api.thingspeak.com/update?api_key=") + write_api);
        Log.e("anki", "url e:" + url1);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("naveen", "POST OnRespons e: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("field1", "0");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
