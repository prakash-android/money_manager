package com.money.manger.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.money.manger.model.MyListData;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "money.db";
    public static final int DATABASE_VERSION = 1;

    public static final String MONEY_TABLE = "cash_history";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_IMAGE = "IMAGE";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * always beware of variable dataType used in adapter, view setters, database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MONEY_TABLE +
                        "(" + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +  COLUMN_ITEM_NAME + " text," +  COLUMN_AMOUNT + " integer," +  COLUMN_DATE + " date," + COLUMN_IMAGE + " BLOB)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MONEY_TABLE);
        onCreate(db);
    }


    /**
     * @param name , amt, date, image (byteArray saved as blob in db)
     * @return boolean
     * -----------------------------------------
     * insert new row in table
     * insertOrThrow method returns value of result (-1 on failure)
     * else use insert method n validate result returned
     */
    public boolean addCashHistory (String name, int amt, String date, byte[] imageBytes) {
        boolean s;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME , name);
        contentValues.put(COLUMN_AMOUNT , amt);
        contentValues.put(COLUMN_DATE , date);
        contentValues.put(COLUMN_IMAGE , imageBytes);

        // insert row (insertOrThrow method)
        long success = db.insert(MONEY_TABLE , null, contentValues);
        s = success != -1L;

        //close db connection
        db.close();

        Log.e("addCashHistory", "" + s);
        return s;
    }


    /**
     * @param id
     * @return boolean
     * ----------------------------
     * update values to row with id (unique field)
     */
    public boolean updateCashHistory (Integer id, String name, int amt, String date) {
        boolean s;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME, name);
        contentValues.put(COLUMN_AMOUNT, amt);
        contentValues.put(COLUMN_DATE, date);

        //update row
        long success = db.update( MONEY_TABLE , contentValues, COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        s = success != -1L;

        db.close();

        Log.e("updateCashHistory", "" + s);
        return true;
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
     * getCount() is removed, ite expensive operation in db (row counting by cursor)
     */
    public ArrayList<MyListData> getAllDateCashHistory(String sDate){
        ArrayList<MyListData> cashHistoryArrayList = new ArrayList<MyListData>();

        String selectQuery = "SELECT * FROM "+ MONEY_TABLE + " WHERE "+ COLUMN_DATE + " = " + '"'+ sDate +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        int id;
        String name;
        int amt;
        String date;
        byte[] img;
        try{
            cursor =  db.rawQuery( selectQuery , null );
            if (cursor != null){
                try{
                    if (cursor.moveToNext()) {

                        while (!cursor.isAfterLast()) {
                            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                            name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
                            amt = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
                            date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                            img = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));

                            MyListData t = new MyListData(id, name, amt, date, img);

                            cashHistoryArrayList.add(t);
                            cursor.moveToNext();
                        }
                    } else {
                        //query result was empty, handle here
                        Log.e("mm", selectQuery);
                    }

                } finally {
                    //close cursor here
                    cursor.close();
                }
            }

        }catch (SQLiteException e){
            //handles all sqlite exceptions & returns empty arrayList
            Log.e("mm Exception", e.getMessage());
            return cashHistoryArrayList;

        }catch (CursorIndexOutOfBoundsException cIOB){
            //handles cursor index exceptions & returns empty arrayList
            Log.e("mm Exception", cIOB.getLocalizedMessage());
            return cashHistoryArrayList;
        }


        //close db connection
        db.close();
        //Log.e("mm", "rows returned (arrayList size) " + cashHistoryArrayList.size() );
        return cashHistoryArrayList;
    }

    /**
     * get sum of amount in date, calculate total
     * by query string
     * use cursor to get value
     *** SUM(AMT) As TOTAL - its used to get returned columnIndex
     *** SUM() - adds values in field  ( integer, text, varchar )
     * getCount removed , its expensive n no longer needed
     * ---------------------------------------------------------------
     * @param sDate
     * @return total
     */
    public int getAllDateCashHistoryTotal(String sDate){

        String selectQuery = "SELECT "+ "SUM(" + COLUMN_AMOUNT + ") AS Total " +" FROM "+ MONEY_TABLE + " WHERE "+ COLUMN_DATE + " = " + '"'+ sDate +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        int totalAmt = 0;
       // int rowCount;
        try{
            cursor =  db.rawQuery( selectQuery , null );
            if (cursor != null){
               // rowCount = cursor.getCount();
                try{
                    if (cursor.moveToFirst()) {

                        totalAmt = cursor.getInt(cursor.getColumnIndex("Total"));
                    } else {
                        //query result was empty, handle here
                      //  Log.e("mm", "rows returned " + rowCount );
                    }

                } finally {
                    //close cursor here
                    cursor.close();
                }
            }

        }catch (SQLiteException e){
            //handles all sqlite exceptions & returns empty arrayList
            Log.e("mm", e.getMessage());
            return totalAmt;
        }



        //close db connection
        db.close();
        Log.e("mm", "total value " + totalAmt );
        return totalAmt;
    }


    /**
     * @param sMonth
     * @return arrayList of cash history data in selected month
     * ----------------------------------------------------------
     * strftime('%Y-%m', column_name) - crops date to preferred format
     */
    public ArrayList<MyListData> getAllMonthCashHistory(String sMonth){
        ArrayList<MyListData> cashHistoryArrayList = new ArrayList<MyListData>();

        String selectQuery = "SELECT * FROM "+ MONEY_TABLE + " WHERE strftime( '%Y-%m', "+ COLUMN_DATE + " ) = " + '"'+ sMonth +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        int id;
        String name;
        int amt;
        String date;
        byte[] img;
        int rowCount;
        try{
            cursor =  db.rawQuery( selectQuery , null );
            if (cursor != null){
                rowCount = cursor.getCount();
                try{
                    if (cursor.moveToNext()) {

                        while (!cursor.isAfterLast()) {
                            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                            name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
                            amt = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
                            date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                            img = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));

                            MyListData t = new MyListData(id, name, amt, date, img);

                            cashHistoryArrayList.add(t);
                            cursor.moveToNext();
                        }
                    } else {
                        //query result was empty, handle here
                        Log.e("mm", "rows returned " + rowCount );
                        Log.e("mm", selectQuery);
                    }

                } finally {
                    //close cursor here
                    cursor.close();
                }
            }

        }catch (SQLiteException e){
            //handles all sqlite exceptions & returns empty arrayList
            Log.e("mm", e.getMessage());
            Log.e("mm", selectQuery);
            return cashHistoryArrayList;
        }


        //close db connection
        db.close();
        //Log.e("mm", "rows returned (arrayList size) " + cashHistoryArrayList.size() );
        return cashHistoryArrayList;
    }


    /**
     * get month total here by query string
     * use cursor to get value
     * --------------------------------------
     * @param sMonth
     * @return int (total)
     */
    public int getAllMonthCashHistoryTotal(String sMonth){
        ArrayList<MyListData> cashHistoryArrayList = new ArrayList<MyListData>();

        String selectQuery = "SELECT "+ "SUM(" + COLUMN_AMOUNT + ") AS Total " +" FROM "+  MONEY_TABLE + " WHERE strftime( '%Y-%m', "+ COLUMN_DATE + " ) = " + '"'+ sMonth +'"' + ";" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        int totalAmt = 0;
        int rowCount;
        try{
            cursor =  db.rawQuery( selectQuery , null );
            if (cursor != null){
                rowCount = cursor.getCount();
                try{
                    if (cursor.moveToFirst()) {

                         totalAmt = cursor.getInt(cursor.getColumnIndex("Total"));
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
            return totalAmt;
        }


        //close db connection
        db.close();
        Log.e("mm", "total value " + totalAmt );
        return totalAmt;
    }


    /**
     * delete particular row with id from table
     * checks result/ row returned > 0
     * returns boolean
     */
    public boolean deleteItem (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        boolean s = db.delete(MONEY_TABLE,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) }) > 0;

        Log.e("deleteItem", "" + s );

        return s;
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
