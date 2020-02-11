package com.money.manger.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME , name);
        contentValues.put(COLUMN_AMOUNT , amt);
        contentValues.put(COLUMN_DATE , date);
        db.insert(MONEY_TABLE , null, contentValues);
        return true;
    }

    public ArrayList<MyListData> getAllCashHistory(String sDate){
        ArrayList<MyListData> cashHistoryArrayList = new ArrayList<MyListData>();

        String selectQuery = "SELECT * FROM "+ MONEY_TABLE + " WHERE "+ COLUMN_DATE + " = " + '"'+ sDate +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( selectQuery , null );
        res.moveToFirst();

        String name;
        String amt;

        while(res.isAfterLast() == false){
            name = res.getString(res.getColumnIndex(COLUMN_ITEM_NAME));
            amt = res.getString(res.getColumnIndex(COLUMN_AMOUNT));

            MyListData t = new MyListData(name, amt);

            cashHistoryArrayList.add(t);
            res.moveToNext();
        }

        Log.e("mm", String.valueOf(cashHistoryArrayList.size()) );
        Log.e("mm", selectQuery );

        return cashHistoryArrayList;
    }


}
