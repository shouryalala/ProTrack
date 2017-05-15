package com.eightyeightysix.shourya.protrack;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by shourya on 16/5/17.
 */

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources resources;
    ListModel tempValues = null;

    public CustomAdapter(Activity a, ArrayList d, Resources res) {
        activity = a;
        data = d;
        resources = res;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder {
        public TextView date_entry;
        public TextView content_entry;
    }

    @Override
    public int getCount() {
        if(data.size() <= 0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView == null) {
            vi = inflater.inflate(R.layout.item, null);

            holder = new ViewHolder();
            holder.date_entry = (TextView) vi.findViewById(R.id.date);
            holder.content_entry = (TextView) vi.findViewById(R.id.content);

            vi.setTag(holder);
        }
        else
            holder = (ViewHolder) vi.getTag();

        if(data.size() <= 0) {
            holder.date_entry.setText(R.string.no_data);
        }
        else {
            tempValues = null;
            tempValues = (ListModel) data.get(position);

            holder.date_entry.setText(tempValues.getDate());
            holder.content_entry.setText(tempValues.getContent());
        }
        return vi;
    }
}
