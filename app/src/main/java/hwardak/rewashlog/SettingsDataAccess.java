package hwardak.rewashlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
            (String userEmail, String userEmailPassword, String recipientEmail, String storeNumber){
        this.open();
        ContentValues values = new ContentValues();
        values.put(RewashLogDBOpenHelper.COLUMN_USER_EMAIL, userEmail);
        values.put(RewashLogDBOpenHelper.COLUMN_EMAIL_PW, userEmailPassword);
        values.put(RewashLogDBOpenHelper.COLUMN_RECIPIENT_EMAIL, recipientEmail);
        values.put(RewashLogDBOpenHelper.COLUMN_STORE_NUMBER, storeNumber);

        database.insert(RewashLogDBOpenHelper.TABLE_SETTINGS, null, values);

        //These two lines were put in to see if the table is growing by each row.
        // Delete later.
        Cursor cursor = database.rawQuery("Select * from settings;", null);
        Log.d(LOGTAG, "Settings row count: " +  cursor.getCount());


        this.close();
    }

}
