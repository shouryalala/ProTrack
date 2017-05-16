package com.eightyeightysix.shourya.protrack;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EntryActivity extends AppCompatActivity implements EntryFragment.EntryDialogListener {

    public CustomAdapter customAdapter;
    public ArrayList<ListModel> entry;
    ListView listView;
    public static final String TABLE_NAME = "progress";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_CONTENT = "content";
    public static ProTrackDbHelper mDbHelper;

    @Override
    public void onButtonClickListener(String date, String content) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_DATE, date);
        values.put(COLUMN_NAME_CONTENT, content);
        db.insert(TABLE_NAME, null, values);
        populateList();
        Toast.makeText(getApplicationContext(),"New Entry added!", Toast.LENGTH_LONG).show();
    }

    public EntryActivity entryActivity = null;
    Button new_entry;

    public void populateList() {
        listView = (ListView) findViewById(R.id.listView);
        entry = new ArrayList<ListModel>(100);
        getContent(mDbHelper);
        entryActivity = this;
        Resources resources = getResources();
        customAdapter = new CustomAdapter(entryActivity, entry, resources);
        listView.setAdapter(customAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mDbHelper = new ProTrackDbHelper(getApplicationContext());
        //entryActivity = this;
        populateList();
        new_entry = (Button) findViewById(R.id.button1);
        new_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_entry();
            }
        });

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 23);
        cal.add(Calendar.MINUTE, 00);
        cal.add(Calendar.SECOND, 00);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }



    public void getContent(ProTrackDbHelper mObj) {
        SQLiteDatabase db = mObj.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        while(cursor.moveToNext()) {
            String inst_date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE));
            String inst_content = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CONTENT));
            ListModel listModel = new ListModel();
            listModel.setDate(inst_date);
            listModel.setContent(inst_content);
            entry.add(listModel);
           // customAdapter.add(entry);
        }
        cursor.close();
    }

    public void add_new_entry() {
        DialogFragment dialog = new EntryFragment();
        dialog.show(getSupportFragmentManager(),"EntryFragment");
    }

    @Override
    public void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}