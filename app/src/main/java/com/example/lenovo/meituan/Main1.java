package com.example.lenovo.meituan;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.PasswordTransformationMethod;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import android.view.KeyEvent;
import android.app.Activity;


public class Main1 extends AppCompatActivity {

    private List<Shops>  ShopsList =new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        //接收数据
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        //Toolbar
        Toolbar toolbarMain1 = (Toolbar) findViewById(R.id.toolbarMain1);
        //导航
        setSupportActionBar(toolbarMain1);
        ActionBar actionBar = getSupportActionBar();
        //滚动界面
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ShopsAdapter shopsAdapter = new ShopsAdapter(ShopsList,userName);
        recyclerView.setAdapter(shopsAdapter);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            Snackbar.make(linearLayout,"确定要退出该账号吗",Snackbar.LENGTH_SHORT)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent4 = new Intent(Main1.this, MainActivity.class);
                            startActivity(intent4);
                        }
                    })
                    .show();
            return true;
        }
        return false;
    }


        private void initData()
    {
        String shopName;
        String userName;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Shop", new String[]{"shopName","userName"}, null, null, null, null,null);
        if(cursor.moveToFirst()) {
            do {
                shopName = cursor.getString(cursor.getColumnIndex("shopName"));
                userName = cursor.getString(cursor.getColumnIndex("userName"));
                Shops shops = new Shops();
                shops.setShopName(shopName);
                shops.setUserName(userName);
                ShopsList.add(shops);
            } while(cursor.moveToNext());
        }
        cursor.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu) ;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        switch (item.getItemId()) {
            case R.id.shop:
                Intent intent2 = new Intent(Main1.this, MyShop.class);
                intent2.putExtra("userName", userName);
                startActivity(intent2);
                return true;
            case R.id.addShop:
                Intent intent1 = new Intent(Main1.this, CreateShop.class);
                intent1.putExtra("userName", userName);
                startActivity(intent1);
                return true;
            case R.id.list:
                Intent intent3 = new Intent(Main1.this, ShowList.class);
                intent3.putExtra("userName", userName);
                startActivity(intent3);
                return true;
            case R.id.out:
                Intent intent4 = new Intent(Main1.this, MainActivity.class);
                startActivity(intent4);
                return true;
            case R.id.delete:
                //第一个参数需要传入一个view（当前布局中的任意一个），snackbar会使用这个view自动查找最外层的布局，用于展示snackbar。
                Snackbar.make(linearLayout,"确定要注销吗",Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.delete("Goods", "userName == ?", new String[] {userName});
                                db.delete("Shop", "userName == ?", new String[] {userName});
                                db.delete("List", "ownerName == ?", new String[] {userName});
                                db.delete("User", "name == ?", new String[] {userName});
                                Intent intent5 = new Intent(Main1.this, MainActivity.class);
                                startActivity(intent5);
                                Toast.makeText(Main1.this, "删除完成", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                return true;
        }
        return true;
    }
}