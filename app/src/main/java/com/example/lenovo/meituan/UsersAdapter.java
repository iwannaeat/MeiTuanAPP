package com.example.lenovo.meituan;

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


import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<Users> list;
    public String userName;
    public UsersAdapter(List<Users> list,String userName1){
        userName = userName1;
        this.list = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View shopView;
        TextView tv_shopName;
        Button btn_createGoods;
        Button btn_open;
        Button btn_delete;

        public ViewHolder(View view) {
            super(view);
            shopView = view;
            tv_shopName = (TextView) view.findViewById(R.id.tv_shopName);
            btn_createGoods = (Button) view.findViewById(R.id.btn_createGoods);
            btn_open = (Button) view.findViewById(R.id.btn_open);
            btn_delete = (Button) view.findViewById(R.id.btn_delete);


        }
    }

    public UsersAdapter(List<Users> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information_myshop, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.tv_shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Users users = list.get(position);
                Toast.makeText(view.getContext(), users.getShopName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btn_createGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_shopName;
                View shopView;
                shopView = view;
                int position = holder.getAdapterPosition();
                Users users = list.get(position);
                tv_shopName = (TextView) view.findViewById(R.id.tv_shopName);
                String shopName = users.getShopName();
                Intent intent = new Intent();
                intent.setClass(shopView .getContext(),CreateGoods .class );
                intent.putExtra("shopName", shopName);
                intent.putExtra("userName", userName);
                shopView.getContext().startActivity(intent);
            }
        });
        holder.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View shopView;
                shopView = view;
                int position = holder.getAdapterPosition();
                Users users = list.get(position);
                String shopName = users.getShopName();
                Intent intent = new Intent();
                intent.setClass(shopView .getContext(),MyGoods .class );
                intent.putExtra("shopName", shopName);
                intent.putExtra("userName", userName);
                shopView.getContext().startActivity(intent);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View shopView;
                shopView = view;
                int position = holder.getAdapterPosition();
                Users users = list.get(position);
                String shopName = users.getShopName();
                final MyDatabaseHelper dbHelper = new MyDatabaseHelper(view.getContext(), "User.db", null, 2);
                dbHelper.getWritableDatabase();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Shop", "shopName == ?", new String[] {shopName});
                db.delete("Goods", "shopName == ?", new String[] {shopName});
                db.delete("List", "shopName == ?", new String[] {shopName});
                Toast.makeText(shopView.getContext(), "删除完成", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent();
                intent2.setClass(shopView .getContext(),MyShop.class );
                intent2.putExtra("userName", userName);
                shopView.getContext().startActivity(intent2);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder viewHolder, int i) {
        Users users = list.get(i);
        viewHolder.tv_shopName.setText(users.getShopName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
