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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    String Username, Password;
    EditText UsernameEt, PasswordEt;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsernameEt = findViewById(R.id.usernameEt);
        PasswordEt = findViewById(R.id.passwordEt);

        sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);

        findViewById(R.id.LoginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeKeyboard();
                Username = UsernameEt.getText().toString().trim().toUpperCase();
                Password = PasswordEt.getText().toString().trim();

                if (Username.isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                } else if (Password.isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter Password..", Toast.LENGTH_SHORT).show();
                } else {


                    LoginValidation(Username,Password);

                }
            }
        });
    }

    private void LoginValidation(String username, String password) {
        ProgressDialog dialog = new ProgressDialog(Login.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringRequest request = new StringRequest(Request.Method.GET, "http://tsm.ecssofttech.com/SSG_PHP/loginUser.php?Password="+password+"&AccountID="+username+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("notfound")){
                    Toast.makeText(Login.this, "Please enter correct Username and Password", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String AccountID = object.getString("AccountID");
                            String Password = object.getString("Password");

                            if (AccountID.equals(Password)) {
                                Intent in = new Intent(Login.this, UpdatePassword.class);
                                in.putExtra("AccountID", AccountID);
                                startActivity(in);
                                finish();

                            } else {

                                editor.putString("Username", AccountID);
                                editor.apply();
                                startActivity(new Intent(Login.this, RegisterDashboard.class));
                                finish();

                            }
                        }


                    } catch (Exception e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        Log.e("loginValidationerror", e.getMessage());
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Something went wrong try later..", Toast.LENGTH_SHORT).show();
                Log.e("UserLoginLog",error.getMessage());
                dialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(Login.this);
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
}