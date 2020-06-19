package com.example.caretaker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Utility.Prefs;
import com.example.caretaker.Adapter.CardAdapterClient;
import com.example.caretaker.Model.Modalclientview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class clientvisit extends AppCompatActivity {
    //Creating a List of superheroes
    private List<Modalclientview> listHeroes;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    // public static final String TAG_MESS = "mess_name";
    public static final String CARER_ID = "client_id";
    public static final String CARER_NAME = "client_name";
    public static final String CARER_MAIL= "client_email";

    public static final String CARER_NUM = "client_phone";
    public static final String CARER_ADD = "client_address";
    public static final String CARER_DATE = "start_date";
    public static final String CARER_STIME = "start_time";
    public static final String CARER_ETIME = "end_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientvisit);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //editId = (EditText) findViewById(R.id.editTextId);
        //btn = (Button) findViewById(R.id.buttonget);




        //Initializing our superheroes list
        listHeroes = new ArrayList<>();

        getData();
    }

    private void getData() {

        String carer_email= Prefs.with(clientvisit.this).getEmail();

        Toast.makeText(clientvisit.this, "" + carer_email, Toast.LENGTH_LONG).show();

        //Toast.makeText(getApplicationContext(), category, Toast.LENGTH_LONG).show();
        String url = getResources().getString(R.string.url) + "action=view_appointment&carer_email="+carer_email;
        url = url.replaceAll(" ", "%20");
        Log.d("VURL", url + "");
        //Creating a json array request
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("Response", response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String scode = jsonResponse.optString("code");
                    Log.wtf("responseCode", scode + " fff " + response);

                    //JSONArray classj=jsonResponse.getJSONArray("classes");
                    JSONArray Data = jsonResponse.getJSONArray("classes_cart");
                    parseData(Data);


                } catch (JSONException e) {
                    Toast.makeText(clientvisit.this,
                            "No data Received ",
                            Toast.LENGTH_LONG).show();


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("USER", "Error: " + error.getMessage());
                Toast.makeText(clientvisit.this,"No data Received", Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
                //dialog.dismiss();
            }
        });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(strReq);
        // Adding request to request queue
        // MyApplication.getInstance().addToRequestQueue(strReq);

    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Modalclientview superHero = new Modalclientview();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                superHero.setClient_id(json.getString(CARER_ID));
                superHero.setClient_name(json.getString(CARER_NAME));
                superHero.setClient_email(json.getString(CARER_MAIL));
                superHero.setClient_phone(json.getString(CARER_NUM));
                superHero.setClient_address(json.getString(CARER_ADD));
                superHero.setStart_date(json.getString(CARER_DATE));
                superHero.setStart_time(json.getString(CARER_STIME));
                superHero.setEnd_time(json.getString(CARER_ETIME));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            listHeroes.add(superHero);
        }


        adapter = new CardAdapterClient(listHeroes, clientvisit.this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder back = new AlertDialog.Builder(clientvisit.this);
        back.setTitle("Are you sure want to leave now?");
        back.setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                endActivity();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialogback = back.create();
        dialogback.show();
    }

    public void endActivity() {
        this.finish();
    }
}
