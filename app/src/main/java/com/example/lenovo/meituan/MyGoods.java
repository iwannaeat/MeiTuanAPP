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

import java.util.ArrayList;
import java.util.List;

public class MyGoods extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private final String TAG = "MainActivity";
    private List<UsersGoods>  UsersGoodsList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygoods);
        Intent intent = getIntent();
        String shopName = intent.getStringExtra("shopName");
        String userName = intent.getStringExtra("userName");
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //设置监听
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String userName = intent.getStringExtra("userName");
                Intent intent2 = new Intent(MyGoods.this, MyShop.class);
                intent2.putExtra("userName", userName);
                startActivity(intent2);
            }
        });
        //滚动界面
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        UsersGoodsAdapter usersGoodsAdapter = new UsersGoodsAdapter(UsersGoodsList,shopName,userName);
        recyclerView.setAdapter(usersGoodsAdapter);

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            String userName = intent.getStringExtra("userName");
            Intent intent2 = new Intent(MyGoods.this, MyShop.class);
            intent2.putExtra("userName", userName);
            startActivity(intent2);
            return true;
        }
        return false;
    }
    private void initData()
    {
        String shopName;
        Intent intent = getIntent();
        String userName = intent.getStringExtra("shopName");
        String goodsName;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Goods", new String[]{"shopName","goodsName","price"}, null, null, null, null,null);
        if(cursor.moveToFirst()) {
            do {
                shopName = cursor.getString(cursor.getColumnIndex("shopName"));
                goodsName = cursor.getString(cursor.getColumnIndex("goodsName"));
                double price =cursor.getDouble(cursor.getColumnIndex("price"));
                if (shopName.equals(intent.getStringExtra("shopName")))
                {
                    UsersGoods usersGoods = new UsersGoods();
                    usersGoods.setGoodsName(goodsName);
                    usersGoods.setPrice(price);
                    UsersGoodsList.add(usersGoods);
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
    }
}