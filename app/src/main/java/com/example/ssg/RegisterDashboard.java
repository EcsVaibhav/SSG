package com.example.ssg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_dashboard);

        findViewById(R.id.oneBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RegisterDashboard.this,RegisterUser.class);
                in.putExtra("Product","Mixer");
                startActivity(in);

            }
        });

        findViewById(R.id.TwoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RegisterDashboard.this,RegisterUser.class);
                in.putExtra("Product","AttaChakki");
                startActivity(in);

            }
        });

        findViewById(R.id.threeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(RegisterDashboard.this,RegisterUser.class);
                in.putExtra("Product","SmartTV");
                startActivity(in);
            }
        });

        findViewById(R.id.fourBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RegisterDashboard.this,RegisterUser.class);
                in.putExtra("Product","Fridge");
                startActivity(in);

            }
        });
    }
}