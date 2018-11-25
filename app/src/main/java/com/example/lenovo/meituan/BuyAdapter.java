package com.example.lenovo.meituan;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class BuyAdapter extends RecyclerView.Adapter<BuyAdapter.ViewHolder> {

    private List<Buy> list;

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_goodsName;
        TextView tv_price;
        TextView tv_shopName;
        View buyView;

        public ViewHolder(View view) {
            super(view);
            final MyDatabaseHelper dbHelper = new MyDatabaseHelper(view.getContext(), "User.db", null, 2);
            dbHelper.getWritableDatabase();
            buyView = view;
            tv_goodsName = (TextView) view.findViewById(R.id.tv_goodsName);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_shopName = (TextView) view.findViewById(R.id.tv_shopName);
        }
    }

    public BuyAdapter(List<Buy> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BuyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information_showbuy, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BuyAdapter.ViewHolder viewHolder, int i) {
        Buy buy = list.get(i);
        viewHolder.tv_goodsName.setText(buy.getGoodsName());
        viewHolder.tv_shopName.setText(buy.getShopName());
        viewHolder.tv_price.setText(String.valueOf(buy.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}