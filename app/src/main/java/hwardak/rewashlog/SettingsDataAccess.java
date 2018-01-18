package hwardak.rewashlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by HWardak on 2017-06-17.
 */

public class SettingsDataAccess {

    private static final String LOGTAG = "DATABASE R:";
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    public SettingsDataAccess(Context context) {
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

    public void saveSettings
            (String userEmail, String userEmailPassword, String recipientEmail, int storeNumber){
        this.open();


        ContentValues values = new ContentValues();
        values.put(RewashLogDBOpenHelper.COLUMN_USER_EMAIL, userEmail);
        values.put(RewashLogDBOpenHelper.COLUMN_EMAIL_PW, userEmailPassword);
        values.put(RewashLogDBOpenHelper.COLUMN_RECIPIENT_EMAIL, recipientEmail);
        values.put(RewashLogDBOpenHelper.COLUMN_STORE_NUMBER, storeNumber);

        //Clears the contents of the table, as there should on be one row of settings
        database.execSQL("delete from " + RewashLogDBOpenHelper.TABLE_SETTINGS);

        //Add the values to the newly empty table.
        database.replace(RewashLogDBOpenHelper.TABLE_SETTINGS, null, values);

        //These two lines were put in to see if the table is growing by each row.
        // Delete later.
        Cursor cursor = database.rawQuery("Select * from settings;", null);
        Log.d(LOGTAG, "Settings row count: " +  cursor.getCount());


        this.close();
    }

     boolean isSettingAvailable() {
        this.open();
        Cursor cursor = database.rawQuery("Select * from settings;", null);
        if(cursor.getCount() == 1){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList getSettings() {
        this.open();
        ArrayList<String> settingsList = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from settings;", null);

        if(cursor.getCount() == 1){
            cursor.moveToNext();
            settingsList.add(cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_USER_EMAIL)));
            settingsList.add(cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_EMAIL_PW)));
            settingsList.add(cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_RECIPIENT_EMAIL)));
            settingsList.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_STORE_NUMBER))));
        }
        return settingsList;
    }

    public String getRecipientEmail() {
        this.open();
        Cursor cursor = database.rawQuery("Select " + RewashLogDBOpenHelper.COLUMN_RECIPIENT_EMAIL + " from settings;", null);
        if (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_RECIPIENT_EMAIL));
        } else {
            return "  ";
        }
    }

    public String getUserEmail() {
        this.open();
        Cursor cursor = database.rawQuery("Select " + RewashLogDBOpenHelper.COLUMN_USER_EMAIL + " from settings;", null);
        if (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_USER_EMAIL));
        } else {
            return "  ";
        }

    }

    public String getUserPw() {
        this.open();
        Cursor cursor = database.rawQuery("Select " + RewashLogDBOpenHelper.COLUMN_EMAIL_PW + " from settings;", null);
        if (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_EMAIL_PW));
        } else {
            return "  ";
        }
    }
}
