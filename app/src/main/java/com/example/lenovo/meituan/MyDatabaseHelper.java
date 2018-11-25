package com.example.lenovo.meituan;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "password text)";
    private static final String CREATE_SHOP = "create table Shop ("
            + "id integer primary key autoincrement, "
            + "shopName text,"
            + "userName text)";
    private static final String CREATE_Goods = "create table Goods ("
            + "id integer primary key autoincrement, "
            + "goodsName text, "
            + "shopName text,"
            + "userName text,"
            + "price real)";
    private static final String CREATE_LIST = "create table List ("
            + "id integer primary key autoincrement, "
            + "goodsName text, "
            + "shopName text,"
            + "userName text,"
            + "price real)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_SHOP);
        db.execSQL(CREATE_LIST);
        db.execSQL(CREATE_Goods);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Shop");
        db.execSQL("drop table if exists List");
        db.execSQL("drop table if exists Goods");
        onCreate(db);
    }
}
