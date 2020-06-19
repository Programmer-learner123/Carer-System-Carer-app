package com.example.caretaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AppLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);

        Button button = findViewById(R.id.fbButton);
        final EditText number = findViewById(R.id.mobile_number);
        final EditText msg = findViewById(R.id.message);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String MobileNumber = number.getText().toString();
                String Textmessage = msg.getText().toString();



                boolean installed = appInstalldCheck("com.whatsapp");

                if(installed){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+ "+91" + MobileNumber + "&text=" + Textmessage));
                    startActivity(intent);
                }else{

                    Toast.makeText(AppLauncher.this,"Whatsapp is not Intalled in your device!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean appInstalldCheck(String s) {

        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try{
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch(PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
}

