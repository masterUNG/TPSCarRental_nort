package com.projectshoponline.app_rent_car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ReportFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        DataRentCar Controller
        dataRentCarController();


    }   // Main Method

    private void dataRentCarController() {
        Button button = getView().findViewById(R.id.btnDataRentCar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentReportFragment, new DataRentCarFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarReport);
        ((ReportActivity) getActivity()).setSupportActionBar(toolbar);
        ((ReportActivity) getActivity()).getSupportActionBar().setTitle("Report");
        ((ReportActivity) getActivity()).getSupportActionBar().setSubtitle("Please Choose Report Type");
        ((ReportActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((ReportActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        return view;
    }
}
