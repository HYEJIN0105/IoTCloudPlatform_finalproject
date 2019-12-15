package com.example.android_resapi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetThingShadow;
import com.example.android_resapi.ui.apicall.UpdateShadow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceActivity extends AppCompatActivity {
    String urlStr;
    final static String TAG = "AndroidAPITest";
    Timer timer;
    Button startGetBtn;
    Button stopGetBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent intent = getIntent();
        urlStr = intent.getStringExtra("thingShadowURL");

        startGetBtn = findViewById(R.id.startGetBtn);
        startGetBtn.setEnabled(true);
        startGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new GetThingShadow(DeviceActivity.this, urlStr).execute();
                    }
                },
                        0,2000);

                startGetBtn.setEnabled(false);
                stopGetBtn.setEnabled(true);
            }
        });

        stopGetBtn = findViewById(R.id.stopGetBtn);
        stopGetBtn.setEnabled(false);
        stopGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();
                clearTextView();
                startGetBtn.setEnabled(true);
                stopGetBtn.setEnabled(false);
            }
        });

        Button updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit_temp = findViewById(R.id.edit_temp);
                EditText edit_led = findViewById(R.id.edit_led);
                EditText edit_buz = findViewById(R.id.edit_buz);
                EditText edit_hum= findViewById(R.id.edit_hum);

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String temp_input = edit_temp.getText().toString();
                    if (temp_input != null && !temp_input.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "temperature");
                        tag1.put("tagValue", temp_input);

                        jsonArray.put(tag1);
                    }

                    String hum_input = edit_hum.getText().toString();
                    if (hum_input != null && !hum_input.equals("")) {
                        JSONObject tag2 = new JSONObject();
                        tag2.put("tagName", "humidity");
                        tag2.put("tagValue", hum_input);

                        jsonArray.put(tag2);
                    }
                    String led_input = edit_led.getText().toString();
                    if (led_input != null && !led_input.equals("")) {
                        JSONObject tag3 = new JSONObject();
                        tag3.put("tagName", "LED");
                        tag3.put("tagValue", led_input);

                        jsonArray.put(tag3);
                    }


                    String buz_input = edit_buz.getText().toString();
                    if (buz_input != null && !buz_input.equals("")) {
                        JSONObject tag4 = new JSONObject();
                        tag4.put("tagName", "BUZZER");
                        tag4.put("tagValue", buz_input);

                        jsonArray.put(tag4);
                    }

                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG,"payload="+payload);
                if (payload.length() >0 )
                    new UpdateShadow(DeviceActivity.this,urlStr).execute(payload);
                else
                    Toast.makeText(DeviceActivity.this,"변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void clearTextView() {
        TextView reported_ledTV = findViewById(R.id.reported_led);
        TextView reported_tempTV = findViewById(R.id.reported_temp);
        TextView reported_humTV = findViewById(R.id.reported_hum);
        TextView reported_buzTV = findViewById(R.id.reported_buz);
        reported_tempTV.setText("");
        reported_ledTV.setText("");
        reported_humTV.setText("");
        reported_buzTV.setText("");

        TextView desired_ledTV = findViewById(R.id.desired_led);
        TextView desired_tempTV = findViewById(R.id.desired_temp);
        TextView desired_humTV = findViewById(R.id.desired_hum);
        TextView desired_buzTV = findViewById(R.id.desired_buz);
        desired_tempTV.setText("");
        desired_ledTV.setText("");
        desired_humTV.setText("");
        desired_buzTV.setText("");
    }

}

