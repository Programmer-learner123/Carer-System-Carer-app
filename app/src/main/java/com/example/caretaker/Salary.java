
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
import com.example.caretaker.Adapter.SalaryAdapter;
import com.example.caretaker.Model.ModelSalary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Salary extends AppCompatActivity {

    private List<ModelSalary> listHeroes;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    // public static final String TAG_MESS = "mess_name";
    public static final String EMAIL = "carer_email";
    public static final String SALARY = "salary";

    public static final String DAYS = "days";
    public static final String CARER_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

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

        String carer_email= Prefs.with(Salary.this).getEmail();

        Toast.makeText(Salary.this, "" + carer_email, Toast.LENGTH_LONG).show();

        //Toast.makeText(getApplicationContext(), category, Toast.LENGTH_LONG).show();
        String url = getResources().getString(R.string.url) +"action=salary&carer_email="+carer_email;
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
                    JSONArray Data = jsonResponse.getJSONArray("classes_salary");
                    parseData(Data);


                } catch (JSONException e) {
                    Toast.makeText(Salary.this,"No data Received",
                            Toast.LENGTH_LONG).show();


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("USER", "Error: " + error.getMessage());
                Toast.makeText(Salary.this,"No data Received", Toast.LENGTH_SHORT).show();
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
            ModelSalary superHero = new ModelSalary();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                superHero.setCarer_email(json.getString(EMAIL));
                superHero.setSalary(json.getString(SALARY));
                superHero.setDays(json.getString(DAYS));
                superHero.setDate(json.getString(CARER_DATE));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            listHeroes.add(superHero);
        }


        adapter = new SalaryAdapter(listHeroes, Salary.this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder back = new AlertDialog.Builder(Salary.this);
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


