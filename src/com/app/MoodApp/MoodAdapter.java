package com.app.MoodApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PsichO on 26.02.14.
 */
public class MoodAdapter extends ArrayAdapter<Mood> {
    private ArrayList<Mood> moods;
    LayoutInflater inflater;

    public MoodAdapter(Context context, int textViewResourceId, ArrayList<Mood> moods) {
        super(context, textViewResourceId,moods);
        this.moods = moods;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder();
            holder.mood = (TextView)convertView.findViewById(R.id.mood);
            holder.comment = (TextView)convertView.findViewById(R.id.comment);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Mood mood = moods.get(pos);
        if(mood != null) {
            holder.mood.setTextColor(Color.BLACK);
            holder.mood.setText(mood.getMood());
            holder.comment.setText(mood.getComment());
        }


        return convertView;
    }

    static class ViewHolder
    {
        TextView mood;
        TextView comment;
    }
}
