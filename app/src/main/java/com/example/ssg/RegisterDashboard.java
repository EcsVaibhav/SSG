package com.example.ssg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import meow.bottomnavigation.MeowBottomNavigation;

public class RegisterDashboard extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    ImageView menuBtn;

    TextView CcountTV,TcountTV;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_dashboard);

        menuBtn = findViewById(R.id.menuBtn);
        CcountTV = findViewById(R.id.CcountTV);
        TcountTV = findViewById(R.id.TcountTV);

        sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String Umobile = sharedPreferences.getString("Username"," ");
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(RegisterDashboard.this, menuBtn);
                popupMenu.getMenuInflater().inflate(R.menu.registerdashboardmenu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_profile) {

                            startActivity(new Intent(RegisterDashboard.this, UserProfile.class));
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


        getRcount(Umobile);
    }

    private void getRcount(String mob) {

        ProgressDialog dialog = new ProgressDialog(RegisterDashboard.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, "http://tsm.ecssofttech.com/SSG_PHP/getRegistrationCount.php?Referby=SSG-"+mob+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String Ccount = object.getString("Ccount");
                        String Tcount = object.getString("Tcount");

                        CcountTV.setText(Ccount);
                        TcountTV.setText(Tcount);

                        dialog.dismiss();


                    }


                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterDashboard.this, "Something went wrong try later..", Toast.LENGTH_SHORT).show();
                Log.e("UserLoginLog",error.getMessage());
                dialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(RegisterDashboard.this);
        queue.add(request);
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