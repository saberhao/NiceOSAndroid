package com.coder.nosandroid.niceosandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coder.nosandroid.niceosandroid.R;


public class drawableFragment extends Fragment {
	
	final int DISPLAY_SPRING_IMAGE = 0;
	final int DISPLAY_SUMMER_IMAGE = 1;
	final int DISPLAY_AUTUMN_IMAGE = 2;
	final int DISPLAY_WINTER_IMAGE = 3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_layout,null);
		ImageView iv = (ImageView) view.findViewById(R.id.fragment_iv);
		int tag = this.getArguments().getInt("key");
		switch(tag)
		{
		case DISPLAY_SPRING_IMAGE :
			iv.setBackgroundResource(R.drawable.spring);
			break;
		case DISPLAY_SUMMER_IMAGE :
			iv.setBackgroundResource(R.drawable.summer);
			break;
		case DISPLAY_AUTUMN_IMAGE :
			iv.setBackgroundResource(R.drawable.autumn);
			break;
		default :
			iv.setBackgroundResource(R.drawable.winter);	
		}
		return view;
	}

}
