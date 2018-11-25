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

public class MyShop extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private final String TAG = "MainActivity";
    private List<Users>  UsersList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myshop);
        Intent intent2 = getIntent();
        String userName = intent2.getStringExtra("userName");
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);

        //找到控件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //设置监听
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = getIntent();
                String userName = intent2.getStringExtra("userName");
                Intent intent = new Intent(MyShop.this, Main1.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
        
        initData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        UsersAdapter usersAdapter = new UsersAdapter(UsersList,userName);
        recyclerView.setAdapter(usersAdapter);

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent2 = getIntent();
            String userName = intent2.getStringExtra("userName");
            Intent intent = new Intent(MyShop.this, Main1.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private void initData()
    {
        String shopName;
        Intent intent2 = getIntent();
        String userName = intent2.getStringExtra("userName");
        String user;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Shop", new String[]{"shopName","userName"}, null, null, null, null,null);
        if(cursor.moveToFirst()) {
            do {
                shopName = cursor.getString(cursor.getColumnIndex("shopName"));
                user = cursor.getString(cursor.getColumnIndex("userName"));
                if (user.equals(intent2.getStringExtra("userName")))
                {
                    Users users = new Users();
                    users.setShopName(shopName);
                    UsersList.add(users);
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
    }
}
