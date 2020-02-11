package com.money.manger.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.money.manger.model.MyListData;

import java.util.ArrayList;

import javax.crypto.AEADBadTagException;

public class DbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "money.db";
    public static final int DATABASE_VERSION = 1;

    public static final String MONEY_TABLE = "cash_history";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_DATE = "DATE";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MONEY_TABLE +
                        "(" + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +  COLUMN_ITEM_NAME + " text," +  COLUMN_AMOUNT + " text," +  COLUMN_DATE + " date)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }



    public boolean addCashHistory (String name, String amt, String date) {
        boolean s;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME , name);
        contentValues.put(COLUMN_AMOUNT , amt);
        contentValues.put(COLUMN_DATE , date);

        // insert row
        long success = db.insert(MONEY_TABLE , null, contentValues);
        s = success != -1L;

        //close db connection
        db.close();

        Log.e("addCashHistory", "" + s);
        return s;
    }

    public ArrayList<MyListData> getAllCashHistory(String sDate){
        ArrayList<MyListData> cashHistoryArrayList = new ArrayList<MyListData>();

        String selectQuery = "SELECT * FROM "+ MONEY_TABLE + " WHERE "+ COLUMN_DATE + " = " + '"'+ sDate +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try{
            cursor =  db.rawQuery( selectQuery , null );
        }catch (SQLiteException e){
            Log.e("mm", e.getMessage());
            db.execSQL(selectQuery);
            return cashHistoryArrayList;
        }
        cursor.moveToFirst();

        String name;
        String amt;

        while(cursor.isAfterLast() == false){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
            amt = cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT));

            MyListData t = new MyListData(name, amt);

            cashHistoryArrayList.add(t);
            cursor.moveToNext();
        }

        db.close();
//        Log.e("mm", String.valueOf(cashHistoryArrayList.size()) );
//        Log.e("mm", selectQuery );

        return cashHistoryArrayList;
    }


}
