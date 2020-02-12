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

        // insert row (insertOrThrow method)
        long success = db.insert(MONEY_TABLE , null, contentValues);
        s = success != -1L;

        //close db connection
        db.close();

        Log.e("addCashHistory", "" + s);
        return s;
    }


    /**
     * @param sDate
     * @return array list of cash history data
     * -----------------------------------------------------------------------
     * moveToFirst() - moves cursor to first row
     * moveTONext() - moves cursor to next row
     * isAfterLast() - @boolean checks if cursor is at last row in result
     * exceptions handled - invalid query, empty result returned, other sqlite basic exceptions
     * closing cursor and db connection at last - to avoid other errors
     */
    public ArrayList<MyListData> getAllCashHistory(String sDate){
        ArrayList<MyListData> cashHistoryArrayList = new ArrayList<MyListData>();

        String selectQuery = "SELECT * FROM "+ MONEY_TABLE + " WHERE "+ COLUMN_DATE + " = " + '"'+ sDate +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        String name;
        String amt;
        int rowCount;
        try{
            cursor =  db.rawQuery( selectQuery , null );
            if (cursor != null){
                    rowCount = cursor.getCount();
                try{
                    if (cursor.moveToNext()) {

                        while (!cursor.isAfterLast()) {
                            name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
                            amt = cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT));

                            MyListData t = new MyListData(name, amt);

                            cashHistoryArrayList.add(t);
                            cursor.moveToNext();
                        }
                    } else {
                        //query result was empty, handle here
                        Log.e("mm", "rows returned " + rowCount );
                    }

                } finally {
                    //close cursor here
                    cursor.close();
                }
            }

        }catch (SQLiteException e){
            //handles all sqlite exceptions & returns empty arrayList
            Log.e("mm", e.getMessage());
            return cashHistoryArrayList;
        }


        //close db connection
        db.close();
        Log.e("mm", "rows returned (arrayList size) " + cashHistoryArrayList.size() );
        return cashHistoryArrayList;
    }


}




/*
    public ArrayList<MyListData> getAllCashHistory(String sDate){
        ArrayList<MyListData> cashHistoryArrayList = new ArrayList<MyListData>();

        String selectQuery = "SELECT  FROM "+ MONEY_TABLE + " WHERE "+ COLUMN_DATE + " = " + '"'+ sDate +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();
        //exception - handle invalid queries
        Cursor cursor;
        try{
            cursor =  db.rawQuery( selectQuery , null );
        }catch (SQLiteException e){
            Log.e("mm", e.getMessage());
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

        return cashHistoryArrayList;
    }
*/
