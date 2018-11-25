package com.example.lenovo.meituan;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private final String TAG = "MainActivity";
    String realName;
    String realPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        //跳转到注册界面
        Button register1 = (Button) findViewById(R.id.register1);
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        //设置密码为不可见
        final EditText password = (EditText) findViewById(R.id.password);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //获取用户名和密码
        final EditText name = (EditText) findViewById(R.id.name);
        //登录按钮
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                //查询数据
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("User", new String[]{"name", "password"}, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        realName = cursor.getString(cursor.getColumnIndex("name"));
                        realPassword = cursor.getString(cursor.getColumnIndex("password"));
                        if (name.getText().toString().equals(realName) && password.getText().toString().equals(realPassword)) {
                            i = 1;
                            String userName = name.getText().toString();
                            Intent intent = new Intent(MainActivity.this, Main1.class);
                            intent.putExtra("userName", name.getText().toString());
                            startActivity(intent);
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
                if (i == 0) {
                    Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            Snackbar.make(linearLayout, "确定要退出吗", Snackbar.LENGTH_SHORT)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.exit(0);
                        }
                    })
                    .show();
            return true;
        }
        return false;
    }
}