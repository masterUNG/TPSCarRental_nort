package com.projectshoponline.app_rent_car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentReportFragment, new ReportFragment())
                    .commit();
        }

    }
}
