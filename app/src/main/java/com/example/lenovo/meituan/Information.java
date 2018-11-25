package com.example.lenovo.meituan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Information extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        final TextView tv_shopName = (TextView) findViewById(R.id.tv_shopName);
        //打开店铺查看商品
        Button btn_open = (Button) findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopName = tv_shopName.getText().toString();
                Intent intent = new Intent(Information.this,ShowGoods.class);
                intent.putExtra("shopName", shopName);
                startActivity(intent);
            }
        });
    }
}
