package com.projectshoponline.app_rent_car;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DialogActivity_1 extends Activity implements View.OnClickListener {


    TextView tv_product_id;
    TextView tv_product_name;
    TextView tv_product_price;
    TextView tv_product_img_1;
    TextView tv_stat_time;
    TextView tv_end_time;

    ImageView imgview1;


    Button b_Action_1 ;
    Button b_Cancel_1 ;

    MyHttpPoster poster;
    String New_String_product_img_1;

    Calendar myCalendar = Calendar.getInstance();
    String strMemberID = "";

    ////////////////
    NumberPicker np;

    ///////////////
    TextView tv_product_amount;

    private String driverIdString;
    private double[] pointLatDoubles = new double[2];
    private double[] pointLngDoubles = new double[2];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogactivity_1);

//        Create Spinner
        createSpinner();

//        Map Controller
        mapController();

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
            Intent newActivity = new Intent(DialogActivity_1.this, MainActivity.class);
            startActivity(newActivity);
        }

        //*** Get Member ID from Session
        strMemberID = usrHelper.getMemberID();

        tv_product_id = (TextView) findViewById(R.id.tv_product_id);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_product_price = (TextView) findViewById(R.id.tv_product_price);
        tv_product_img_1 = (TextView) findViewById(R.id.tv_product_img_1);
        tv_product_amount = (TextView) findViewById(R.id.tv_product_amount);
       // tv_stat_time = (TextView) findViewById(R.id.tv_stat_time);
       // tv_end_time = (TextView) findViewById(R.id.tv_end_time);
///////////////////////////////////////
        //Get the widgets reference from XML layout
        final TextView tv_NumberPicker = (TextView) findViewById(R.id.tv_NumberPicker);
        np = (NumberPicker) findViewById(R.id.np);

        //Set TextView text color
        //tv.setTextColor(Color.parseColor("#FF2C834F"));

        //Set TextView text color
        tv_NumberPicker.setTextColor(Color.parseColor("#ffd32b3b"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(20);
        //np.setBackgroundColor(Color.parseColor("#FF2C834F"));
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                tv_NumberPicker.setText("" + newVal);
                tv_product_amount.setText("" + newVal);
               // tv_p_amount.setText("" + newVal);
            }
        });
        //////////////////

        final Intent intent = getIntent();
        String s_ID = intent.getStringExtra("product_id");
        String s_NAME = intent.getStringExtra("product_name");
        String s_PRICE = intent.getStringExtra("product_price");
        String s_IMAGE = intent.getStringExtra("product_img_1");


       // New_String_product_img_1 = intent.getStringExtra("product_img_1");

        tv_product_id.setText("" + s_ID);
        tv_product_name.setText("" + s_NAME);
        tv_product_price.setText("" + s_PRICE);
        tv_product_img_1.setText("" + s_IMAGE);
        /////////////////////////////////////

        imgview1 = (ImageView) findViewById(R.id.image1);
        Picasso.with(DialogActivity_1.this)
                //.load(""+MyArrList.get(position).get("product_img"))
                // .load("http://thaiprojectapp.com/kie/img/a1.jpg")
                .load("" + s_IMAGE)
                // .placeholder(R.drawable.placeholder)   // optional
                //.error(R.drawable.error)      // optional
                //.resize(400,400)                        // optional
                .into(imgview1);



        b_Action_1 = (Button) findViewById(R.id.b_Action_1);
        b_Cancel_1 = (Button) findViewById(R.id.b_Cancel_1);
        b_Action_1.setOnClickListener(this);
        b_Cancel_1.setOnClickListener(this);


        // editText = (EditText) findViewById(R.id.editText);
        tv_stat_time = (EditText) findViewById(R.id.tv_stat_time);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel_1();
            }
        };
        //editText.setOnClickListener(new View.OnClickListener() {
        tv_stat_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DialogActivity_1.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        // editText = (EditText) findViewById(R.id.editText);
//        tv_end_time = (EditText) findViewById(R.id.tv_end_time);
//        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel_2();
//            }
//        };
//        //editText.setOnClickListener(new View.OnClickListener() {
//        tv_end_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(DialogActivity_1.this, date2,
//                        myCalendar.get(Calendar.YEAR),
//                        myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

    }   // Main Method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("3AugV1", "requestCode ==> " + requestCode);
        if (requestCode == 500) {

            pointLatDoubles = data.getDoubleArrayExtra("Lat");
            pointLngDoubles = data.getDoubleArrayExtra("Lng");

            Log.d("3AugV1", "latStat ==> " + pointLatDoubles[0]);

            TextView startTextView = findViewById(R.id.txtStart);
            TextView endTextView = findViewById(R.id.txtEnd);

            startTextView.setText(mySetUpText(pointLatDoubles[0], pointLngDoubles[0]));
            endTextView.setText(mySetUpText(pointLatDoubles[1], pointLngDoubles[1]));

        }

    }

    private String mySetUpText(double pointLatDouble, double pointLngDouble) {

        String latString = String.format("%.3f", pointLatDouble);
        String lngString = String.format("%.3f", pointLngDouble);
        String result = "(" + latString + ", " + lngString + ")";

        return result;
    }

    private void mapController() {
        Button button = findViewById(R.id.btnSetMap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DialogActivity_1.this, MapsActivity.class);
                startActivityForResult(intent, 500);
            }
        });
    }

    private void createSpinner() {
        Spinner spinner = findViewById(R.id.driverSpinner);
        String urlJSON = "http://projectshoponline.com/rentcar/getAllDriver.php";

        try {

            ReadAllDataFromServer readAllDataFromServer = new ReadAllDataFromServer(DialogActivity_1.this);
            readAllDataFromServer.execute(urlJSON);
            String jsonString = readAllDataFromServer.get();
            Log.d("31JulyV1", "JSON ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            final String[] idStrings = new String[jsonArray.length()];
            String[] strings = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idStrings[i] = jsonObject.getString("id");
                strings[i] = jsonObject.getString("Name") + " " + jsonObject.getString("Surname");
            }

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(DialogActivity_1.this,
                    android.R.layout.simple_list_item_1, strings);
            spinner.setAdapter(stringArrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    driverIdString = idStrings[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    driverIdString = idStrings[0];
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }




    }


    private void updateLabel_1() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tv_stat_time.setText(sdf.format(myCalendar.getTime()));
    }
//    private void updateLabel_2() {
//        String myFormat = "MM/dd/yy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        tv_end_time.setText(sdf.format(myCalendar.getTime()));
//    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.b_Action_1:
                // it was the first button


                String str_tv_stat_time = tv_stat_time.getText().toString().trim();
               // String str_tv_end_time = tv_end_time.getText().toString().trim();



                //  if(str_editText_L.isEmpty() || str_editText_L.length() == 0 || str_editText_L.equals("") || str_editText_L == null)
                if (str_tv_stat_time.isEmpty() || str_tv_stat_time.length() == 0 || str_tv_stat_time.equals("") || str_tv_stat_time == null

                      //  || str_tv_end_time.isEmpty() || str_tv_end_time.length() == 0 || str_tv_end_time.equals("") || str_tv_end_time == null




                        )

                {
                    //EditText is empty
                    Toast.makeText(getApplicationContext(), "ใส่ข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                } else {
                    //EditText is not empty

                    // Toast.makeText(getApplicationContext(), "is not empty", Toast.LENGTH_SHORT).show();

                    poster = new MyHttpPoster("http://projectshoponline.com/rentcar/app_add_order.php");

                    ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();


                    //data.add(new BasicNameValuePair("dataname1", strMemberID));
                    //    data.add(new BasicNameValuePair("dataname1", New_String_group_id));
                    //  data.add(new BasicNameValuePair("dataname2", New_String_group_name));

                    data.add(new BasicNameValuePair("dataname1", tv_product_id.getText().toString().trim()));
                    data.add(new BasicNameValuePair("dataname2", tv_product_name.getText().toString().trim()));

                    data.add(new BasicNameValuePair("dataname3", tv_product_price.getText().toString().trim()));
                    data.add(new BasicNameValuePair("dataname4", tv_product_img_1.getText().toString().trim()));
                    data.add(new BasicNameValuePair("amount", tv_product_amount.getText().toString().trim()));
                    data.add(new BasicNameValuePair("dataname5", tv_stat_time.getText().toString().trim()));
                   // data.add(new BasicNameValuePair("dataname6", tv_end_time.getText().toString().trim()));
                    // data.add(new BasicNameValuePair("dataname9", tv_price.getText().toString()));
                    //data.add(new BasicNameValuePair("dataname6", textView_Product_price_total.getText().toString()));

                    data.add(new BasicNameValuePair("dataname7", strMemberID));
                    data.add(new BasicNameValuePair("dataname8", driverIdString));
                    data.add(new BasicNameValuePair("dataname9", Double.toString(pointLatDoubles[0])));
                    data.add(new BasicNameValuePair("dataname10", Double.toString(pointLngDoubles[0])));
                    data.add(new BasicNameValuePair("dataname11", Double.toString(pointLatDoubles[1])));
                    data.add(new BasicNameValuePair("dataname12", Double.toString(pointLngDoubles[1])));
                    poster.doPost(data, new Handler() {
                        public void handleMessage(android.os.Message msg) {
                            switch (msg.what) {
                                case MyHttpPoster.HTTP_POST_OK:

                                    String resultValue = (String) msg.obj;
                                    //result.setText(resultValue);
                                    finish();
                                    // Goto Activity2
                                    // Intent newActivity = new Intent(Page_Order.this,Show_All.class);
                                    //  startActivity(newActivity);


                            }
                        }


                    });

                    showToastMessage("Ok");
                    this.finish();
                }


                break;
            case R.id.b_Cancel_1:
                // it was the second button
                showToastMessage("Cancel");
                this.finish();
//                break;
        }
    }
    void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }
}
