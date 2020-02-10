package com.money.manger.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static android.content.ContentValues.TAG;


public class DatabaseHandler {

       private static final String TAG = "ExpensesDatabase";

    //The columns we'll include in the EXPENSES table
    public static final String COL_NAME = "ITEMNAME";
    public static final String COL_AMOUNT = "AMOUNT";

    public static final String DATABASE_NAME = "EXPENSES";
    public static final String FTS_VIRTUAL_TABLE = "FTS";
    public static final int DATABASE_VERSION = 1;

    public final DatabaseOpenHelper databaseOpenHelper;

    public DatabaseHandler(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }


    public static class DatabaseOpenHelper extends SQLiteOpenHelper {

        public Context helperContext;
        public SQLiteDatabase mDatabase;

        public static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        COL_NAME + ", " +
                        COL_AMOUNT + ")";

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(FTS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }

        public long addRow(String word, String definition) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_NAME, word);
            initialValues.put(COL_AMOUNT, definition);

            return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
        }

        public long getRowCount() {
            SQLiteDatabase db = this.getReadableDatabase();
            long count = DatabaseUtils.queryNumEntries(db, FTS_VIRTUAL_TABLE);
            db.close();
            return count;
        }


    }

    public void action(){

    }

}
