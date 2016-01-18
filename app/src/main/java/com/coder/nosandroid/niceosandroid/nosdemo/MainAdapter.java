package com.coder.nosandroid.niceosandroid.nosdemo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coder.nosandroid.niceosandroid.R;

import java.util.List;

/**
 * Created by saberhao on 2016/1/18.
 */
public class MainAdapter extends ArrayAdapter<ContentItem> {

    private Typeface mTFlight;
    private Typeface mTFRegular;

    public MainAdapter(Context context, List<ContentItem> objects) {
        super(context, 0,objects);
        this.mTFlight = Typeface.createFromAsset(context.getAssets(),"OpenSans-Light.ttf");
        this.mTFRegular = Typeface.createFromAsset(context.getAssets(),"OpenSans-Regular.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContentItem mContentItem = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,null);
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            holder.tvDesc = (TextView)convertView.findViewById(R.id.tvDesc);
            holder.tvNew = (TextView)convertView.findViewById(R.id.tvNew);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        holder.tvNew.setTypeface(mTFRegular);
        holder.tvName.setTypeface(mTFRegular);
        holder.tvDesc.setTypeface(mTFRegular);

        holder.tvName.setText(mContentItem.itemname);
        holder.tvDesc.setText(mContentItem.desc);

        if (mContentItem.isNew) {
            holder.tvNew.setVisibility(View.VISIBLE);
        } else {
            holder.tvNew.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvDesc;
        TextView tvNew;
    }

}
