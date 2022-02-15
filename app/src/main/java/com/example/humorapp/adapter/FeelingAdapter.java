package com.example.humorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.humorapp.R;
import com.example.humorapp.model.Feeling;

import java.util.ArrayList;

public class FeelingAdapter extends ArrayAdapter<Feeling> {

    public FeelingAdapter(Context context, ArrayList<Feeling> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Feeling feeling = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feeling, parent, false);
        }
        //
        ImageView image = (ImageView) convertView.findViewById(R.id.imageFeeling);
        TextView txtFeeling = (TextView) convertView.findViewById(R.id.textFeeling);
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.textAuthor);
        TextView txtDate = (TextView) convertView.findViewById(R.id.textDate);
        //
        Glide.with(getContext()).load(feeling.getImage()).into(image);
        txtFeeling.setText(feeling.getName());
        if(feeling.getUser() != null) {
            txtAuthor.setText(feeling.getUser().getName());
            txtDate.setText(feeling.getDate().toString());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
