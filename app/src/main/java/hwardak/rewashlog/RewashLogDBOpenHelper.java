package hwardak.rewashlog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HWardak on 2017-05-08.
 */

public class RewashLogDBOpenHelper extends SQLiteOpenHelper{


    private static final String LOGTAG = "DATABASE: ";
    private static final String DATABASE_NAME ="rewashlog.db";
    private static int DATABASE_VERSION = 1;


    private static final String TABLE_EMPLOYEES = "employees";
    private static final String COLUMN_EMPLOYEE_ID = "_id";
    private static final String COLUMN_EMPLOYEE_NAME = "name";

    private static final String EMPLOYEE_TABLE_CREATE
            = "CREATE TABLE " + TABLE_EMPLOYEES + " ("
            + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_EMPLOYEE_NAME + " TEXT );";



    private static final String TABLE_REWASHES = "rewashes";
    private static final String COLUMN_REWASH_ID = "rewashID";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WASH_PACKAGE = "washPackage";
    private static final String COLUMN_REASON = "reason";

    private static final String REWASH_TABLE_CREATE
            = "CREATE TABLE " + TABLE_REWASHES + " ("
            + COLUMN_REWASH_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TIME + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_WASH_PACKAGE + " TEXT,"
            + COLUMN_REASON + " TEXT"
            + ");";

    public RewashLogDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EMPLOYEE_TABLE_CREATE);
        Log.d(LOGTAG, "Employee Table created.");

        db.execSQL(REWASH_TABLE_CREATE);
        Log.d(LOGTAG, "Rewash Table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
    }
}
