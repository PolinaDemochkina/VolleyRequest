package com.example.volley;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView displayDate;
    Button getDate;
    String url = "http://date.jsontest.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDate = (Button)findViewById(R.id.getDate);
        displayDate = (TextView)findViewById(R.id.displayDateAndTime);
    }

    public void onclick(View v) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final ProgressDialog dlg = ProgressDialog.show(
                this,
                "Retrieving REST data",
                "Please Wait...", true);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                dlg.dismiss();
                                try
                                {
                                    displayDate.setText(" - Date: " + response.getString("date") + "\n - Time: " + response.getString("time"));
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(getApplicationContext(),"Request failed: " + error.toString(), Toast.LENGTH_LONG).show();
                                dlg.dismiss();
                            }
                        });

        request.setTag("TAG");
        queue.add(request);
    }
}