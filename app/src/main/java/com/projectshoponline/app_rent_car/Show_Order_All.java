package com.projectshoponline.app_rent_car;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Show_Order_All extends AppCompatActivity {

    ArrayList<HashMap<String, String>> MyArrList;

    ImageView imgview1;

    String strMemberID = "";

    Button cmdDelete;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_order_all);
        //setTitle("Medicinal plants and herbs");


//        //*** Permission StrictMode
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//
//        //*** Get Session Login
//        final UserHelper usrHelper = new UserHelper(this);
//
//        //*** Get Login Status
//        if(!usrHelper.getLoginStatus())
//        {
//            Intent newActivity = new Intent(Show_Order_All.this, MainActivity.class);
//            startActivity(newActivity);
//        }
//
//        //*** Get Member ID from Session
//        strMemberID = usrHelper.getMemberID();

        ShowData();


        // btnSearch
        final Button btnSearch = (Button) findViewById(R.id.btnSearch);
        //btnSearch.setBackgroundColor(Color.TRANSPARENT);
        // Perform action on click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //btnSearch.setText(strKeySearch.getText());
                ShowData();
            }
        });

    }

    public void ShowData()
    {
        // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1);

        // keySearch
        EditText strKeySearch = (EditText)findViewById(R.id.txtKeySearch);

        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);


        //String ip = "nap21.fulba.com";
       // String url = "http://projectshoponline.com/rentcar/app_get_all_orders_by_user.php?user_id="+strMemberID;

        String url = "http://projectshoponline.com/rentcar/app_get_all_orders.php";
        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            String txtKeyword = strKeySearch.getText().toString();

            params.add(new BasicNameValuePair("txtKeyword", txtKeyword));

            JSONArray data = new JSONArray(getJSONUrl(url, params));
            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);


                map = new HashMap<String, String>();
                map.put("order_id", c.getString("order_id"));
                map.put("order_product_id", c.getString("order_product_id"));
                map.put("order_product_name", c.getString("order_product_name"));
                map.put("order_product_img", c.getString("order_product_img"));

                MyArrList.add(map);

            }

            lisView1.setAdapter(new ImageAdapter(this));

            lisView1.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {


//                    String s_ID = MyArrList.get(position).get("category_id")
//                            .toString();
//
//                    Intent newActivity = new Intent(Page_Order.this,Show_All.class);
//                    newActivity.putExtra("category_id", s_ID);
//                    startActivity(newActivity);



                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("Json", e.toString());
            //e.printStackTrace();
        }
    }


    public class ImageAdapter extends BaseAdapter
    {
        private Context context;

        public ImageAdapter(Context c)
        {
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArrList.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.show_order_all_column, null);
            }
            // imgCmdDelete
            cmdDelete = (Button) convertView.findViewById(R.id.btnDelete);
            //cmdDelete.setBackgroundColor(Color.TRANSPARENT);

            final AlertDialog.Builder adb1 = new AlertDialog.Builder(Show_Order_All.this);
            final AlertDialog.Builder adb2 = new AlertDialog.Builder(Show_Order_All.this);

            cmdDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    adb1.setTitle("Delete?");
                    adb1.setMessage("Are you sure delete [" + MyArrList.get(position).get("order_product_name") + "]");
                    adb1.setNegativeButton("Cancel", null);
                    adb1.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            String url = "http://projectshoponline.com/rentcar/app_get_delete_orders.php";
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("order_id", MyArrList.get(position).get("order_id")));

                            String resultServer = getJSONUrl(url, params);


                            String strStatusID = "0";
                            String strError = "Unknow Status";

                            try {
                                JSONObject c = new JSONObject(resultServer);
                                strStatusID = c.getString("StatusID");
                                strError = c.getString("Error");
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }


                            if (strStatusID.equals("0")) {

                                adb2.setTitle("Error! ");
                                adb2.setIcon(android.R.drawable.btn_star_big_on);
                                adb2.setPositiveButton("Close", null);
                                adb2.setMessage(strError);
                                adb2.show();
                            } else {
                                Toast.makeText(Show_Order_All.this, "Delete data successfully.",
                                        Toast.LENGTH_SHORT).show();
                                ShowData();
                            }

                        }
                    });
                    adb1.show();
                }
            });

            // R.id.
            TextView txt_id = (TextView) convertView.findViewById(R.id.txt_id);
            txt_id.setPadding(10, 0, 0, 0);
            txt_id.setText(MyArrList.get(position).get("order_product_id") + ".");

            // R.id.
            TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
            txt1.setPadding(5, 0, 0, 0);
            txt1.setText(MyArrList.get(position).get("order_product_name") + ".");

            // ImageView imgview1 = (ImageView) convertView.findViewById(R.id.image1);
            imgview1 = (ImageView) convertView.findViewById(R.id.image1);


            Picasso.with(Show_Order_All.this)
                    .load(""+MyArrList.get(position).get("order_product_img"))
                    // .placeholder(R.drawable.placeholder)   // optional
                    //.error(R.drawable.error)      // optional
                    //.resize(400,400)                        // optional
                    .into(imgview1);

            return convertView;

        }

    }


    public String getJSONUrl(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);


        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
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



}
