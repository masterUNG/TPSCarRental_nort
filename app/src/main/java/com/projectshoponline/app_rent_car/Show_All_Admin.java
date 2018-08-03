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
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Show_All_Admin extends AppCompatActivity {

    ArrayList<HashMap<String, String>> MyArrList;

    ImageView imgview1;
    TextView tv_category_id;
    String New_String_category_id;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_admin);

        //////////////////
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ////////////////

        tv_category_id = (TextView) findViewById(R.id.tv_category_id);


        final Intent intent = getIntent();

        String s_category_id = intent.getStringExtra("category_id");

        New_String_category_id = intent.getStringExtra("category_id");

        tv_category_id.setText("" + s_category_id);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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

        final Intent intent = getIntent();

        String s_category_id = intent.getStringExtra("category_id");


        //  String ip = "nap21.fulba.com";
        // String url = "http://"+ip+"/json/showAllDatapy.php";
        // String url = "http://projectshoponline.com/rentcar/app_show_category.php";
        String url = "http://projectshoponline.com/rentcar/app_show_all.php?category_id="+ s_category_id;

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
                map.put("product_id", c.getString("product_id"));
                map.put("product_name", c.getString("product_name"));
                map.put("product_price", c.getString("product_price"));
                map.put("product_img_1", c.getString("product_img_1"));



                MyArrList.add(map);

            }

            lisView1.setAdapter(new ImageAdapter(this));

            lisView1.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {


                    String s_ID = MyArrList.get(position).get("product_id")
                            .toString();

                    String s_NAME = MyArrList.get(position).get("product_name")
                            .toString();

                    String s_PRICE = MyArrList.get(position).get("product_price")
                            .toString();

                    String s_IMAGE = MyArrList.get(position).get("product_img_1")
                            .toString();
                  //  Intent newActivity = new Intent(Show_All_Admin.this,Show_Detail_And_Rent.class);
                    // newActivity.putExtra("category_id", New_String_category_id);
                  //  newActivity.putExtra("product_id", s_ID);
                 //   newActivity.putExtra("product_name", s_NAME);
                  //  newActivity.putExtra("product_price", s_PRICE);
                  //  newActivity.putExtra("product_img_1", s_IMAGE);
                  //  startActivity(newActivity);


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
                convertView = inflater.inflate(R.layout.show_all_column, null);
            }

            // R.id.
            TextView txt_id = (TextView) convertView.findViewById(R.id.txt_id);
            txt_id.setPadding(10, 0, 0, 0);
            txt_id.setText(MyArrList.get(position).get("product_id") + ".");

            // R.id.
            TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
            txt1.setPadding(5, 0, 0, 0);
            txt1.setText(MyArrList.get(position).get("product_name") + ".");

            // R.id.
            TextView txt2 = (TextView) convertView.findViewById(R.id.txt2);
            txt2.setPadding(5, 0, 0, 0);
            txt2.setText(MyArrList.get(position).get("product_price") + ".");

            // ImageView imgview1 = (ImageView) convertView.findViewById(R.id.image1);
            imgview1 = (ImageView) convertView.findViewById(R.id.image1);


            Picasso.with(Show_All_Admin.this)
                    .load(""+MyArrList.get(position).get("product_img_1"))
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

