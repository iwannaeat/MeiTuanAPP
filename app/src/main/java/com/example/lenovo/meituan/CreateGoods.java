package com.example.lenovo.meituan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateGoods extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategoods);
        final EditText et_goodsName = (EditText) findViewById(R.id.et_goodsName);
        final EditText et_price = (EditText) findViewById(R.id.et_price);
        Button btn_createGoods = (Button) findViewById(R.id.btn_createGoods);
        Button btn_out = (Button) findViewById(R.id.btn_out);
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        dbHelper.getWritableDatabase();
        btn_createGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_goodsName.getText().toString().equals("")||et_price.getText().toString().equals("")){
                    Toast.makeText(CreateGoods.this, "商品名或价格不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = getIntent();
                    //添加数据
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("goodsName", et_goodsName.getText().toString());
                    values.put("shopName", intent.getStringExtra("shopName"));
                    values.put("userName", intent.getStringExtra("userName"));
                    values.put("price", et_price.getText().toString());
                    db.insert("Goods", null, values);
                    values.clear();
                    //界面跳转
                    Intent intent2 = new Intent( CreateGoods.this, MyShop.class);
                    intent2.putExtra("userName", intent.getStringExtra("userName"));
                    startActivity(intent2);
                    Toast.makeText(CreateGoods.this, "添加成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Intent intent2 = new Intent(CreateGoods.this, MyShop.class);
                intent2.putExtra("userName", intent.getStringExtra("userName"));
                startActivity(intent2);
            }
        });
    }
}