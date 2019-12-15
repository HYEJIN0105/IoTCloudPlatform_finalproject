package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.android_resapi.R;
import com.example.android_resapi.httpconnection.GetRequest;

public class GetThingShadow extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    public GetThingShadow(Activity activity, String urlStr) {
        super(activity);
        this.urlStr = urlStr;
    }

    @Override
    protected void onPreExecute() {
        try {
            Log.e(TAG, urlStr);
            url = new URL(urlStr);

        } catch (MalformedURLException e) {
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            activity.finish();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null)
            return;
        Map<String, String> state = getStateFromJSONString(jsonString);
        TextView reported_ledTV = activity.findViewById(R.id.reported_led);
        TextView reported_tempTV = activity.findViewById(R.id.reported_temp);
        TextView reported_humTV = activity.findViewById(R.id.reported_hum);
        TextView reported_buzTV = activity.findViewById(R.id.reported_buz);
        reported_tempTV.setText(state.get("reported_temperature"));
        reported_ledTV.setText(state.get("reported_LED"));
        reported_humTV.setText(state.get("reported_humidity"));
        reported_buzTV.setText(state.get("reported_BUZZER"));

        TextView desired_ledTV = activity.findViewById(R.id.desired_led);
        TextView desired_tempTV = activity.findViewById(R.id.desired_temp);
        TextView desired_humTV = activity.findViewById(R.id.desired_hum);
        TextView desired_buzTV = activity.findViewById(R.id.desired_buz);
        desired_tempTV.setText(state.get("desired_temperature"));
        desired_ledTV.setText(state.get("desired_LED"));
        desired_humTV.setText(state.get("desired_humidity"));
        desired_buzTV.setText(state.get("desired_BUZZER"));

    }

    protected Map<String, String> getStateFromJSONString(String jsonString) {
        Map<String, String> output = new HashMap<>();
        try {
            // 처음 double-quote와 마지막 double-quote 제거
            jsonString = jsonString.substring(1,jsonString.length()-1);
            // \\\" 를 \"로 치환
            jsonString = jsonString.replace("\\\"","\"");
            Log.i(TAG, "jsonString="+jsonString);
            JSONObject root = new JSONObject(jsonString);
            JSONObject state = root.getJSONObject("state");
            JSONObject reported = state.getJSONObject("reported");
            String tempValue = reported.getString("temperature");
            String ledValue = reported.getString("LED");
            String humValue = reported.getString("humidity");
            String buzValue = reported.getString("BUZZER");
            output.put("reported_temperature", tempValue);
            output.put("reported_LED",ledValue);
            output.put("reported_humidity",humValue);
            output.put("reported_BUZZER",buzValue);

            JSONObject desired = state.getJSONObject("desired");
            String desired_tempValue = desired.getString("temperature");
            String desired_ledValue = desired.getString("LED");
            String desired_humValue = desired.getString("humidity");
            String desired_buzValue = desired.getString("BUZZER");
            output.put("desired_temperature", desired_tempValue);
            output.put("desired_LED",desired_ledValue);
            output.put("desired_humidity", desired_humValue);
            output.put("desired_BUZZER",desired_buzValue);

        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
