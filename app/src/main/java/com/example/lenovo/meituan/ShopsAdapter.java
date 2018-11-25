package com.example.lenovo.meituan;

import android.content.Intent;
import android.content.Context;
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

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {

    public String userName;
    private List<Shops> list;
    public ShopsAdapter(List<Shops> list,String userName1){
        userName = userName1;
        this.list = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View shopView;

        TextView tv_shopName;
        TextView tv_userName;
        Button btn_open;
        Context context ;


        public ViewHolder(View view) {
            super(view);
            shopView =view;

            tv_shopName = (TextView) view.findViewById(R.id.tv_shopName);
            tv_userName = (TextView) view.findViewById(R.id.tv_userName);
            btn_open = (Button) view.findViewById(R.id.btn_open);
        }
    }

    public ShopsAdapter(List<Shops> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ShopsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder .context=viewGroup.getContext() ;
        holder.tv_shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Shops shops = list.get(position);
                Toast.makeText(view.getContext(), shops.getShopName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View shopView =view;
                TextView tv_shopName = (TextView) view.findViewById(R.id.tv_shopName);
                int position = holder.getAdapterPosition();
                Shops shops = list.get(position);
                String shopName = shops.getShopName();
                Intent intent = new Intent();
                intent.setClass(shopView .getContext(),ShowGoods .class );
                intent.putExtra("shopName", shopName);
                intent.putExtra("userName", userName);
                shopView.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsAdapter.ViewHolder viewHolder, int i) {
        Shops shops = list.get(i);
        viewHolder.tv_shopName.setText(shops.getShopName());
        viewHolder.tv_userName.setText(shops.getUserName());
    }

    @Override
    public int getItemCount() {
        if(null==list) return 0;
        else return list.size();
    }
}