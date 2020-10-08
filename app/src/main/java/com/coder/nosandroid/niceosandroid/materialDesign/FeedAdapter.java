package com.coder.nosandroid.niceosandroid.materialDesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.nosandroid.niceosandroid.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder feedHolder, int i) {
        //用户头像
        Picasso.with(feedHolder.itemView.getContext())
                .load(getAvatarResId(i))
                .centerInside()
                .fit()
                .into(feedHolder.mIvAvatar);

        //内容图片
        Picasso.with(feedHolder.itemView.getContext())
                .load(getContentResId(i))
                .centerInside()
                .fit()
                .into(feedHolder.mIvContent);

        //nickname
        feedHolder.mTvNickname.setText("Saberhao " + i);
    }

    private int getAvatarResId(int position){
        switch (position % 4){
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }

    public int getContentResId(int position){
        switch (position % 4){
            case 0:
                return R.drawable.gakki_1;
            case 1:
                return R.drawable.gakki_2;
            case 2:
                return R.drawable.gakki_3;
            case 3:
                return R.drawable.gakki_4;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class FeedHolder extends RecyclerView.ViewHolder{

        ImageView mIvAvatar;
        ImageView mIvContent;
        TextView mTvNickname;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mIvContent = itemView.findViewById(R.id.iv_content);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
        }
    }

}
