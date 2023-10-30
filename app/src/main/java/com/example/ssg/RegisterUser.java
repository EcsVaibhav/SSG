package com.example.ssg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
public class RegisterUser extends AppCompatActivity {

    String product,uName,uMobile,uMail,uAddress;
    EditText NameEt,MobileEt,MailEt,AddressEt,NomineeEt,BAccountNoET,ifscCodeET,BanknameET,BAHnameET;

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

        product = getIntent().getStringExtra("Product");
        
        findViewById(R.id.RegisterBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                uName = NameEt.getText().toString().trim();
                uMobile = MobileEt.getText().toString().trim();
                uMail = MailEt.getText().toString().trim();
                uAddress = AddressEt.getText().toString().trim();

                String nominee = NomineeEt.getText().toString().trim();
                String Baccountno = BAccountNoET.getText().toString().trim();
                String ifsccode = ifscCodeET.getText().toString().trim();
                String bankname = BanknameET.getText().toString().trim();
                String BAHname = BAHnameET.getText().toString().trim();

                if (uName.isEmpty()){
                    Toast.makeText(RegisterUser.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                }else if (uMobile.length()!=10){
                    Toast.makeText(RegisterUser.this, "Enter Valid mobile Number", Toast.LENGTH_SHORT).show();
                }else if (!(isEmailValid(uMail))){
                    Toast.makeText(RegisterUser.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }else if (uAddress.isEmpty()){
                    Toast.makeText(RegisterUser.this, "Enter Address", Toast.LENGTH_SHORT).show();
                }else if (nominee.isEmpty()){
                    Toast.makeText(RegisterUser.this, "Enter Nominee", Toast.LENGTH_SHORT).show();
                }else if (Baccountno.isEmpty()){
                    Toast.makeText(RegisterUser.this, "Enter Bank account number", Toast.LENGTH_SHORT).show();
                }else if (ifsccode.isEmpty()){
                    Toast.makeText(RegisterUser.this, "Enter IFSC code", Toast.LENGTH_SHORT).show();
                }else if (bankname.isEmpty()){
                    Toast.makeText(RegisterUser.this, "Enter Bank Name", Toast.LENGTH_SHORT).show();
                }else if (BAHname.isEmpty()){
                    Toast.makeText(RegisterUser.this, "Enter Bank Account Holder name", Toast.LENGTH_SHORT).show();
                }else {

                    registerUser(uName,uMobile,uMail,uAddress,nominee,Baccountno,ifsccode,bankname,BAHname);
                }
                
            }
        });
        
        
        

    }

    private void registerUser(String uName, String uMobile, String uMail, String uAddress, String nominee, String baccountno, String ifsccode, String bankname, String BAHname) {
        ProgressDialog dialog = new ProgressDialog(RegisterUser.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String RMobile = sharedPreferences.getString("Username","");

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String TodayDate = dateFormat.format(currentDate);

        StringRequest request = new StringRequest(Request.Method.GET, "http://tsm.ecssofttech.com/SSG_PHP/registerUser.php?Name="+uName+"&Mobile="+uMobile+"&Email="+uMail+"&Address="+uAddress+"&Password="+uMobile+"&ReferalCode=SSG-"+RMobile+"&Product="+product+"&RegisterDate="+TodayDate+"&AccountID=SSG-"+uMobile+"&Nominee="+nominee+" &BAccountNo="+baccountno+"&IfscCode="+ifsccode+"&BankName="+bankname+"&BAHname="+BAHname+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("exits")){
                    Toast.makeText(RegisterUser.this, "Mobile Number Already exits.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else if (response.equals("inserted")){
                    Toast.makeText(RegisterUser.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterUser.this,RegisterDashboard.class));
                    finishAffinity();
                    dialog.dismiss();
                }else {
                    Toast.makeText(RegisterUser.this, "Something went wrong try later...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterUser.this, "Something went wrong try later", Toast.LENGTH_SHORT).show();
                Log.e("UserRegisterLog",error.getMessage());
                dialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(RegisterUser.this);
        queue.add(request);

    }

    private void closeKeyboard()
    {

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