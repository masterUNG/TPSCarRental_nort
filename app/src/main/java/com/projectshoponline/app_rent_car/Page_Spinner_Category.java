package com.projectshoponline.app_rent_car;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Page_Spinner_Category extends AppCompatActivity {

    Spinner spinner1;

    public static ArrayList<String> Service_Id = null;

    public static ArrayList<String> Service1 = null;
    public static ArrayList<String> Service2 = null;

    public static ArrayList<String> Service_new= null;

    String service_id;
    String S_New;


    Boolean chk_work_dept1 = false;

    EditText etView1;


    Button btnSubmit;
    ///////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_spinner_category);


        etView1 = (EditText) findViewById(R.id.etView1);
        //////////////////////////////////////////////////////////////////

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        //*** Button Next
        final Button b_action_1 = (Button) findViewById(R.id.b_action_1);
        b_action_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent intent = new Intent(Page_Spinner_Category.this,Show_All.class);
                intent.putExtra("text_1", etView1.getText().toString());

                startActivity(intent);
            }
        });//*** Button Next



        Service_Id = new ArrayList<String>();
        Service1 = new ArrayList<String>();
        Service2 = new ArrayList<String>();
        Service_new = new ArrayList<String>();


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Page_Spinner_Category.this,Service_Id.get(position),Toast.LENGTH_LONG).show();


                etView1.setText(Service_Id.get(position).toString());
                //et_place_type_id.setText(Service_Id.get(position).toString());
                // Intent intent = new Intent(this, Page_Helpdesk_Show_All.class);

                // Intent intent = new Intent();
                // Intent intent = new Intent(Page_Helpdesk_Show_All.this,Page_Helpdesk_Show_All_Detail.class);
                // intent.putExtra("text_1", Service_Id.get(position).toString());
                //intent.putExtra("text_1", etView1.getText().toString());
                //intent.putExtra("text_2", etView2.getText().toString());
                // startActivity(intent);



                if (spinner1.getSelectedItem() == "Please select Work Department") {
                    chk_work_dept1 = true;
                    //Do nothing.



                } else {
                    chk_work_dept1 = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        getData();
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Page_Spinner_Category_Config.SPINNER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Service");
                    Log.e("onResponse: ", jsonObject + "");
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                        service_id =jsonObject1.getString(Page_Spinner_Category_Config.SPINNER_ID);
                        String service1 = jsonObject1.getString(Page_Spinner_Category_Config.SPINNER_TEXT1);
                        String service2 = jsonObject1.getString(Page_Spinner_Category_Config.SPINNER_TEXT2);
                        //String service_new = jsonObject1.getString(Config.SPINNER_TEXT1)+(Config.SPINNER_TEXT2);

                        Service_Id.add(jsonObject1.getString(Page_Spinner_Category_Config.SPINNER_ID));
                        // Service_Id.add(jsonObject1.getString(Config.SPINNER_ID));

                        Service1.add(jsonObject1.getString(Page_Spinner_Category_Config.SPINNER_TEXT1));
                        Service2.add(jsonObject1.getString(Page_Spinner_Category_Config.SPINNER_TEXT2));

                        // Service_new.add((jsonObject1.getString(Config.SPINNER_TEXT1)+"").add(jsonObject1.getString(Config.SPINNER_TEXT2));

                        //String S_New = (""+Service1 +""+ Service1);
                        //Service_new.add(jsonObject1.getString(Config.SPINNER_ID)+(Config.SPINNER_TEXT));
                        // Service.add("Please select Work Department");
                        spinner1.setPrompt("Please select Work Department");
                        spinner1.setAdapter(new ArrayAdapter<String>(Page_Spinner_Category.this, android.R.layout.simple_dropdown_item_1line, Service1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(this);
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //  }



    }

}