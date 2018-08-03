package com.projectshoponline.app_rent_car;

import android.app.DatePickerDialog;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataRentCarFragment extends Fragment{

    private int yearCurrentAnInt, monthCurrenAnInt, dayCurrentAnInt;
    private int yearStartAnInt, monthStartAnInt, dayStartAnInt;
    private int yearEndAnInt, monthEndAnInt, dayEndAnInt;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get CurrentTime
        getCurrentTime();

//        Create Toolbar
        createToolbar();

//        SetStart Controller
        setStartController();

    }   // Main Method

    private void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        yearCurrentAnInt = calendar.get(Calendar.YEAR);
        monthCurrenAnInt = calendar.get(Calendar.MONTH);
        dayCurrentAnInt = calendar.get(Calendar.DAY_OF_MONTH);
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
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarDataRentCar);
        ((ReportActivity) getActivity()).setSupportActionBar(toolbar);
        ((ReportActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.data_rent_car));
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_rent_car, container, false);
        return view;
    }
}
