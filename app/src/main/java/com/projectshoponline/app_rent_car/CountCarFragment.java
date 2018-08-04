package com.projectshoponline.app_rent_car;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CountCarFragment extends Fragment{

    private int yearCurrentAnInt, monthCurrenAnInt, dayCurrentAnInt;
    private String startString, endString;
    private boolean startABoolean = true, endABoolean = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //        Get CurrentTime
        getCurrentTime();

//        Create Toolbar
        createToolbar();

//        SetStart Controller
        setStartController();

//        SetEnd Controller
        setEndController();

//        Update Controller
        updateController();
    }

    private void updateController() {
        Button button = getView().findViewById(R.id.btnUpdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (startABoolean || endABoolean) {
                    Toast.makeText(getActivity(),
                            "Please Setup Start and End Date",
                            Toast.LENGTH_SHORT).show();
                } else {
                    reportData();
                }

            }
        });
    }

    private void reportData() {
        Intent intent = new Intent(getActivity(), ListCountCarActivity.class);
        intent.putExtra("Start", startString);
        intent.putExtra("End", endString);
        startActivity(intent);
    }

    private void setEndController() {
        Button button = getView().findViewById(R.id.btnSetEnd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                showEnd(year, month, day);
                                endABoolean = false;
                            }
                        },yearCurrentAnInt, monthCurrenAnInt, dayCurrentAnInt);
                datePickerDialog.show();

            }
        });

    }

    private void showEnd(int year, int month, int day) {
        TextView textView = getView().findViewById(R.id.txtShowEnd);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String endDate = dateFormat.format(calendar.getTime());
        textView.setText(endDate);
        endString = endDate;
    }

    private void setStartController() {
        Button button = getView().findViewById(R.id.btnSetStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                showStart(year, month, day);
                                startABoolean = false;
                            }
                        },yearCurrentAnInt, monthCurrenAnInt, dayCurrentAnInt);
                datePickerDialog.show();

            }
        });
    }

    private void showStart(int year, int month, int day) {
        TextView textView = getView().findViewById(R.id.txtShowStart);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String startDate = dateFormat.format(calendar.getTime());
        textView.setText(startDate);
        startString = startDate;
    }

    private void createToolbar() {

        Toolbar toolbar = getView().findViewById(R.id.toolbarCountCar);
        ((ReportActivity) getActivity()).setSupportActionBar(toolbar);
        ((ReportActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.count_car));
        ((ReportActivity) getActivity()).getSupportActionBar().setSubtitle("กรุณาเลือกวันเริ่มต้น และ วันสิ้นสุด");
        ((ReportActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((ReportActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }

    private void getCurrentTime() {

        Calendar calendar = Calendar.getInstance();
        yearCurrentAnInt = calendar.get(Calendar.YEAR);
        monthCurrenAnInt = calendar.get(Calendar.MONTH);
        dayCurrentAnInt = calendar.get(Calendar.DAY_OF_MONTH);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count_car, container, false);
        return view;
    }
}
