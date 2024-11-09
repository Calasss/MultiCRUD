package com.example.eva25;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;


public class DataHelper extends SQLiteOpenHelper{
    public  DataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE gremio(rut INTEGER PRIMARY KEY, nombre TEXT, felyne TEXT, arma TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS gremio");
        db.execSQL("CREATE TABLE gremio(rut INTEGER PRIMARY KEY, nombre TEXT, felyne TEXT, arma TEXT)");
    }
}
