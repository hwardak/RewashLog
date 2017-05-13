package hwardak.rewashlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HWardak on 2017-05-13.
 */

public class RewashDataAccess {

    private static final String LOGTAG = "DATABASE R:";
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    public RewashDataAccess(Context context) {
        dbHelper = new RewashLogDBOpenHelper(context);
    }

    public void open() {
        Log.d(LOGTAG, "Database opened.");
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        Log.d(LOGTAG, "Database closed.");
        dbHelper.close();
    }

    public void addRewashToTable
            (String employeeName, String time, String date, String washType, String reason){
    this.open();
        ContentValues values = new ContentValues();
        values.put(RewashLogDBOpenHelper.COLUMN_NAME,employeeName);
        values.put(RewashLogDBOpenHelper.COLUMN_TIME,time);
        values.put(RewashLogDBOpenHelper.COLUMN_DATE,date);
        values.put(RewashLogDBOpenHelper.COLUMN_WASH_PACKAGE,washType);
        values.put(RewashLogDBOpenHelper.COLUMN_REASON,reason);
        database.insert(RewashLogDBOpenHelper.TABLE_REWASHES, null, values);

        Cursor cursor = database.rawQuery("Select * from rewashes;", null);

        Log.d(LOGTAG, "Rewash row count: " +  cursor.getCount());
        this.close();

    }


}
