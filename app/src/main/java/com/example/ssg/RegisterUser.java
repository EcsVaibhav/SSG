package com.example.ssg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterUser extends AppCompatActivity {

    String product, uName, uMobile, uMail, uAddress, selectedmode;
    EditText NameEt, MobileEt, MailEt, AddressEt, NomineeEt, BAccountNoET, ifscCodeET, BanknameET, BAHnameET, IDCountET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        NameEt = findViewById(R.id.nameET);
        MobileEt = findViewById(R.id.mobileET);
        MailEt = findViewById(R.id.emailET);
        AddressEt = findViewById(R.id.adressET);
        NomineeEt = findViewById(R.id.NomineeEt);
        BAccountNoET = findViewById(R.id.BAccountNoET);
        ifscCodeET = findViewById(R.id.ifscCodeET);
        BanknameET = findViewById(R.id.BanknameET);
        BAHnameET = findViewById(R.id.BAHnameET);
        IDCountET = findViewById(R.id.IDCountET);

        product = getIntent().getStringExtra("Product");

        findViewById(R.id.RegisterBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                uName = NameEt.getText().toString().trim();
                uMobile = MobileEt.getText().toString().trim();
                uMail = MailEt.getText().toString().trim();
                uAddress = AddressEt.getText().toString().trim();

                String IDCount = IDCountET.getText().toString().trim();
                String nominee = NomineeEt.getText().toString().trim();
                String Baccountno = BAccountNoET.getText().toString().trim();
                String ifsccode = ifscCodeET.getText().toString().trim();
                String bankname = BanknameET.getText().toString().trim();
                String BAHname = BAHnameET.getText().toString().trim();

                if (uMail.isEmpty()) {
                    uMail = "null";
                }
                if (uAddress.isEmpty()) {
                    uAddress = "null";
                }
                if (nominee.isEmpty()) {
                    nominee = "null";
                }
                if (Baccountno.isEmpty()) {
                    Baccountno = "null";
                }
                if (ifsccode.isEmpty()) {
                    ifsccode = "null";
                }
                if (bankname.isEmpty()) {
                    bankname = "null";
                }
                if (BAHname.isEmpty()) {
                    BAHname = "null";
                }

                if (IDCount.isEmpty()) {
                    Toast.makeText(RegisterUser.this, "Please enter Id Count", Toast.LENGTH_SHORT).show();
                }else if (Integer.parseInt(IDCount)<1){
                    Toast.makeText(RegisterUser.this, "You have to purchase at least 1 Id", Toast.LENGTH_SHORT).show();
                }else if (uName.isEmpty()) {
                    Toast.makeText(RegisterUser.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                } else if (uMobile.length() != 10) {
                    Toast.makeText(RegisterUser.this, "Enter Valid mobile Number", Toast.LENGTH_SHORT).show();
                } else if (!(isEmailValid(uMail))) {
                    Toast.makeText(RegisterUser.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (uAddress.isEmpty()) {
                    Toast.makeText(RegisterUser.this, "Enter Address", Toast.LENGTH_SHORT).show();
                } else {

                    paymentMode(IDCount, uName, uMobile, uMail, uAddress, nominee, Baccountno, ifsccode, bankname, BAHname);

                    /*registerUser(IDCount, uName, uMobile, uMail, uAddress, nominee, Baccountno, ifsccode, bankname, BAHname);*/
                }

            }
        });


    }

    private void paymentMode(String IDCount, String uName, String uMobile, String uMail, String uAddress, String nominee, String baccountno, String ifsccode, String bankname, String BAHname) {


        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUser.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_for_selectpayment, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        TextView Rfee = dialogView.findViewById(R.id.RfeeTV);
        Spinner selectMode = dialogView.findViewById(R.id.paymodespinner);



        double ID = Double.parseDouble(IDCount);
        String RAmount = String.valueOf(ID*110);
        Rfee.setText("Registration Fees : "+RAmount+" Rs");

        String[] mode = {"Select Mode", "Cash", "Online"};

        ArrayAdapter adapter = new ArrayAdapter(RegisterUser.this, android.R.layout.simple_spinner_item, mode);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectMode.setAdapter(adapter);

        selectMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedmode = mode[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.Paybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(RegisterUser.this, RegisterDashboard.class));
                finishAffinity();*/
                if (selectedmode.equals("Select Mode")) {
                    Toast.makeText(RegisterUser.this, "Please select Mode", Toast.LENGTH_SHORT).show();
                } else {

                    if (selectedmode.equals("Online")){
                        Toast.makeText(RegisterUser.this, "This service temporary not available", Toast.LENGTH_SHORT).show();
                    }else {
                        alertDialog.dismiss();
                        registerUser(IDCount, uName, uMobile, uMail, uAddress, nominee, baccountno, ifsccode, bankname, BAHname);
                    }

                }

            }
        });
        alertDialog.show();
    }

    private void sendSMS(String name, String UMobile, String accountID) {

        String message = "Congratulation " + name + " You are successfully register with SSG.\nYour Username is : " + accountID + " \nYour Password is : " + accountID + "";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(UMobile, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Username and Password sent To Mobile number", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "SMS failed to send."+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void registerUser(String IDCount, String uName, String uMobile, String uMail, String uAddress, String nominee, String baccountno, String ifsccode, String bankname, String BAHname) {
        ProgressDialog dialog = new ProgressDialog(RegisterUser.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String RMobile = sharedPreferences.getString("Username", "");

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String TodayDate = dateFormat.format(currentDate);

        StringRequest request = new StringRequest(Request.Method.GET, "http://tsm.ecssofttech.com/SSG_PHP/registerUser.php?IdCount="+IDCount+"&Name=" + uName + "&Mobile=" + uMobile + "&Email=" + uMail + "&Address=" + uAddress + "&ReferalCode=SSG-" + RMobile + "&Product=" + product + "&RegisterDate=" + TodayDate + "&Nominee=" + nominee + " &BAccountNo=" + baccountno + "&IfscCode=" + ifsccode + "&BankName=" + bankname + "&BAHname=" + BAHname + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("failed")) {
                    Toast.makeText(RegisterUser.this, "Something went wrong try later...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else if (response.isEmpty()) {

                    Toast.makeText(RegisterUser.this, "Something went wrong try later...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                } else if (response.equals("notfound")) {
                    Toast.makeText(RegisterUser.this, "Technical error contact Customer care", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String UMobile = object.getString("Mobile");
                            String AccountID = object.getString("AccountID");
                            String Name = object.getString("Name");

                            sendSMS(Name, UMobile, AccountID);
                            Toast.makeText(RegisterUser.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterUser.this, RegisterDashboard.class));
                            finishAffinity();
                            dialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterUser.this, "Technical error contact Customer care", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterUser.this, "Something went wrong try later" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UserRegisterLog", error.getMessage());
                dialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(RegisterUser.this);
        queue.add(request);

    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();

        if (view != null) {

            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }


    public boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}