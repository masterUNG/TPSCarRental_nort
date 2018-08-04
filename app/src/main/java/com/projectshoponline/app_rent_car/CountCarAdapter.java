package com.projectshoponline.app_rent_car;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CountCarAdapter extends RecyclerView.Adapter<CountCarAdapter.CountCarViewHolder> {

    private Context context;
    private ArrayList<String> itemStringArrayList, productNameStringArrayList, countStringArrayList;
    private LayoutInflater layoutInflater;

    public CountCarAdapter(Context context,
                           ArrayList<String> itemStringArrayList,
                           ArrayList<String> productNameStringArrayList,
                           ArrayList<String> countStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.itemStringArrayList = itemStringArrayList;
        this.productNameStringArrayList = productNameStringArrayList;
        this.countStringArrayList = countStringArrayList;
    }

    @Override
    public CountCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycler_view_count_car, parent, false);
        CountCarViewHolder countCarViewHolder = new CountCarViewHolder(view);

        return countCarViewHolder;
    }

    @Override
    public void onBindViewHolder(CountCarViewHolder holder, int position) {

        String itemString = itemStringArrayList.get(position);
        String productNameString = productNameStringArrayList.get(position);
        String countString = countStringArrayList.get(position);

        holder.itemTextView.setText(itemString);
        holder.productNameTextView.setText(productNameString);
        holder.countTextView.setText(countString);

    }

    @Override
    public int getItemCount() {
        return itemStringArrayList.size();
    }

    public class CountCarViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTextView, productNameTextView, countTextView;

        public CountCarViewHolder(View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.txtItem);
            productNameTextView = itemView.findViewById(R.id.txtProductName);
            countTextView = itemView.findViewById(R.id.txtCount);

        }
    }

}   // Main Class
