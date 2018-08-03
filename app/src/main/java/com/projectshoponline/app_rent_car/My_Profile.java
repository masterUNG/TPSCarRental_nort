package com.projectshoponline.app_rent_car;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class My_Profile extends Activity {

    String strMemberID = "";

    String New_String_id;
    String New_String_name;
    String New_String_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        //รับค่าส่งมาจากจากหน้า page_showdata2 เพื่อมาแสดง
//        tvView1 = (TextView) findViewById(R.id.tvView1);
//
//
//        final Intent intent = getIntent();
//        String sProductID = intent.getStringExtra("ProductID");
//
//
//        tvView1.setText("" + sProductID);

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
            Intent newActivity = new Intent(My_Profile.this, MainActivity.class);
            startActivity(newActivity);
        }

        //*** Get Member ID from Session
        strMemberID = usrHelper.getMemberID();

        //*** Show User Info
        show_User_Profile();
}
    public void show_User_Profile()
    {

        final TextView tMemberID = (TextView)findViewById(R.id.txtMemberID);
        final TextView tUsername = (TextView)findViewById(R.id.txtUsername);
        final TextView tEmail = (TextView)findViewById(R.id.txtEmail);
        final TextView tPicture = (TextView)findViewById(R.id.txtPicture);


        //String url = "http://projectappnew.com/web2/app/get_MemberID.php";
        String url = "http://projectshoponline.com/rentcar/app_my_profile.php?user_id="+ strMemberID;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sMemberID", strMemberID));


        String resultServer  = MyHttpLogin.getHttpPost(url,params);

        String strMemberID = "";
        String strUsername = "";
        String strEmail = "";
        String strPicture = "";


        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strMemberID = c.getString("user_id");
            strUsername = c.getString("username");
            strEmail = c.getString("email");
            strPicture = c.getString("user_img");


            // ?????????????????????????????????//
            // NEW STRING ###################################//


          //  New_String_id = c.getString("MemberID");
           // New_String_name = c.getString("Username");



            // NEW STRING ###################################//
            // ?????????????????????????????????//


            ImageView Picture = (ImageView)findViewById(R.id.picture);

            //  rounded  วงกลม
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.image1);
//            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
//            roundedBitmapDrawable.setCircular(true);
//            imgview1.setImageDrawable(roundedBitmapDrawable);

            //mChart2.setImageBitmap(new DownloadImagesTask().execute(graph_URL_2));
            new DownloadImageTask(Picture).execute(strPicture);


            if(!strMemberID.equals(""))
            {
                tMemberID.setText(strMemberID);
                tUsername.setText(strUsername);
                tEmail.setText(strEmail);
                tPicture.setText(strPicture);

            }
            else
            {
                tMemberID.setText("-");
                tUsername.setText("-");
                tEmail.setText("-");
                tPicture.setText("-");

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
