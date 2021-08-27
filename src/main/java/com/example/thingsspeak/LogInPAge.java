package com.example.thingsspeak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LogInPAge extends AppCompatActivity {
    private EditText channel_id;
    private EditText write_api;
    private EditText read_api;
    Button bt;
    Boolean isValidString=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_p_age);
        bt = (Button) findViewById(R.id.button6);
        write_api = (EditText) findViewById(R.id.editText2);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1 = write_api.getText().toString();
                postData(value1);
            }
        });
    }

    private void postData(final String write_api) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1=(("https://api.thingspeak.com/update?api_key=")+write_api);
        Log.e("ankitha","url e:"+url1);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("naveen", "OnRespons e: " + response);
                if(response.length()>0 && response!=null){
                    isValidString=true;
                    onSentInput(isValidString,write_api);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isValidString=false;
                onSentInput(isValidString,write_api);
            }
        })
        {

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("field1", "0");
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void onSentInput(boolean isValidString,String value1){
        if(isValidString==true){
            Toast.makeText(LogInPAge.this, "Log in Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogInPAge.this, MainActivity.class);
            intent.putExtra("value1", value1);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(LogInPAge.this, "wrong", Toast.LENGTH_SHORT).show();
        }
    }
}