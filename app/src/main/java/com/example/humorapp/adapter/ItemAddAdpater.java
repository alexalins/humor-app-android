package com.example.humorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.humorapp.R;
import com.example.humorapp.model.Feeling;

import java.util.List;

public class ItemAddAdpater extends BaseAdapter {

    private Context context;
    private List<Feeling> listFeeling;

    public ItemAddAdpater(Context context, List<Feeling> listFeeling) {
        this.context = context;
        this.listFeeling = listFeeling;
    }
    @Override
    public int getCount() {
        return listFeeling.size();
    }

    @Override
    public Object getItem(int position) {
        return listFeeling.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listFeeling.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Feeling feeling = (Feeling) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_feeling, parent, false);
        }
        //
        ImageView image = (ImageView) convertView.findViewById(R.id.imageFeeling);
        TextView txtFeeling = (TextView) convertView.findViewById(R.id.textFeeling);
        //
        Glide.with(getContext()).load(feeling.getImage()).into(image);
        txtFeeling.setText(feeling.getName());
        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Feeling> getListFeeling() {
        return listFeeling;
    }

    public void setListFeeling(List<Feeling> listFeeling) {
        this.listFeeling = listFeeling;
    }
}
