package hwardak.rewashlog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Set;

/**
 * Created by HWardak on 2017-05-08.
 */

public  class RewashLogDBOpenHelper extends SQLiteOpenHelper{


    private static final String LOGTAG = "DATABASE: ";
    private static final String DATABASE_NAME ="rewashlog.db";
    private static int DATABASE_VERSION = 12;


    static final String TABLE_SETTINGS = "settings";
    public static final String COLUMN_USER_EMAIL = "userEmail";
    public static final String COLUMN_EMAIL_PW = "emailPW";
    public static final String COLUMN_RECIPIENT_EMAIL = "recipientEmail";
    public static final String COLUMN_STORE_NUMBER = "storeNumber";

    private static final String SETTINGS_TABLE_CREATE
            = "CREATE TABLE " + TABLE_SETTINGS + " ("
            + COLUMN_USER_EMAIL + " TEXT, "
            + COLUMN_EMAIL_PW + " TEXT, "
            + COLUMN_RECIPIENT_EMAIL + " TEXT, "
            + COLUMN_STORE_NUMBER + " INTEGER);";


    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_EMPLOYEE_ID = "_id";
    public static final String COLUMN_EMPLOYEE_NAME = "name";

    private static final String EMPLOYEE_TABLE_CREATE
            = "CREATE TABLE " + TABLE_EMPLOYEES + " ("
            + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_EMPLOYEE_NAME + " TEXT );";



    public static final String TABLE_REWASHES = "rewashes";
    public static final String COLUMN_REWASH_ID = "rewashID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DAY_OF_MONTH = "day";
    public static final String COLUMN_MONTH = "monthNumber";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_WASH_PACKAGE = "washPackage";
    public static final String COLUMN_REASON = "reason";

    private static final String REWASH_TABLE_CREATE
            = "CREATE TABLE " + TABLE_REWASHES + " ("
            + COLUMN_REWASH_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TIME + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_DAY_OF_MONTH + " INTEGER,"
            + COLUMN_MONTH + " INTEGER,"
            + COLUMN_YEAR + " INTEGER,"
            + COLUMN_WASH_PACKAGE + " TEXT,"
            + COLUMN_REASON + " TEXT"
            + ");";

    public RewashLogDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SETTINGS_TABLE_CREATE);
        Log.d(LOGTAG, "Settings Table created.");

        db.execSQL(EMPLOYEE_TABLE_CREATE);
        Log.d(LOGTAG, "Employee Table created.");

        db.execSQL(REWASH_TABLE_CREATE);
        Log.d(LOGTAG, "Rewash Table created.");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REWASHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        this.onCreate(db);
    }
}
