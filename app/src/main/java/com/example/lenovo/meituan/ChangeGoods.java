package com.example.lenovo.meituan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ChangeGoods extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changegoods);
        final EditText et_goodsName = (EditText) findViewById(R.id.et_goodsName);
        final EditText et_price = (EditText) findViewById(R.id.et_price);
        Button btn_createGoods = (Button) findViewById(R.id.btn_createGoods);
        Button btn_out = (Button) findViewById(R.id.btn_out);
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        dbHelper.getWritableDatabase();
        //接收数据
        Intent intent2 = getIntent();
        final String userName = intent2.getStringExtra("userName");
        final String goodsName = intent2.getStringExtra("goodsName");
        final String shopName = intent2.getStringExtra("shopName");
        btn_createGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_goodsName.getText().toString().equals("")||et_price.getText().toString().equals("")){
                    Toast.makeText(ChangeGoods.this, "商品名或价格不能为空！", Toast.LENGTH_SHORT).show();
                }
                else {
                    //修改
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("price", et_price.getText().toString());
                    values.put("goodsName", et_goodsName.getText().toString());
                    db.update("Goods", values, "goodsName = ? and shopName= ?", new String[]{goodsName, shopName});
                    //界面跳转
                    Intent intent = new Intent(ChangeGoods.this, MyGoods.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("shopName", shopName);
                    startActivity(intent);
                    Toast.makeText(ChangeGoods.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeGoods.this, MyGoods.class);
                intent.putExtra("userName", userName);
                intent.putExtra("shopName", shopName);
                startActivity(intent);
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent2 = getIntent();
            final String userName = intent2.getStringExtra("userName");
            final String goodsName = intent2.getStringExtra("goodsName");
            final String shopName = intent2.getStringExtra("shopName");
            Intent intent = new Intent(ChangeGoods.this, MyGoods.class);
            intent.putExtra("userName", userName);
            intent.putExtra("shopName", shopName);
            startActivity(intent);
            return true;
        }
        return false;
    }

}