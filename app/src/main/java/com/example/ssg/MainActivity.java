package com.example.ssg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
                String UsernamePreferance = sharedPreferences.getString("Username","");

                if(UsernamePreferance.isEmpty()) {
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                }else {
                    startActivity(new Intent(MainActivity.this,RegisterDashboard.class));
                    finish();
                }
            }
        },2000);
    }
}