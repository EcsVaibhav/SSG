package com.example.ssg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Stage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        navBar();
    }
    private void navBar() {

        RelativeLayout HomeRLBtn,StageRLBtn,ProfileRLBtn;
        HomeRLBtn = findViewById(R.id.HomeRLBtn);
        StageRLBtn = findViewById(R.id.StageRLBtn);
        ProfileRLBtn = findViewById(R.id.ProfileRLBtn);

        HomeRLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Stage.this,RegisterDashboard.class);
                overridePendingTransition(0,0);
                startActivity(in);
                finish();
            }
        });
        ProfileRLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Stage.this,UserProfile.class);
                overridePendingTransition(0,0);
                startActivity(in);
                finish();
            }
        });

    }
}