package com.example.caretaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Utility.Prefs;
import com.example.Utility.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    EditText email, phone;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    String scode, msg;
    Button fab;
    Prefs pref;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        pref = new Prefs(getApplicationContext());
        context = MainActivity.this;
   /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        requestQueue = Volley.newRequestQueue(this);
         progressBar=findViewById(R.id.progressBar);

         email=findViewById(R.id.emailid);
         phone=findViewById(R.id.password);

        if (pref.isLogin()) {
            Intent intent = new Intent(MainActivity.this, profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            startActivity(new Intent(context, MainActivity.class));
            finish();
            return;
        }


        fab = findViewById(R.id.submit_area);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                if(utility.isInternetAvailable(MainActivity.this)){
                    if(!email.getText().toString().equals("") && !phone.getText().toString().equals("")) {
                        Login(email.getText().toString(), phone.getText().toString());
                    }else {
                        utility.SetMessage(MainActivity.this,"Email Id and Password both required.");
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }else {
                    utility.SetMessage(MainActivity.this,"Please Check your Internet Connection.");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
    /*startActivity(new Intent(Login.this, MainActivity.class));*/
    public void Login(final String carer_email, final String carer_phone){
        String url = getResources().getString(R.string.url)+"action=CaretakerSignIn&carer_email="+carer_email+"&carer_phone="+carer_phone;
        url=url.replaceAll(" ","%20");
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
                        //And then read attributes like
                        String id = jsonResponse.getString("id");
                        String name = jsonResponse.getString("carer_name");
                        String email = jsonResponse.getString("carer_email");
                        String phone = jsonResponse.getString("carer_phone");
                        String address = jsonResponse.getString("address");



                        Prefs.with(MainActivity.this).setProfile(id,name,email,phone,address);

                        Log.v("responseCode",""+id);
                        progressBar.setVisibility(View.INVISIBLE);
                        utility.SetMessage(MainActivity.this,msg+" ");
                        startActivity(new Intent(MainActivity.this,profile.class));
                    } else {
                        msg = jsonResponse.getString("msg");
                        utility.SetMessage(MainActivity.this,"" + msg);
                    }
                    Log.v("Response code", scode );
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    utility.SetMessage(MainActivity.this,"" + e.getMessage());
                    Log.v("responseCode", e.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response", "Error: " + error.getMessage());
                utility.SetMessage(MainActivity.this,"" + error.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "CaretakerSignIn");
                params.put("carer_email", carer_email);
                params.put("carer_phone", carer_phone);
                Log.v("PARAM", "Posting params: " + params.toString());
                Log.v("strreq", ""+params.toString());
                return params;
            }

        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

    }


    }
