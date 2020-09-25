package com.coder.nosandroid.niceosandroid.sampleCustomerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.coder.nosandroid.niceosandroid.Utilities.ViewUtil;

import androidx.annotation.Nullable;

public class TestView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(100, 100, 500, 500, paint);
        canvas.drawCircle(getWidth()/2, getHeight() / 2, ViewUtil.dp2px(150), paint);
    }
}
