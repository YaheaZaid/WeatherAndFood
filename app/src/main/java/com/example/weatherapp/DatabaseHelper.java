package com.example.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_name";
    private static final String TABLE_FOOD = "table_food";
    private static final String TABLE_DRINK = "table_drink";


    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);


    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String Table1 = "create table " + TABLE_FOOD + "(id INTEGER PRIMARY KEY, food TEXT)";
        String Table2 = "create table " + TABLE_DRINK + "(id INTEGER PRIMARY KEY, drink TEXT)";

        db.execSQL(Table1);
        db.execSQL(Table2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DRINK);
        onCreate(db);
    }
    public boolean insert(String drink,String food){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("food",food);
        sqLiteDatabase.insert(TABLE_FOOD,null,contentValues1);
        ContentValues contentValues2 = new ContentValues();
        contentValues2 .put("drink",drink);
        sqLiteDatabase.insert(TABLE_DRINK,null,contentValues2);
        return true;
    }
    public boolean insertFood(String food){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("food",food);
        sqLiteDatabase.insert(TABLE_FOOD,null,contentValues1);
        return true;
    }
    public boolean insertDrink(String drink){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2 .put("drink",drink);
        sqLiteDatabase.insert(TABLE_DRINK,null,contentValues2);
        return true;
    }
    public Boolean deleteItem(String item,String table,String kind){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + table + " WHERE " +kind+" = '"+item+"'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
            return true;
        else
            return false;

    }
    public ArrayList getFood(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_FOOD,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("food")));
            cursor.moveToNext();
        }
        return arrayList;

    }
    public ArrayList getDrink(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_DRINK,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("drink")));
            cursor.moveToNext();
        }
        return arrayList;

    }
}
