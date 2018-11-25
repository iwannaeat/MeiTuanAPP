package com.example.lenovo.meituan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateShop extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createshop);
        final EditText et_shopName = (EditText) findViewById(R.id.et_shopName);
        Button btn_createShop = (Button) findViewById(R.id.btn_createShop);
        Button btn_out = (Button) findViewById(R.id.btn_out);
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        dbHelper.getWritableDatabase();
        btn_createShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                final String userName = intent1.getStringExtra("userName");
                String shopName;
                int i = 1;
                if (et_shopName.getText().toString().equals("")){
                    Toast.makeText(CreateShop.this, "店铺名不能为空！", Toast.LENGTH_SHORT).show();
                    i =0;
                }
                else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.query("Shop", new String[]{"shopName"}, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            shopName = cursor.getString(cursor.getColumnIndex("shopName"));
                            if (et_shopName.getText().toString().equals(shopName)) {
                                i = 0;
                                Toast.makeText(CreateShop.this, "该店铺已经存在！", Toast.LENGTH_SHORT).show();
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    //添加数据
                    if (i == 1) {
                        ContentValues values = new ContentValues();
                        values.put("shopName", et_shopName.getText().toString());
                        values.put("userName", intent1.getStringExtra("userName"));
                        db.insert("Shop", null, values);
                        values.clear();
                        //界面跳转
                        Intent intent = new Intent(CreateShop.this, Main1.class);
                        intent.putExtra("userName", intent1.getStringExtra("userName"));
                        startActivity(intent);
                        Toast.makeText(CreateShop.this, "添加成功！", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                Intent intent = new Intent(CreateShop.this, Main1.class);
                intent.putExtra("userName", intent1.getStringExtra("userName"));
                startActivity(intent);
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = getIntent();
            Intent intent = new Intent(CreateShop.this, Main1.class);
            intent.putExtra("userName", intent1.getStringExtra("userName"));
            startActivity(intent);
            return true;
        }
        return false;
    }

}
