package com.example.caretaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.Utility.Prefs;
import com.example.ottime;


public class profile extends AppCompatActivity {

    private TextView visitclient,otclient,salarycarer,Log,button;
    Prefs pref;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pref = new Prefs(getApplicationContext());
        context = profile.this;

        visitclient= findViewById(R.id.one);
        otclient= findViewById(R.id.personal);
        salarycarer= findViewById(R.id.medical);

        button = findViewById(R.id.btnn1);

        Log= findViewById(R.id.logout);
        pref = new Prefs(getApplicationContext());
        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.clearSession();
                Intent intent = new Intent(profile.this, MainActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });

        visitclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(profile.this,clientvisit.class);

                startActivity(intent);

            }
        });

        otclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(profile.this, ottime.class);

                startActivity(intent);

            }
        });

        salarycarer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(profile.this, Salary.class);

                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(profile.this, AppLauncher.class);

                startActivity(intent);


            }
        });
    }
}
