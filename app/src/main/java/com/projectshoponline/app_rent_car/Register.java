package com.projectshoponline.app_rent_car;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Permission StrictMode
        permissionMode();

//        Create Toolbar
        createToolbar();

        //Save Controller
        saveController();

        //Next Controller
        nextController();

    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setSubtitle("Please Fill All Every Blank");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void nextController() {
        final Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent newActivity = new Intent(Register.this,Login.class);
                startActivity(newActivity);
            }
        });//*** Button Next
    }

    private void saveController() {
        final Button btnSave = (Button) findViewById(R.id.btnSave);
        // Perform action on click
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SaveData())
                {
                    // When Save Complete
                }
            }
        });
    }

    private void permissionMode() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public boolean SaveData()
    {

        // txtUsername,txtPassword,txtName,txtEmail,txtTel
        final EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        final EditText txtConPassword = (EditText)findViewById(R.id.txtConPassword);
        final EditText txtAddress = (EditText)findViewById(R.id.txtAddress);
        final EditText txtEmail = (EditText)findViewById(R.id.txtEmail);
        final EditText txtTel = (EditText)findViewById(R.id.txtTel);
        final EditText txtFname = (EditText)findViewById(R.id.txtFname);
        final EditText txtLname = (EditText)findViewById(R.id.txtLname);
        final EditText txtCiziten_id = (EditText)findViewById(R.id.txtCiziten_id);
        final EditText txtDriver_license_number = (EditText)findViewById(R.id.txtDriver_license_number);


        // Dialog
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);

        // Check Citizen ID
        if(txtCiziten_id.getText().length() == 0)
        {
            ad.setMessage("Please input [Citizen ID] ");
            ad.show();
            txtCiziten_id.requestFocus();
            return false;
        }
//
//        // Check Drivet License Number
        if(txtDriver_license_number.getText().length() == 0)
        {
            ad.setMessage("Please input [Driver License number] ");
            ad.show();
            txtDriver_license_number.requestFocus();
            return false;
        }

        // Check Username
        if(txtUsername.getText().length() == 0)
        {
            ad.setMessage("Please input [Username] ");
            ad.show();
            txtUsername.requestFocus();
            return false;
        }
        // Check Password
        if(txtPassword.getText().length() == 0 || txtConPassword.getText().length() == 0 )
        {
            ad.setMessage("Please input [Password/Confirm Password] ");
            ad.show();
            txtPassword.requestFocus();
            return false;
        }
        // Check Password and Confirm Password (Match)
        if(!txtPassword.getText().toString().equals(txtConPassword.getText().toString()))
        {
            ad.setMessage("Password and Confirm Password Not Match! ");
            ad.show();
            txtConPassword.requestFocus();
            return false;
        }
        // Check First Name
//        if(txtFname.getText().length() == 0)
//        {
//            ad.setMessage("Please input [First Name] ");
//            ad.show();
//            txtFname.requestFocus();
//            return false;
//        }
        // Check Email
        if(txtEmail.getText().length() == 0)
        {
            ad.setMessage("Please input [Email] ");
            ad.show();
            txtEmail.requestFocus();
            return false;
        }
        // Check Tel
//        if(txtTel.getText().length() == 0)
//        {
//            ad.setMessage("Please input [Tel] ");
//            ad.show();
//            txtTel.requestFocus();
//            return false;
//        }



        String url = "http://projectshoponline.com/rentcar/app_register.php";


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("dataname1", txtUsername.getText().toString()));
        params.add(new BasicNameValuePair("dataname2", txtPassword.getText().toString()));
        params.add(new BasicNameValuePair("dataname3", txtAddress.getText().toString()));
        params.add(new BasicNameValuePair("dataname4", txtEmail.getText().toString()));
        params.add(new BasicNameValuePair("dataname5", txtTel.getText().toString()));
        params.add(new BasicNameValuePair("dataname6", txtFname.getText().toString()));
        params.add(new BasicNameValuePair("dataname7", txtLname.getText().toString()));
        params.add(new BasicNameValuePair("dataname8", txtCiziten_id.getText().toString()));
        params.add(new BasicNameValuePair("dataname9", txtDriver_license_number.getText().toString()));


        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"1","Error":""}
         */

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "Unknow Status!";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strStatusID = c.getString("StatusID");
            strError = c.getString("Error");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Prepare Save Data
        if(strStatusID.equals("0"))
        {
            ad.setMessage(strError);
            ad.show();
        }
        else
        {
            Toast.makeText(Register.this, "Save Data Successfully", Toast.LENGTH_SHORT).show();
            txtUsername.setText("");
            txtPassword.setText("");
            txtConPassword.setText("");
            txtAddress.setText("");
            txtEmail.setText("");
            txtTel.setText("");
            txtFname.setText("");
            txtLname.setText("");
            txtCiziten_id.setText("");
            txtDriver_license_number.setText("");

            // Goto Activity2
            Intent newActivity = new Intent(Register.this,Login.class);
            startActivity(newActivity);


        }

//        // Goto Activity2
//        Intent newActivity = new Intent(Register.this,Login.class);
//        startActivity(newActivity);


        return true;
    }


    public String getHttpPost(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            // httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


    // ‚§È¥ ‡¡◊ËÕ‡«≈“æ‘¡æÏ§’∫Õ¥ ·µ–∑’Ë«Ë“ß ®–´ÈÕπ§’∫Õ¥≈ß¡“¥È“π≈Ë“ß
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        hideSoftKeyboard(Register.this);

        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        //////////////////////////////// ‚§È¥ ‡¡◊ËÕ‡«≈“æ‘¡æÏ§’∫Õ¥ ·µ–∑’Ë«Ë“ß
        //////////////////////////////// ®–´ÈÕπ§’∫Õ¥≈ß¡“¥È“π≈Ë“ß\\\\\\\\\\\\\\\\\\\\\\

    }
}

