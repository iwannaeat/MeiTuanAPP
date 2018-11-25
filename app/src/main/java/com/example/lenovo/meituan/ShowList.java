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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowList extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private final String TAG = "MainActivity";
    private List<Buy>  BuyList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlist);
        Intent intent3 = getIntent();
        String userName = intent3.getStringExtra("userName");
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        //找到控件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //设置监听
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = getIntent();
                String userName = intent3.getStringExtra("userName");
                Intent intent = new Intent(ShowList.this, Main1.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
        //滚动界面
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        BuyAdapter buyAdapter = new BuyAdapter(BuyList);
        recyclerView.setAdapter(buyAdapter);

    }
    private void initData()
    {
        Intent intent3 = getIntent();
        String shopName;
        String goodsName;
        String userName;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("List", new String[]{"goodsName","userName","price","shopName"}, null, null, null, null,null);
        if(cursor.moveToFirst()) {
            do {
                shopName = cursor.getString(cursor.getColumnIndex("shopName"));
                goodsName = cursor.getString(cursor.getColumnIndex("goodsName"));
                userName = cursor.getString(cursor.getColumnIndex("userName"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                if (intent3.getStringExtra("userName").equals(userName)){
                    Buy buy = new Buy();
                    buy.setGoodsName(goodsName);
                    buy.setShopName(shopName);
                    buy.setPrice(price);
                    BuyList.add(buy);
                }

            } while(cursor.moveToNext());
        }
        cursor.close();
    }
}