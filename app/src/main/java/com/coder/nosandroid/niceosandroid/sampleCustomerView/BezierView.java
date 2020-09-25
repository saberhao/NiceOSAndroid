package com.coder.nosandroid.niceosandroid.sampleCustomerView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.animation.PathInterpolatorCompat;

public class BezierView extends View {

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //画笔
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();

    private void init(){

        Paint paint = mPaint;
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        //一阶贝塞尔曲线
        Path path = mPath; //用局部变量，性能好
//        path.moveTo(100,100);
//        path.lineTo(300,300);
//
//        //二阶贝塞尔曲线
//        //path.quadTo(500,0,700,300);
//        //相对二阶贝塞尔曲线实现
//        path.rQuadTo(200,-300,400,0);

        path.moveTo(200,600);
        //三阶贝塞尔曲线
        path.cubicTo(400,300,600,900,800,600);
        //path.rCubicTo(200,-300,400,300,600,0);

        //画笔下移
        path.moveTo(200,1000);
        //计算贝塞尔曲线值方法一,坐标数组包含{起点 - 控制点 - 终点}
        float[] xPoints = new float[]{200,400,600,800};
        float[] yPoints = new float[]{1000,700,1300,1000};

        int fps = 1000;
        float delta = 1.0f/fps;
        for (float progress = 0; progress <= 1; progress += delta) {
            //进度
            float x = deCasteljau(progress,xPoints);
            float y = deCasteljau(progress,yPoints);
            path.lineTo(x,y);
        }

        //画笔下移
        path.moveTo(200,1400);
        //计算贝塞尔曲线值方法一,坐标数组包含{起点 - 控制点 - 终点}
        xPoints = new float[]{200,400,600,800};
        yPoints = new float[]{1400,1100,1700,1400};

        int endIndex = xPoints.length-1;
        for (float progress = 0; progress <= 1; progress += delta) {
            //进度
            float x = deCasteljau2(0,endIndex,progress,xPoints);
            float y = deCasteljau2(0,endIndex,progress,yPoints);
            path.lineTo(x,y);
        }

        Path path2 = new Path();
        path2.cubicTo(200f, 200f, 400f, 100f, 600f, 500f);
        path2.lineTo(1f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 20);
        //animator.setInterpolator(PathInterpolatorCompat.create(path2));
        animator.start();
    }

    /**
     * 根据德卡斯特里奥算法计算t时刻贝塞尔曲线的点的值 {x或y}，非递归实现
     *
     * @param u 时间 {0~1}
     * @param values 贝塞尔点（控制点和终点）集合 {x或y}
     * @return u时刻贝塞尔曲线所处的点坐标
     */
    private float deCasteljau(float u, float... values){
        final int n = values.length;
        for (int k = 1; k < n; k++) {
            for (int j = 0; j < k; j++) {
                values[j] = values[j]+(values[j+1] - values[j])*u;
            }
        }
        //运算结果保存第一位
        return values[0];
    }

    /**
     * 根据德卡斯特里奥算法计算t时刻贝塞尔曲线的点的值 {x或y}，非递归实现
     *
     * @param endIndex 贝塞尔曲线结束点集合下标
     * @param startIndex  贝塞尔曲线起点集合下标
     * @param u 时间 {0~1}
     * @param values 贝塞尔点（控制点和终点）集合 {x或y}
     * @return  u时刻贝塞尔曲线点坐标 {x或y}
     */
    private float deCasteljau2(int startIndex, int endIndex, float u, float... values){
        if (endIndex == 1){
            return (1-u)*values[startIndex] + u*values[startIndex+1];
        } else {
            return (1 - u) * deCasteljau2(startIndex, endIndex - 1, u,values) + u*deCasteljau2(startIndex + 1, endIndex - 1, u,values);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
