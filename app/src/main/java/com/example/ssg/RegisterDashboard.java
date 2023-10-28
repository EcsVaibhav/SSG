package com.example.ssg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class RegisterDashboard extends AppCompatActivity {

    ImageView menuBtn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_dashboard);

        menuBtn = findViewById(R.id.menuBtn);

        sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(RegisterDashboard.this, menuBtn);
                popupMenu.getMenuInflater().inflate(R.menu.registerdashboardmenu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_profile) {
                            Toast.makeText(RegisterDashboard.this, "Profile open", Toast.LENGTH_SHORT).show();
                        } else if (item.getItemId() == R.id.action_logout) {

                            SharedPreferences.Editor editor =   sharedPreferences.edit();
                            editor.remove("Username");
                            editor.apply();
                            Toast.makeText(RegisterDashboard.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterDashboard.this,Login.class));
                            finishAffinity();
                        } else {
                            return false;
                        }
                        return false;
                    }
                });

                popupMenu.show();

            }
        });



        findViewById(R.id.oneBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RegisterDashboard.this, RegisterUser.class);
                in.putExtra("Product", "Mixer");
                startActivity(in);

            }
        });

        findViewById(R.id.TwoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RegisterDashboard.this, RegisterUser.class);
                in.putExtra("Product", "AttaChakki");
                startActivity(in);

            }
        });

        findViewById(R.id.threeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(RegisterDashboard.this, RegisterUser.class);
                in.putExtra("Product", "SmartTV");
                startActivity(in);
            }
        });

        findViewById(R.id.fourBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RegisterDashboard.this, RegisterUser.class);
                in.putExtra("Product", "Fridge");
                startActivity(in);

            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterDashboard.this);
        builder.setTitle("Alert!")
                .setMessage("Are you sure you want to exit.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finishAffinity();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
}