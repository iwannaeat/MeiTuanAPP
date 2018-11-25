package com.example.lenovo.meituan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowGoods extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private final String TAG = "MainActivity";
    private List<Goods>  GoodsList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showgoods);
        Intent intent = getIntent();
        String shopName = intent.getStringExtra("shopName");
        String userName = intent.getStringExtra("userName");
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        //找到控件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //设置监听
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = getIntent();
                String userName = intent2.getStringExtra("userName");
                Intent intent = new Intent(ShowGoods.this, Main1.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
        //滚动界面
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        GoodsAdapter goodsAdapter = new GoodsAdapter(GoodsList,shopName,userName);
        recyclerView.setAdapter(goodsAdapter);

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent2 = getIntent();
            String userName = intent2.getStringExtra("userName");
            Intent intent = new Intent(ShowGoods.this, Main1.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            return true;
        }
        return false;
    }
    private void initData()
    {
        Intent intent = getIntent();
        String shopName;
        String goodsName;
        String userName;
        double price;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Goods", new String[]{"goodsName","shopName","price","userName"}, null, null, null, null,null);
        if(cursor.moveToFirst()) {
            do {
                shopName = cursor.getString(cursor.getColumnIndex("shopName"));
                goodsName = cursor.getString(cursor.getColumnIndex("goodsName"));
                userName = cursor.getString(cursor.getColumnIndex("userName"));
                price =cursor.getDouble(cursor.getColumnIndex("price"));
                if (intent.getStringExtra("shopName").equals(shopName)){
                    Goods goods = new Goods();
                    goods.setGoodsName(goodsName);
                    goods.setPrice(price);
                    goods.setUserName(userName);
                    GoodsList.add(goods);
                }

            } while(cursor.moveToNext());
        }
        cursor.close();
    }
}