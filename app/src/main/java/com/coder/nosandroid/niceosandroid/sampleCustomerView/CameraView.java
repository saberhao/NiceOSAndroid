package com.coder.nosandroid.niceosandroid.sampleCustomerView;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.coder.nosandroid.niceosandroid.Utilities.ViewUtil;

import androidx.annotation.Nullable;

public class CameraView extends View {
    private static final int IMAGE_WIDTH = 600;
    private static final int OFFSET = 100;
    private static final int ROTATION_ANGLES = 100;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        //旋转
        camera.rotateX(45);
        //移动camera位置
        camera.setLocation(0,0, ViewUtil.getZForCamera());//适配像素密度不同的手机
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制上半部分 步骤A->步骤B执行
        canvas.save();
        canvas.translate(OFFSET+IMAGE_WIDTH/2, OFFSET+IMAGE_WIDTH/2); //步骤B
        canvas.rotate(-ROTATION_ANGLES);
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0);
        canvas.rotate(ROTATION_ANGLES);
        canvas.translate(-(OFFSET+IMAGE_WIDTH/2), -(OFFSET+IMAGE_WIDTH/2));
        canvas.drawBitmap(ViewUtil.getAvatar(IMAGE_WIDTH), OFFSET, OFFSET, paint); //步骤A
        canvas.restore();

        //移动canvas在绘制 绘制下半部分 代码类似从下往上执行
        canvas.save();
        canvas.translate(OFFSET+IMAGE_WIDTH/2, OFFSET+IMAGE_WIDTH/2);
        canvas.rotate(-ROTATION_ANGLES);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
        canvas.rotate(ROTATION_ANGLES);
        canvas.translate(-(OFFSET+IMAGE_WIDTH/2), -(OFFSET+IMAGE_WIDTH/2));
        canvas.drawBitmap(ViewUtil.getAvatar(IMAGE_WIDTH), OFFSET, OFFSET, paint);
        canvas.restore();
    }
}
