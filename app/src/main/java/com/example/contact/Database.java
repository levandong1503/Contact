package com.example.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_danhba2";
    public static final String TABLE_NAME = "table_danhba";
    public static final int DB_VERSION = 10;
    public static final String MA = "ma";
    public static final String NAME = "name";
    public static final String PHONE = "phone";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME +"(" +
                MA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT ,"+
                PHONE + " TEXT "+
                ")";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropDb = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropDb);
        db.close();
    }

    public ArrayList<DanhBa> getAll(){
        String create = "CREATE TABLE " + TABLE_NAME +"(" +
                MA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT ,"+
                PHONE + " TEXT "+
                ")";
        Log.i("\n\n\n\n\n\n\n infor", create);

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DanhBa> danhBas = new ArrayList<>();
        String sql = "select * from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                DanhBa danhBa = new DanhBa();
                danhBa.setId(cursor.getInt(0));
                danhBa.setName(cursor.getString(1));
                danhBa.setPhone(cursor.getString(2));
                System.out.println(cursor.getString(1));
                danhBas.add(danhBa);
            }while (cursor.moveToNext());

        }

        return danhBas;
    }

    public int addDanhBa(DanhBa danhBa){

        String create = "CREATE TABLE " + TABLE_NAME +"(" +
                MA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT ,"+
                PHONE + "TEXT "+
                ")";
        System.out.println("\n\n\n\n\n\n\n infor" + create);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MA,danhBa.getId());
        contentValues.put(NAME,danhBa.getName());
        contentValues.put(PHONE,danhBa.getPhone());
        return (int) db.insert(TABLE_NAME,null,contentValues);
    }

    public int deleteDanhBa(int Id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME,MA + " = "+Id , null);
    }

    public DanhBa findById(int Id){
        SQLiteDatabase db = this.getWritableDatabase();
        DanhBa danhBa = null;
        String sql = "select * from "+MA + " = "+Id;
        Cursor cursor = db.rawQuery(sql, null);
        do {
            danhBa = new DanhBa(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }while (cursor.moveToNext());
        return danhBa;
    }

    public int updateDanhBa(DanhBa danhBa){
        SQLiteDatabase db = this.getWritableDatabase();
        /*
        String sql = "update from "+ TABLE_NAME +
                "set " + NAME + " = " + danhBa.getName() + " , " +
                PHONE + " = "+danhBa.getPhone() + " where " + MA + " = " + danhBa.getId();
         */
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,danhBa.getName());
        contentValues.put(PHONE,danhBa.getPhone());
        return db.update(TABLE_NAME,contentValues,MA+ " = "+ danhBa.getId(),null);
    }

    public ArrayList<DanhBa> findByName(String Name){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from "+TABLE_NAME
                +  " where "+ NAME + " like '%"+Name+"%'";
        ArrayList<DanhBa> dsdb = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            do{
                dsdb.add( new DanhBa(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            }while(cursor.moveToNext());

        }
        return  dsdb;
    }

}