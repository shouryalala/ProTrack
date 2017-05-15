package com.eightyeightysix.shourya.protrack;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EntryActivity extends AppCompatActivity {

    public CustomAdapter customAdapter;
    public ArrayList<ListModel> entry;
    ListView listView;
    public EntryActivity entryActivity = null;

    private EntryActivity() {
    }

    /* Inner class that defines the table contents */
    public static class ProgressEntry implements BaseColumns {
        public static final String TABLE_NAME = "progress";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CONTENT = "content";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ProTrackDbHelper mDbHelper = new ProTrackDbHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        entry = new ArrayList<ListModel>(100);
        getContent(mDbHelper);

        Resources resources = getResources();
        customAdapter = new CustomAdapter(entryActivity, entry, resources);
        listView.setAdapter(customAdapter);
    }

    public void getContent(ProTrackDbHelper mObj) {
        SQLiteDatabase db = mObj.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ProgressEntry.TABLE_NAME, null);
        while(cursor.moveToNext()) {
            String inst_date = cursor.getString(cursor.getColumnIndex(ProgressEntry.COLUMN_NAME_DATE));
            String inst_content = cursor.getString(cursor.getColumnIndex(ProgressEntry.COLUMN_NAME_CONTENT));
            ListModel listModel = new ListModel();
            listModel.setDate(inst_date);
            listModel.setContent(inst_content);
            entry.add(listModel);
           // customAdapter.add(entry);
        }
        cursor.close();
    }
}