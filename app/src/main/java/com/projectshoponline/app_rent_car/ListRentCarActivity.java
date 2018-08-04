package com.projectshoponline.app_rent_car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class ListRentCarActivity extends AppCompatActivity {

    private String startString, endString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rent_car);

        getValueFromIntent();

//        Create RecyclerView
        createRecyclerView();

    }   // Main Method

    private void createRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDataRentCar);
        MyConstant myConstant = new MyConstant();

        try {

            GetValueWhere2Column getValueWhere2Column = new GetValueWhere2Column(ListRentCarActivity.this);
            getValueWhere2Column.execute(startString, endString, myConstant.getUrlGetWhereStartAnEnd());
            String jsonString = getValueWhere2Column.get();
            Log.d("4AugV1", "JSON ==> " + jsonString);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getValueFromIntent() {
        startString = getIntent().getStringExtra("Start");
        endString = getIntent().getStringExtra("End");
        Log.d("4AugV1", "Start ==> " + startString);
        Log.d("4AugV1", "End ==> " + endString);
    }

}   // Main Class
