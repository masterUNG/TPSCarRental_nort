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

public class Show_Category extends AppCompatActivity {

    ArrayList<HashMap<String, String>> MyArrList;

    ImageView imgview1;


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_category);
        //setTitle("Medicinal plants and herbs");

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


        //String ip = "nap21.fulba.com";
        String url = "http://projectshoponline.com/rentcar/app_show_category.php";
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
                map.put("category_id", c.getString("category_id"));
                map.put("category_name", c.getString("category_name"));
                map.put("category_img", c.getString("category_img"));



                MyArrList.add(map);

            }

            lisView1.setAdapter(new ImageAdapter(this));

            lisView1.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {


                         String s_ID = MyArrList.get(position).get("category_id")
                            .toString();

                    Intent newActivity = new Intent(Show_Category.this,Show_All.class);
                    newActivity.putExtra("category_id", s_ID);
                    startActivity(newActivity);



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
                convertView = inflater.inflate(R.layout.show_category_column, null);
            }

                        // R.id.
            TextView txt_id = (TextView) convertView.findViewById(R.id.txt_id);
            txt_id.setPadding(10, 0, 0, 0);
            txt_id.setText(MyArrList.get(position).get("category_id") + ".");

                        // R.id.
            TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
            txt1.setPadding(5, 0, 0, 0);
            txt1.setText(MyArrList.get(position).get("category_name") + ".");

           // ImageView imgview1 = (ImageView) convertView.findViewById(R.id.image1);
            imgview1 = (ImageView) convertView.findViewById(R.id.image1);


            Picasso.with(Show_Category.this)
                    .load(""+MyArrList.get(position).get("category_img"))
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









//
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.squareup.picasso.Picasso;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.StatusLine;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by mdoublee on 1/12/2018 AD.
// */
//
//public class Show_Category extends AppCompatActivity {
//
//
//    TextView tvView1;
//
//    Button b_menu;
//
//    ArrayList<HashMap<String, String>> MyArrList;
//
////////////////////////////
//
//    //    private Context mContext;
////    //private Activity mActivity;
////
////    // private CoordinatorLayout mCLayout;
////
//    private ImageView imgview1;
////    private Uri mImageUri = Uri.parse("file:///android_asset/images/sunset_birds_flying_sky.jpeg");
//
//
//    /////////////////
//    @SuppressLint("NewApi")
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.show_category);
//
////
////
////
////        Button b_MainActivity = (Button) findViewById(R.id.b_MainActivity);
////        b_MainActivity.setOnClickListener(new View.OnClickListener() {
////
////            public void onClick(View V) {
////                Intent intent = new Intent(V.getContext(), MainActivity.class);
////                startActivityForResult(intent, 0);
////            }
////
////        });
//
//
//        //   tvView1 = (TextView) findViewById(R.id.tvView1);
//
//// final Intent intent = getIntent();
////        String sMedicine_ID = intent.getStringExtra("text_1");
////
////
////
////        tvView1.setText("" + sMedicine_ID);
//
//
//
//
//        // Permission StrictMode
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//
//        ShowData();
//
//        // btnSearch
//        final Button btnSearch = (Button) findViewById(R.id.btnSearch);
//        //btnSearch.setBackgroundColor(Color.TRANSPARENT);
//        // Perform action on click
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                ShowData();
//            }
//        });
//
//    }
//    //แสดงข้อมูล
//    public void ShowData() {
//
//        final ListView lisView1 = (ListView) findViewById(R.id.listView1);
//
//        EditText strKeySearch = (EditText) findViewById(R.id.txtKeySearch);
//
//        // Disbled Keyboard auto focus
//        InputMethodManager imm = (InputMethodManager) getSystemService(
//                Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);
//
//        //String url = "https://imsu.co/u/13570368/medicine.php";
//
//
//        // final Intent intent = getIntent();
//        // String sCategory_id = intent.getStringExtra("place_id");
//
//
//
//        // tvView1.setText(""+sCategory_id);
//
//
//        String url = "http://projectshoponline.com/rentcar/app_show_category.php";
//        // Paste Parameters
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));
//        try {
//            JSONArray data = new JSONArray(getJSONUrl(url, params));
//
//            MyArrList = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> map;
//
//            for (int i = 0; i < data.length(); i++) {
//                JSONObject c = data.getJSONObject(i);
//
//                map = new HashMap<String, String>();
//                map.put("category_id", c.getString("category_id"));
//                map.put("category_name", c.getString("category_name"));
////                map.put("product_detail", c.getString("product_detail"));
////                map.put("product_price", c.getString("product_price"));
//                map.put("category_img", c.getString("category_img"));
//
//
//                MyArrList.add(map);
//
//            }
//            //lisView1.setAdapter(new ImageAdapter(this));
//            lisView1.setAdapter(new ImageAdapter(this));
//            // OnClick Item
//            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> myAdapter, View myView,
//                                        int position, long mylng) {
//
//               //     String s_ID = MyArrList.get(position).get("hospital_id")
//               //             .toString();
//
//              //      Intent newActivity = new Intent(Show_Category.this,Show_All_Detail.class);
//              //      newActivity.putExtra("hospital_id", s_ID);
//              //      startActivity(newActivity);
//
//                }
//            });
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public class ImageAdapter extends BaseAdapter {
//        private Context context;
//
//        public ImageAdapter(Context c) {
//            context = c;
//        }
//
//        public int getCount() {
//            return MyArrList.size();
//        }
//
//        public Object getItem(int position) {
//            return position;
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.show_category_column, null);
//            }
//            // R.id.
//            TextView txt_id = (TextView) convertView.findViewById(R.id.txt_id);
//            txt_id.setPadding(10, 0, 0, 0);
//            txt_id.setText(MyArrList.get(position).get("category_id") + ".");
//
//            // R.id.
//            //TextView txtCatID = (TextView) convertView.findViewById(R.id.CatID);
//            // txtCatID.setPadding(5, 0, 0, 0);
//            //txtCatID.setText(MyArrList.get(position).get("CatID") + ".");
//
////            // R.id.
////            TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
////            txt1.setPadding(5, 0, 0, 0);
////            txt1.setText(MyArrList.get(position).get("product_code") + ".");
////
////            // R.id.
////            TextView txt2 = (TextView) convertView.findViewById(R.id.txt2);
////            txt2.setPadding(5, 0, 0, 0);
////            txt2.setText(MyArrList.get(position).get("product_herd") + ".");
//
//            // R.id.
//            TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
//            txt1.setPadding(5, 0, 0, 0);
//            txt1.setText(MyArrList.get(position).get("category_name") + ".");
//
//            // R.id.
////            TextView txt2 = (TextView) convertView.findViewById(R.id.txt2);
////            txt2.setPadding(5, 0, 0, 0);
////            txt2.setText(MyArrList.get(position).get("product_price") + ".");
////            //      R.id.
//
//
//            //R.id.
////             TextView txt3 = (TextView) convertView.findViewById(R.id.txt3);
////             txt3.setPadding(5, 0, 0, 0);
////            txt3.setText(MyArrList.get(position).get("Elder_Med_ID") + ".");
//            //  R.id.
//
//            //TextView txt4 = (TextView) convertView.findViewById(R.id.txt4);
//            //txt4.setPadding(5, 0, 0, 0);
//            //txt4.setText(MyArrList.get(position).get("Medicine_Img"));
////            // R.id.
////            TextView txtimage2 = (TextView) convertView.findViewById(R.id.product_img_name2);
////            txtimage2.setPadding(5, 0, 0, 0);
////            txtimage2.setText(MyArrList.get(position).get("product_img_name2"));
////            // R.id.
////            TextView txtimage3 = (TextView) convertView.findViewById(R.id.product_img_name3);
////            txtimage3.setPadding(5, 0, 0, 0);
////            txtimage3.setText(MyArrList.get(position).get("product_img_name3"));
//
//            //ImageView imgview1 = (ImageView) convertView.findViewById(R.id.image1);
//            imgview1 = (ImageView) convertView.findViewById(R.id.image1);
//
//            //   new DownloadImageTask(imgview1).execute(MyArrList.get(position).get("images_place_url"));
//
////            ImageView imgview2 = (ImageView) convertView.findViewById(R.id.image2);
////
////            new DownloadImageTask(imgview2).execute(MyArrList.get(position).get("product_img_name2"));
////
////            ImageView imgview3 = (ImageView) convertView.findViewById(R.id.image3);
////
////            new DownloadImageTask(imgview3).execute(MyArrList.get(position).get("product_img_name3"));
//
//
////            //Loading Image from URL
////            Picasso.with(this)
////                    .load("http://thaiprojectapp.com/kie/img/a1.jpg")
////                    .placeholder(R.drawable.placeholder)   // optional
////                    .error(R.drawable.error)      // optional
////                    .resize(400,400)                        // optional
////                    .into(imageView_1);
//
//            //            //Loading Image from URL
//
////            // Load the image into image view
////            Glide.with(mContext)
////                    //.load(mImageUri) // Load image from assets
////                    .load(mImageUrlString) // Image URL
////                    .centerCrop() // Image scale type
////                    .crossFade()
////                    .override(800,500) // Resize image
////                    .placeholder(R.drawable.placeholder) // Place holder image
////                    .error(R.drawable.error) // On error image
////                    .into(mImageView); // ImageView to display image
//
//
//
//            Picasso.with(Show_Category.this)
//                    .load(""+MyArrList.get(position).get("category_img"))
//                    // .placeholder(R.drawable.placeholder)   // optional
//                    //.error(R.drawable.error)      // optional
//                    //.resize(400,400)                        // optional
//                    .into(imgview1);
//
////            // Get the application context
////            mContext = getApplicationContext();
////            //mActivity = MainActivity.this;
////
////
////            // Load the image into image view
////            Glide.with(mContext)
////                    //.load(mImageUri) // Load image from assets
////                    .load(""+MyArrList.get(position).get("images_place_url")) // Image URL
////                    .centerCrop() // Image scale type
////                    .crossFade()
////                    .override(800,500) // Resize image
////                    .placeholder(R.drawable.placeholder) // Place holder image
////                    .error(R.drawable.error) // On error image
////                    .into(imgview1); // ImageView to display image
//
//
//
//            return convertView;
//
//        }
//
//    }
//
//
//    public String getJSONUrl(String url, List<NameValuePair> params) {
//        StringBuilder str = new StringBuilder();
//        HttpClient client = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
//
//
//            HttpResponse response = client.execute(httpPost);
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            if (statusCode == 200) { // Download OK
//                HttpEntity entity = response.getEntity();
//                InputStream content = entity.getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    str.append(line);
//                }
//            } else {
//                Log.e("Log", "Failed to download result..");
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str.toString();
//    }
//
//}
