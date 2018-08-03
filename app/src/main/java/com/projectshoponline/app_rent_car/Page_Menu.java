package com.projectshoponline.app_rent_car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Page_Menu extends AppCompatActivity{

    String strMemberID = "";

    String New_String_id;
    String New_String_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_menu);

        //*** Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //*** Get Session Login
        final UserHelper usrHelper = new UserHelper(this);

        //*** Get Login Status
        if(!usrHelper.getLoginStatus())
        {
            Intent newActivity = new Intent(Page_Menu.this, MainActivity.class);
            startActivity(newActivity);
        }

        //*** Get Member ID from Session
        strMemberID = usrHelper.getMemberID();

        //*** Show User Info
        showUserLoginInfo();


//        //*** Button Logout
//        final Button b_Show_Place = (Button) findViewById(R.id.b_Show_Place);
//        b_Show_Place.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//                // Goto Acitity2
//                Intent newActivity = new Intent(Page_Menu.this,Show_Place.class);
//                startActivity(newActivity);
//            }
//        });
//
//        //*** Button Logout
//        final Button b_Add_Place = (Button) findViewById(R.id.b_add_Place);
//        b_Add_Place.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//                // Goto Acitity2
//                Intent newActivity = new Intent(Page_Menu.this,Page_Add_Upload_Image.class);
//                startActivity(newActivity);
//            }
//        });
        //*** Button Next
        final Button b_My_Profile= (Button) findViewById(R.id.b_My_Profile);
        b_My_Profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent newActivity = new Intent(Page_Menu.this, My_Profile.class);
                startActivity(newActivity);
            }
        });

        //*** Button Next
        final Button b_Show_Category = (Button) findViewById(R.id.b_Show_Category);
        b_Show_Category.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent newActivity = new Intent(Page_Menu.this, Show_Category.class);
                startActivity(newActivity);
            }
        });

        //*** Button Next
        final Button b_Page_My_Order = (Button) findViewById(R.id.b_Page_My_Order);
        b_Page_My_Order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent newActivity = new Intent(Page_Menu.this, Page_Order.class);
                startActivity(newActivity);
            }
        });

//        //*** Button Next
//        final Button  b_Employee_All = (Button) findViewById(R.id.b_Employee_All);
//        b_Employee_All.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Goto Activity 2
//                Intent newActivity = new Intent(Page_Menu.this, Employee_All.class);
//                startActivity(newActivity);
//            }
//        });
//
//        final Button  b_Page_Roza_1 = (Button) findViewById(R.id.b_Page_Roza_1);
//        b_Page_Roza_1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Goto Activity 2
//                Intent newActivity = new Intent(Page_Menu.this, Page_Roza_1.class);
//                startActivity(newActivity);
//            }
//        });
//
//        final Button  b_Page_Show_Score = (Button) findViewById(R.id.b_Page_Show_Score);
//        b_Page_Show_Score.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Goto Activity 2
//                Intent newActivity = new Intent(Page_Menu.this, Page_Show_Score.class);
//                startActivity(newActivity);
//            }
//        });
//
//        final Button  b_Page_Discomfort = (Button) findViewById(R.id.b_Discomfort);
//        b_Page_Discomfort.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Goto Activity 2
//                Intent newActivity = new Intent(Page_Menu.this, Page_Discomfort.class);
//                startActivity(newActivity);
//            }
//        });

        //*** Button Logout
        final Button btnLogout = (Button) findViewById(R.id.Logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Clear Session
                usrHelper.deleteSession();

                // Goto Acitity2
                Intent newActivity = new Intent(Page_Menu.this,MainActivity.class);
                startActivity(newActivity);
            }
        });


        /////////////// exitApp
        Button btnAppExit = (Button) findViewById(R.id.exitApp);
        btnAppExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(Page_Menu.this);
                dialog.setTitle("Exit");
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setCancelable(true);
                dialog.setMessage("Do you want to exit?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }




        }); /////////////// exitApp

    }
    /////////////// onBackPressed
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }  /////////////// onBackPressed





    public void showUserLoginInfo()
    {
        // txtMemberID,txtMemberID,txtUsername,txtPassword,txtName,txtEmail,txtTel
        final TextView tMemberID = (TextView)findViewById(R.id.txtMemberID);
        final TextView tUsername = (TextView)findViewById(R.id.txtUsername);
//        final TextView tPassword = (TextView)findViewById(R.id.txtPassword);
//        final TextView tAddress= (TextView)findViewById(R.id.txtAddress);
//        final TextView tEmail = (TextView)findViewById(R.id.txtEmail);
//        final TextView tTel = (TextView)findViewById(R.id.txtTel);



        String url = "http://projectshoponline.com/rentcar/app_get_user_id.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sMemberID", strMemberID));

        /** Get result from Server (Return the JSON Code)
         *
         * {"MemberID":"2","Username":"aa","Password":"aa","Name":"aa","Tel":"1234","Email":"aaa"}
         */

        String resultServer  = MyHttpLogin.getHttpPost(url,params);

        String strMemberID = "";
        String strUsername = "";
//        String strPassword = "";
//        String strAdress = "";
//        String strEmail = "";
//        String strTel = "";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strMemberID = c.getString("MemberID");
            strUsername = c.getString("Username");
//            strPassword = c.getString("Password");
//            strAdress = c.getString("Address");
//            strEmail = c.getString("Email");
//            strTel = c.getString("Tel");


            // ?????????????????????????????????//
            // NEW STRING ###################################//


            New_String_id = c.getString("MemberID");
            New_String_name = c.getString("Username");



            // NEW STRING ###################################//
            // ?????????????????????????????????//




            if(!strMemberID.equals(""))
            {
                tMemberID.setText(strMemberID);
                tUsername.setText(strUsername);
//                tPassword.setText(strPassword);
//                tAddress.setText(strAdress);
//                tEmail.setText(strEmail);
//                tTel.setText(strTel);
            }
            else
            {
                tMemberID.setText("-");
                tUsername.setText("-");
//                tPassword.setText("-");
//                tAddress.setText("-");
//                tEmail.setText("-");
//                tTel.setText("-");
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
 }
