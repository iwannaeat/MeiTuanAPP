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

public class UsersGoodsAdapter extends RecyclerView.Adapter<UsersGoodsAdapter.ViewHolder> {

    public String shopName;
    public String userName;
    public UsersGoodsAdapter(List<UsersGoods> list,String shopName1,String userName1){
        shopName = shopName1;
        userName = userName1;
        this.list = list;
    }

    private List<UsersGoods> list;

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_goodsName;
        TextView tv_price;
        View usersGoodsView;
        Button btn_change;
        Button btn_delete;

        public ViewHolder(View view) {
            super(view);

            usersGoodsView = view;
            tv_goodsName = (TextView) view.findViewById(R.id.tv_goodsName);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            btn_change = (Button) view.findViewById(R.id.btn_change);
            btn_delete = (Button) view.findViewById(R.id.btn_delete);
        }
    }

    public UsersGoodsAdapter(List<UsersGoods> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UsersGoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information_mygoods, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View usersGoodsView;
                usersGoodsView = view;
                int position = holder.getAdapterPosition();
                UsersGoods usersGoods = list.get(position);
                final MyDatabaseHelper dbHelper = new MyDatabaseHelper(view.getContext(), "User.db", null, 2);
                dbHelper.getWritableDatabase();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Goods", "shopName == ?", new String[] {shopName});
                db.delete("List", "shopName == ?", new String[] {shopName});
                Toast.makeText(usersGoodsView.getContext(), "删除完成", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(usersGoodsView .getContext(),MyGoods.class );
                intent.putExtra("userName", userName);
                intent.putExtra("shopName", shopName);
                usersGoodsView.getContext().startActivity(intent);
            }
        });
        holder.btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View usersGoodsView;
                usersGoodsView = view;
                int position = holder.getAdapterPosition();
                UsersGoods usersGoods = list.get(position);
                Intent intent2 = new Intent();
                intent2.setClass(usersGoodsView .getContext(),ChangeGoods.class );
                intent2.putExtra("userName", userName);
                intent2.putExtra("shopName", shopName);
                intent2.putExtra("goodsName",usersGoods.getGoodsName());
                usersGoodsView.getContext().startActivity(intent2);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersGoodsAdapter.ViewHolder viewHolder, int i) {
        UsersGoods usersGoods = list.get(i);
        viewHolder.tv_goodsName.setText(usersGoods.getGoodsName());
        viewHolder.tv_price.setText(String.valueOf(usersGoods.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
