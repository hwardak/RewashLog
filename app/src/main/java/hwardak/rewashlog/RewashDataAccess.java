package hwardak.rewashlog;

import android.content.Context;
import android.util.Log;

/**
 * Created by HWardak on 2017-05-13.
 */

public class RewashDataAccess {

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
}
