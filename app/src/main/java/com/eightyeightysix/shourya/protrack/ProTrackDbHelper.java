package com.eightyeightysix.shourya.protrack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shourya on 15/5/17.
 */

public class ProTrackDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Protrack.db";
    private static final String SQL_CREATE_QUERIES = "CREATE TABLE " + EntryActivity.TABLE_NAME + " ("
            + "ID INTEGER PRIMARY KEY AUTOINCREMENT," + EntryActivity.COLUMN_NAME_DATE + " TEXT,"
            + EntryActivity.COLUMN_NAME_CONTENT + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EntryActivity.TABLE_NAME;

    public ProTrackDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
