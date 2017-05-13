package hwardak.rewashlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HWardak on 2017-05-09.
 */

public class EmployeeDataAccess {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private static final String LOGTAG = "DATABASE E: ";
    public static final String[] ALL_EMPLOYEE_TABLE_COLUMNS = {
            RewashLogDBOpenHelper.COLUMN_EMPLOYEE_ID,
            RewashLogDBOpenHelper.COLUMN_EMPLOYEE_NAME
    };


    public EmployeeDataAccess(Context context) {
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

    public void addEmployeeToTable(int i, String s) {
        this.open();
        if (!doesEmployeeExist(i)) {
            ContentValues values = new ContentValues();
            values.put(RewashLogDBOpenHelper.COLUMN_EMPLOYEE_ID, String.valueOf(i));
            values.put(RewashLogDBOpenHelper.COLUMN_EMPLOYEE_NAME, s);
            database.insert(RewashLogDBOpenHelper.TABLE_EMPLOYEES, null, values);
            this.close();
            Log.d(LOGTAG, "Employee " + i + " added");
        } else {
            Log.d(LOGTAG, "Employee " + i + " Exists already");
        }
    }

    public boolean doesEmployeeExist(int employeeId) {
        this.open();

        Cursor cursor =
                database.rawQuery("SELECT 1 FROM " + RewashLogDBOpenHelper.TABLE_EMPLOYEES + " WHERE _id= \"" + employeeId + " \" LIMIT 1", null);


        Log.d(LOGTAG, "Returned " + cursor.getCount() + " rows");
        this.close();

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public String getEmployeeName(int i) {
        this.open();
        Cursor cursor =
                database.query(RewashLogDBOpenHelper.TABLE_EMPLOYEES,
                        new String[]{RewashLogDBOpenHelper.COLUMN_EMPLOYEE_NAME},
                        RewashLogDBOpenHelper.COLUMN_EMPLOYEE_ID + " = ?",
                        new String[]{Integer.toString(i)}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            return cursor.getString(cursor.getColumnIndex(RewashLogDBOpenHelper.COLUMN_EMPLOYEE_NAME));
        }
        return null;
    }
}