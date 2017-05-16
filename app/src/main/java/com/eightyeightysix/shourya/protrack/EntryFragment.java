package com.eightyeightysix.shourya.protrack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import java.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by shourya on 16/5/17.
 */

public class EntryFragment extends DialogFragment{

    EntryActivity entryActivity;
    public static String content, date;
    TextView textView_date;
    EditText editText_content;

    public interface EntryDialogListener {
        public void onButtonClickListener(String date, String content);
    }

    EntryDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EntryDialogListener) activity;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstances) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View inf = layoutInflater.inflate(R.layout.entry_layout, null);
        textView_date = (TextView)inf.findViewById(R.id.enter_date);
        editText_content = (EditText)inf.findViewById(R.id.enter_content);
        date = getDate();
        textView_date.setText(date);
        builder.setView(inf);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                content = editText_content.getText().toString();
                mListener.onButtonClickListener(date, content);
            }
        });
        return builder.create();
    }

    public String getDate() {

        Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        if(month == Calendar.MAY) {
            return "May " + date;
        }
        else if(month == Calendar.JUNE) {
            return "June " + date;
        }
        else {
            return "July " + date;
        }
    }
}
