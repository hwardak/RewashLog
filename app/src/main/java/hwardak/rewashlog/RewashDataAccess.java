package hwardak.rewashlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HWardak on 2017-05-13.
 */

public class RewashDataAccess extends AsyncTask {

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
            (String employeeName, String time, String date, int year, int month, int dayOfMonth, String washType, String reason){
        this.open();
        ContentValues values = new ContentValues();
        values.put(RewashLogDBOpenHelper.COLUMN_NAME,employeeName);
        values.put(RewashLogDBOpenHelper.COLUMN_TIME,time);
        values.put(RewashLogDBOpenHelper.COLUMN_DATE,date);
        values.put(RewashLogDBOpenHelper.COLUMN_YEAR, year);
        values.put(RewashLogDBOpenHelper.COLUMN_MONTH, month);
        values.put(RewashLogDBOpenHelper.COLUMN_DAY_OF_MONTH, dayOfMonth);
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


        Cursor cursor = database.rawQuery("Select * from rewashes order by rewashID desc;", null);
        ArrayList<String> rewashList = populateRewashList(cursor);

        this.close();
        return rewashList;
    }

    public ArrayList<String> getRewashListByMonthAndYear(int month, int year) {
        this.open();
        String rewashRow = new String();

        Cursor cursor = database.rawQuery("Select * from rewashes where monthNumber = '" + month + "' AND year = '" + year + "' order by rewashID desc;"
                , null);

        ArrayList<String> rewashList = populateRewashList(cursor);
        this.close();
        return rewashList;
    }

    public ArrayList<String> getRewashListByYear(int year) {
        this.open();
        String rewashRow;

        Cursor cursor = database.rawQuery("Select * from rewashes where year = " + year + " order by rewashID desc;", null);

        ArrayList<String> rewashList = populateRewashList(cursor);


        this.close();
        return rewashList;
    }

    public ArrayList<String> getRewashListByMonth(int month) {
        this.open();
        String rewashRow;

        Cursor cursor = database.rawQuery("Select * from rewashes where monthNumber = " + month + " order by rewashID desc;", null);

        ArrayList<String> rewashList = populateRewashList(cursor);

        this.close();
        return rewashList;
    }

    public ArrayList<String> getYearList(){
        this.open();
        ArrayList<String> yearList = new ArrayList<>();
        String yearRow = "";
        Cursor cursor = database.rawQuery("Select DISTINCT year from rewashes order by year;", null);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                yearRow = "" + cursor.getInt(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_YEAR));
                yearList.add(yearRow);
            }
        }
        this.close();
        return yearList;
    }

    private ArrayList<String> populateRewashList(Cursor cursor){
        String rewashRow;
        ArrayList<String> rewashList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                rewashRow = "";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_NAME));
                rewashRow += " - ";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_TIME));
                rewashRow += " - ";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_DATE));

                rewashRow += " ";

                rewashRow += cursor.getInt(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_YEAR));

                rewashRow += " \n";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_WASH_PACKAGE));

                rewashRow += " : ";

                rewashRow += cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_REASON));

                rewashList.add(rewashRow);

            }

        }
        return rewashList;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    public int getWashTypeCount(String washType) {
        this.open();
        Cursor cursor = database.rawQuery("Select * from rewashes where washPackage ='" + washType+ "';", null);
        return cursor.getCount();
    }

    public int getWashTypeCountByMonthAndYear(String washType, int month, int year) {
        this.open();
        Cursor cursor = database.rawQuery("Select * from rewashes where washPackage = '"+ washType + "' AND monthNumber = '" + month + "' AND year = '" + year + "';"
                , null);
        return cursor.getCount();
    }


    public int getWashTypeCountByMonth(String washType, int month) {
        this.open();
        Cursor cursor = database.rawQuery("Select * from rewashes where washPackage = '"+ washType + "' AND monthNumber = '" + month + "';"
                , null);
        return cursor.getCount();

    }

    public int getWashTypeCountByYear(String washType, int year) {
        this.open();
        Cursor cursor = database.rawQuery("Select * from rewashes where washPackage = '"+ washType + "' AND year = '" + year + "';"
                , null);
        return cursor.getCount();
    }
}
