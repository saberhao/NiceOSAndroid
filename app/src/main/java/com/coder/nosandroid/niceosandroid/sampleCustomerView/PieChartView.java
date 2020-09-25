package com.coder.nosandroid.niceosandroid.sampleCustomerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.ViewUtil;

import androidx.annotation.Nullable;

public class PieChartView extends View {
    private static final int RADIUS = (int) ViewUtil.dp2px(120);
    private static final int LENGTH = (int) ViewUtil.dp2px(20); //偏移角度
    int[] anglesDelta = {60, 100, 120, 80};
    int[] colors = {getResources().getColor(R.color.pink),
            Color.BLUE, Color.GREEN, Color.YELLOW};

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF bounds = new RectF();
    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS,
                getWidth()/2 + RADIUS, getHeight()/2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.RED);
        //canvas.drawArc(bounds, 0, 360, true,paint);
        int currentAngle = 0;
        for (int i = 0; i < anglesDelta.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == 2) {
                float translateX = (float) (Math.cos(Math.toRadians(
                        currentAngle + anglesDelta[i]/2)) * LENGTH);
                float translateY = (float) (Math.sin(Math.toRadians(
                        currentAngle + anglesDelta[i]/2)) * LENGTH);
                canvas.translate(translateX, translateY);
            }
            canvas.drawArc(bounds, currentAngle, anglesDelta[i],
                    true, paint);
            canvas.restore();
            currentAngle = currentAngle + anglesDelta[i];
        }
    }
}
