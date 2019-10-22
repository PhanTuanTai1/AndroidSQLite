package com.example.student.phantuantai_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBContext extends SQLiteOpenHelper {
    public DBContext(Context context) {
        super(context, "QLNhanVien.sqlite", null, 1);
    }

    public void createTable(SQLiteDatabase db){
        String sql = "Create Table IF NOT EXISTS NhanVien(maNV int,tenNV nvarchar(50), PRIMARY KEY (maNV))";
        db.execSQL(sql);
    }

    public long insert(NhanVien nv){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("maNV",nv.getMaNV());
        content.put("tenNV",nv.getTenNV());
        return db.insert("NhanVien",null,content);
        //db.close();
    }
    public  boolean delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("NhanVien","maNV" + "=" + id,null) > 0;
    }
    public long update(NhanVien nv){
        SQLiteDatabase db = getWritableDatabase();
        //int id = nv.getMaNV();
        ContentValues content = new ContentValues();
        content.put("maNV",nv.getMaNV());
        content.put("tenNV",nv.getTenNV());
        return db.update("NhanVien",content,"maNV" + "=" + nv.getMaNV(),null);
    }
    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
