package com.coder.nosandroid.niceosandroid.sampleCustomerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.coder.nosandroid.niceosandroid.Utilities.ViewUtil;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DashboardView extends View {
    private static final int ANGLE = 120;
    private static final float RADIUS = ViewUtil.dp2px(100); //仪表盘半径
    private static final int dash_counter = 20; //刻度个数
    private static final float LENTH = ViewUtil.dp2px(80);
    private static final float dash_width = ViewUtil.dp2px(2);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash = new Path();
    PathDashPathEffect effect;
    int location; //指针位置
    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ViewUtil.dp2px(2));
        //刻度
        dash.addRect(0,0,dash_width,
                ViewUtil.dp2px(10), Path.Direction.CW);

        //计算刻度间隔
        Path arc = new Path();
        arc.addArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS,
                getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                90 + ANGLE/2, 360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        //设置刻度样式
        effect = new PathDashPathEffect(dash,
                (pathMeasure.getLength() - dash_width)/ dash_counter,
                0,
                PathDashPathEffect.Style.ROTATE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画弧线
        canvas.drawArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS,
                getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                90 + ANGLE/2, 360 - ANGLE,
                false, paint );
        //画刻度
        paint.setPathEffect(effect);
        canvas.drawArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS,
                getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                90 + ANGLE/2, 360 - ANGLE,
                false, paint );
        paint.setPathEffect(null);

        //画指针
        float stopX = (float) (getWidth()/2 + Math.cos(Math.toRadians(getAngleFromMark(5))) * LENTH);
        float stopY = (float) (getHeight()/2 + Math.sin(Math.toRadians(getAngleFromMark(5))) * LENTH);
        canvas.drawLine(getWidth()/2, getHeight()/2, stopX, stopY, paint);
    }

    int getAngleFromMark(int mark) {
        return  (int) (90 + (float)ANGLE/2 + (360- (float)ANGLE)/dash_counter*mark);
    }
}
