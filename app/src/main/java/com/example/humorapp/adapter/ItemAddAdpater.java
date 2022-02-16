package com.example.humorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.humorapp.R;
import com.example.humorapp.model.Feeling;
import com.example.humorapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAddAdpater extends ArrayAdapter<Item> {

    public ItemAddAdpater(Context context, ArrayList<Item> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feeling, parent, false);
        }
        //
        ImageView image = (ImageView) convertView.findViewById(R.id.imageFeeling);
        TextView txtFeeling = (TextView) convertView.findViewById(R.id.textFeeling);
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.textAuthor);
        TextView txtDate = (TextView) convertView.findViewById(R.id.textDate);
        //
        if (item.getFeeling() != null) {
            Glide.with(getContext()).load(item.getFeeling().getImage()).into(image);
            txtFeeling.setText(item.getFeeling().getName());
        }

        if (item.getUser() != null) {
            txtAuthor.setText(item.getUser().getName());
        }

        if(item.getDate() != null) {
            txtDate.setText(item.getDate());
        }
        //
        return convertView;
    }
}
