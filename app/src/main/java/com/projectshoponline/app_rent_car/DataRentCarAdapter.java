package com.projectshoponline.app_rent_car;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataRentCarAdapter extends RecyclerView.Adapter<DataRentCarAdapter.DataRentCarViewHolder> {

    private Context context;
    private ArrayList<String> itemStringArrayList, nameAnSurStringArrayList,
            productNameStringArrayList, startDateStringArrayList, amountStringArrayList;
    private LayoutInflater layoutInflater;

    public DataRentCarAdapter(Context context, ArrayList<String> itemStringArrayList, ArrayList<String> nameAnSurStringArrayList, ArrayList<String> productNameStringArrayList, ArrayList<String> startDateStringArrayList, ArrayList<String> amountStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.itemStringArrayList = itemStringArrayList;
        this.nameAnSurStringArrayList = nameAnSurStringArrayList;
        this.productNameStringArrayList = productNameStringArrayList;
        this.startDateStringArrayList = startDateStringArrayList;
        this.amountStringArrayList = amountStringArrayList;
    }

    @Override
    public DataRentCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycler_view_data_rent_car, parent, false);
        DataRentCarViewHolder dataRentCarViewHolder = new DataRentCarViewHolder(view);

        return dataRentCarViewHolder;
    }

    @Override
    public void onBindViewHolder(DataRentCarViewHolder holder, int position) {

        String itemString = itemStringArrayList.get(position);
        String nameAnSurString = nameAnSurStringArrayList.get(position);
        String productNameString = productNameStringArrayList.get(position);
        String stateDateString = startDateStringArrayList.get(position);
        String amountString = amountStringArrayList.get(position);

        holder.itemTextView.setText(itemString);
        holder.nameAnSurTextView.setText(nameAnSurString);
        holder.productNameTextView.setText(productNameString);
        holder.stateDateTextView.setText(stateDateString);
        holder.amountTextView.setText(amountString);

    }

    @Override
    public int getItemCount() {
        return itemStringArrayList.size();
    }

    public class DataRentCarViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTextView, nameAnSurTextView, productNameTextView,
                stateDateTextView, amountTextView;

        public DataRentCarViewHolder(View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.txtItem);
            nameAnSurTextView = itemView.findViewById(R.id.txtNameAndSur);
            productNameTextView = itemView.findViewById(R.id.txtProductName);
            stateDateTextView = itemView.findViewById(R.id.txtStart);
            amountTextView = itemView.findViewById(R.id.txtAmount);


        }
    }

}   // Main Class
