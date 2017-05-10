package hwardak.rewashlog;

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


    public EmployeeDataAccess(Context context){
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

    public boolean doesEmployeeExist(int employeeId){
        Cursor cursor =
                database.query(RewashLogDBOpenHelper.TABLE_EMPLOYEES, ALL_EMPLOYEE_TABLE_COLUMNS,
                null, null, null, null, null);

        Log.d(LOGTAG, "Returned " + cursor.getCount() + " rows");

        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }

    }

}
