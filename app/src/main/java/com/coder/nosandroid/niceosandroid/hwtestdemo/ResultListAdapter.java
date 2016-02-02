package com.coder.nosandroid.niceosandroid.hwtestdemo;

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
 * Created by saberhao on 2016/2/2.
 */
public class ResultListAdapter extends ArrayAdapter<HWTestDetail.HWTestType> {
    private int mPassSize;
    private Typeface mTFlight;
    private Typeface mTFRegular;

    public ResultListAdapter(Context context, List<HWTestDetail.HWTestType> objects, int mPassSize) {
        super(context, 0, objects);
        this.mPassSize = mPassSize;
        this.mTFlight = Typeface.createFromAsset(context.getAssets(),"OpenSans-Light.ttf");
        this.mTFRegular = Typeface.createFromAsset(context.getAssets(),"OpenSans-Regular.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HWTestDetail.HWTestType mHwTestType = getItem(position);
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_testitem,null);
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            holder.tvResult = (TextView)convertView.findViewById(R.id.tvResult);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvName.setTypeface(mTFRegular);
        holder.tvResult.setTypeface(mTFRegular);

        switch (mHwTestType) {
            case SPEAKER:
                holder.tvName.setText(R.string.item_speaker);
                break;
            case VIBRATOR:
                holder.tvName.setText(R.string.item_vibration);
                break;
            case ACCELEROMETER:
                holder.tvName.setText(R.string.item_accelerometer);
                break;
            case GYROSCOPE:
                holder.tvName.setText(R.string.item_gyroscope);
                break;
            case PROXIMITY:
                holder.tvName.setText(R.string.item_proximity);
                break;
            case MAGNETIC:
                holder.tvName.setText(R.string.item_magnetic);
                break;
            case BATTAERY:
                holder.tvName.setText(R.string.item_battary);
                break;
            case BT:
                holder.tvName.setText(R.string.item_bt);
                break;
            case WLAN:
                holder.tvName.setText(R.string.item_wlan);
        }
        if(position + 1 <= mPassSize) {
            holder.tvResult.setText(R.string.test_pass);
            holder.tvResult.setTextColor(getContext().getResources().getColor(R.color.limegreen));
        } else {
            holder.tvResult.setText(R.string.test_fail);
            holder.tvResult.setTextColor(getContext().getResources().getColor(R.color.darkred));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvResult;
    }

}
