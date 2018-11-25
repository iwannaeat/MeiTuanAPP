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

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    public String shopName;
    public String userName;
    public GoodsAdapter(List<Goods> list,String shopName1,String userName1){
        shopName = shopName1;
        userName = userName1;
        this.list = list;
    }

    private List<Goods> list;

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_goodsName;
        TextView tv_price;
        View goodsView;
        Button btn_buy;

        public ViewHolder(View view) {
            super(view);

            goodsView = view;
            tv_goodsName = (TextView) view.findViewById(R.id.tv_goodsName);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            btn_buy = (Button) view.findViewById(R.id.btn_buy);
        }
    }

    public GoodsAdapter(List<Goods> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information_showgoods, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View goodsView;
                goodsView = view;
                int position = holder.getAdapterPosition();
                Goods goods = list.get(position);
                double price=goods.getPrice();
                if (userName.equals(goods.getUserName())){
                    Toast.makeText(goodsView.getContext(), "不能购买自己店铺的商品！", Toast.LENGTH_SHORT).show();
                }
                else{
                    final MyDatabaseHelper dbHelper = new MyDatabaseHelper(view.getContext(), "User.db", null, 2);
                    dbHelper.getWritableDatabase();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("goodsName", goods.getGoodsName());
                    values.put("shopName",shopName);
                    values.put("userName",userName);
                    values.put("price",price);
                    values.put("ownerName",goods.getUserName());
                    db.insert("List", null, values);
                    values.clear();
                    Toast.makeText(goodsView.getContext(), "购买完成", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.ViewHolder viewHolder, int i) {
        Goods goods = list.get(i);
        viewHolder.tv_goodsName.setText(goods.getGoodsName());
        viewHolder.tv_price.setText(String.valueOf(goods.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}