package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Utility.Prefs;
import com.example.Utility.utility;
import com.example.caretaker.R;
import com.example.caretaker.profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ottime extends AppCompatActivity {
    Button bLog;
    EditText etOTP,etPN,etEmail;

    //String url_req = "http://192.168.181.2/service_main/request_otp.php";

    String otp = "";
    ProgressDialog dialog;

    String scode, msg;

    RequestQueue requestQueue;
    Prefs pref;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ottime);
        pref = new Prefs(getApplicationContext());
        context = ottime.this;
        bLog = (Button) findViewById(R.id.bLogin);
        etPN = (EditText) findViewById(R.id.etNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etOTP = (EditText) findViewById(R.id.etLogin);



        context = ottime.this;
        // pref = new PrefManager(this);

        bLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String client_phone = etPN.getText().toString();
                final String client_email = etEmail.getText().toString();
                final String OTP = etOTP.getText().toString();
                final String carer_email= Prefs.with(ottime.this).getEmail();


                if (client_phone.length() == 10 && !etEmail.getText().toString().equals("") && !etOTP.getText().toString().equals("")) {
                    String url = getResources().getString(R.string.url) +"action=OTPTIME&client_phone="+client_phone+"&client_email="+client_email+"&OTP="+OTP+"&carer_email="+carer_email ;
                    url = url.replaceAll(" ", "%20");
                    Toast.makeText(ottime.this,url,Toast.LENGTH_LONG).show();
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("Response", response.toString());

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            scode = jsonResponse.getString("code");
                            Log.v("responseCode", scode + "");
                            if (scode.equals("200")) {
                                Log.v("responseCode","in");

                                msg = jsonResponse.getString("msg");

                                //Log.v("responseCode",""+id);
                              //  progressBar.setVisibility(View.INVISIBLE);
                                utility.SetMessage(ottime.this,msg+" ");
                                startActivity(new Intent(ottime.this,profile.class));
                            } else {
                                msg = jsonResponse.getString("msg");
                                utility.SetMessage(ottime.this,"" + msg);
                            }
                            Log.v("Response code", scode );
                           // progressBar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            utility.SetMessage(ottime.this,"" + e.getMessage());
                            Log.v("responseCode", e.getMessage());

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", "Error: " + error.getMessage());
                        utility.SetMessage(ottime.this, "" + error.getMessage());

                    }
                }) {



                    @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("action", "SignIn");
                            params.put("client_phone", client_phone);
                            params.put("client_email", client_email);
                            params.put("OTP", OTP);
                            params.put("carer_email", carer_email);


                            Log.v("PARAM", "Posting params: " + params.toString());
                            Log.v("strreq", "" + params.toString());
                            return params;
                        }

                    };
                    requestQueue = Volley.newRequestQueue(ottime.this);
                    requestQueue.add(strReq);

                }

            }
        });
    }
}
