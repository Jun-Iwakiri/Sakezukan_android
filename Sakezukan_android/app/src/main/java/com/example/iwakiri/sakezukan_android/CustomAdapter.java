package com.example.iwakiri.sakezukan_android;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iwakiri on 2017/04/28.
 */

public class CustomAdapter extends ArrayAdapter<UnifiedData> {

    private int mResource;
    private List<UnifiedData> mObjects;
    private LayoutInflater mLayoutInflater;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UnifiedData> objects) {
        super(context, resource, objects);

        mResource = resource;
        mObjects = objects;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = mLayoutInflater.inflate(mResource, null);
        }

        UnifiedData data = mObjects.get(position);

        TextView id = (TextView) view.findViewById(R.id.textView29);
        id.setText(String.valueOf(data.getSakeId()));

        TextView brand = (TextView) view.findViewById(R.id.textView28);
        brand.setText(data.getBrand());

        TextView breweryName = (TextView) view.findViewById(R.id.textView27);
        breweryName.setText(data.getBreweryName());

        return view;
    }
}
