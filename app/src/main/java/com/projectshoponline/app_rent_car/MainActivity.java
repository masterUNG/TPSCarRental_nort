package com.projectshoponline.app_rent_car;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Register Controller
        registerController();



        //Button
        Button gotoLogin = (Button) findViewById(R.id.gotoLogin);
        gotoLogin.setOnClickListener (new View.OnClickListener() {

            public void onClick(View V) {
                Intent intent =  new Intent(V.getContext(), Login.class);
                startActivityForResult(intent, 0);
            }

        });//Button




        //Button
        Button goto_Login_Admin = (Button) findViewById(R.id.goto_Login_Admin);
        goto_Login_Admin.setOnClickListener (new View.OnClickListener() {

            public void onClick(View V) {
                Intent intent =  new Intent(V.getContext(), Login_Admin.class);
                startActivityForResult(intent, 0);
            }

        });//Button



        /////////////// exitApp
        Button btnAppExit = (Button) findViewById(R.id.exitApp);
        btnAppExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
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

    private void registerController() {
        Button gotoRegister = (Button) findViewById(R.id.gotoRegister);
        gotoRegister.setOnClickListener (new View.OnClickListener() {

            public void onClick(View V) {
                Intent intent =  new Intent(V.getContext(), Register.class);
                startActivityForResult(intent, 0);
            }

        });//Button
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


}
