package com.example.ssg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class UpdatePassword extends AppCompatActivity {

    EditText hidepasswordET,confrimPasswordET;
    String hidepassword,confrimPassword,UserMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        hidepasswordET = findViewById(R.id.hidepasswordEt);
        confrimPasswordET = findViewById(R.id.confrimPasswordEt);

        findViewById(R.id.resetpassbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                hidepassword = hidepasswordET.getText().toString().trim();
                confrimPassword = confrimPasswordET.getText().toString().trim();
                UserMobile = getIntent().getStringExtra("Mobile");

                if (hidepassword.equals(confrimPassword)){
                    resetpassword(confrimPassword,UserMobile);
                }else {
                    Toast.makeText(UpdatePassword.this, "password and confirm password not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetpassword(String confrimPassword, String userMobile) {

        ProgressDialog dialog = new ProgressDialog(UpdatePassword.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, "http://tsm.ecssofttech.com/SSG_PHP/resetPassword.php?Password="+confrimPassword+"&Mobile="+userMobile+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("updated")){
                    Toast.makeText(UpdatePassword.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(new Intent(UpdatePassword.this,Login.class));
                    finishAffinity();
                }else {
                    Toast.makeText(UpdatePassword.this, "Something went wrong try later..", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdatePassword.this, "Something went wrong try later..", Toast.LENGTH_SHORT).show();
                Log.e("UserLoginLog",error.getMessage());
                dialog.dismiss();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(UpdatePassword.this);
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