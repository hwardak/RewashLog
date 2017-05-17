package hwardak.rewashlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

        //These two lines were put in to see if the table is growing by each row.
        // Delete later.
        Cursor cursor = database.rawQuery("Select * from rewashes;", null);
        Log.d(LOGTAG, "Rewash row count: " +  cursor.getCount());


        this.close();

    }

    public ArrayList<String> getRewashList() {
        this.open();
        ArrayList<String> rewashList = new ArrayList<>();
        String rewashRow = new String();

        Cursor cursor = database.rawQuery("Select * from rewashes;", null);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                rewashRow = "";

//                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_REWASH_ID));
//                rewashRow += " | ";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_NAME));
                rewashRow += " - ";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_TIME));
                rewashRow += " - ";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_DATE));

                rewashRow += " \n";
                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_WASH_PACKAGE));
                rewashRow += " : ";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_REASON));

                rewashList.add(rewashRow);

                this.close();
            }

        }
            return rewashList;
    }
}
