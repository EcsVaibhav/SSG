package com.example.ssg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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

public class UserProfile extends AppCompatActivity {

    TextView AcIdTV,NameTV,mobileTV,EmailTV,AddressTV,referTV,CcountTV,TcountTV;

    TextView AHnameTV,BankNameTV,BAccountNoTV,IFSCTV,NomineeTV,PidcountTV;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        navBar();

        AcIdTV = findViewById(R.id.AcIdTV);
        NameTV = findViewById(R.id.NameTV);
        mobileTV = findViewById(R.id.mobileTV);
        EmailTV = findViewById(R.id.EmailTV);
        AddressTV = findViewById(R.id.AddressTV);
        referTV = findViewById(R.id.referTV);
        CcountTV = findViewById(R.id.CcountTV);
        TcountTV = findViewById(R.id.TcountTV);
        PidcountTV = findViewById(R.id.PidcountTV);


        AHnameTV = findViewById(R.id.AHnameTV);
        BankNameTV = findViewById(R.id.BankNameTV);
        BAccountNoTV = findViewById(R.id.BAccountNoTV);
        IFSCTV = findViewById(R.id.IFSCTV);
        NomineeTV = findViewById(R.id.NomineeTV);


        dialog = new ProgressDialog(UserProfile.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getData();
    }
    private void navBar() {

        RelativeLayout HomeRLBtn,StageRLBtn,ProfileRLBtn;
        HomeRLBtn = findViewById(R.id.HomeRLBtn);
        StageRLBtn = findViewById(R.id.StageRLBtn);
        ProfileRLBtn = findViewById(R.id.ProfileRLBtn);

        StageRLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UserProfile.this,Stage.class);
                overridePendingTransition(0,0);
                startActivity(in);
                finish();
            }
        });
        HomeRLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UserProfile.this,RegisterDashboard.class);
                overridePendingTransition(0,0);
                startActivity(in);
                finish();
            }
        });

    }


    private void getData() {


        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String UserMobile = sharedPreferences.getString("Username"," ");
        StringRequest request = new StringRequest(Request.Method.GET, "http://tsm.ecssofttech.com/SSG_PHP/getUserdetails.php?AccountID="+UserMobile+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("notfound")){
                    Toast.makeText(UserProfile.this, "Something Went wrong try later..", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String IdCount = object.getString("IdCount");
                            String Name = object.getString("Name");
                            String Mobile = object.getString("Mobile");
                            String Email = object.getString("Email");
                            String Address = object.getString("Address");
                            String RegisterUnder = object.getString("ReferalCode");
                            String AccountID = object.getString("AccountID");

                            String AHname = object.getString("BAHname");
                            String BankName = object.getString("BankName");
                            String BAccountNo = object.getString("BAccountNo");
                            String IfscCode = object.getString("IfscCode");
                            String Nominee = object.getString("Nominee");

                            PidcountTV.setText("My Total ID \n"+IdCount);
                            AHnameTV.setText(AHname);
                            BankNameTV.setText(BankName);
                            BAccountNoTV.setText(BAccountNo);
                            IFSCTV.setText(IfscCode);
                            NomineeTV.setText(Nominee);

                            AcIdTV.setText(AccountID);
                            NameTV.setText(Name);
                            mobileTV.setText(Mobile);
                            EmailTV.setText(Email);
                            AddressTV.setText(Address);
                            referTV.setText(RegisterUnder);

                            getRcount(UserMobile);

                        }


                    } catch (Exception e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        Log.e("ProfilegetDataerror", e.getMessage());
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfile.this, "Something went wrong try later..", Toast.LENGTH_SHORT).show();
                Log.e("UserLoginLog",error.getMessage());
                dialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(UserProfile.this);
        queue.add(request);
    }


    private void getRcount(String mob) {
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
                Toast.makeText(UserProfile.this, "Something went wrong try later..", Toast.LENGTH_SHORT).show();
                Log.e("UserLoginLog",error.getMessage());
                dialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(UserProfile.this);
        queue.add(request);
    }
}

