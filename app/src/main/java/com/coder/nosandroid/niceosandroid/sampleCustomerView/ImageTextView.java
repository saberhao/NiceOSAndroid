package com.coder.nosandroid.niceosandroid.sampleCustomerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.ViewUtil;

public class ImageTextView extends View {
    private static final float IMAGE_WIDTH = ViewUtil.dp2px(120);
    private static final float IMAGE_OFFSET = ViewUtil.dp2px(80);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, " +
            "sollicitudin in maximus a, vulputate id magna. Nulla non quam a massa sollicitudin " +
            "commodo fermentum et est. Suspendisse potenti. Praesent dolor dui, dignissim quis " +
            "tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. Quisque " +
            "nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. " +
            "Aenean aliquet dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi " +
            "rhoncus lacus facilisis pellentesque nec vitae lorem. Donec et risus eu ligula " +
            "dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum primis in faucibus " +
            "orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum " +
            "finibus, ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies " +
            "metus at magna cursus congue. Nam eu sem eget enim pretium venenatis. " +
            "Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor.";
    float[] cutWidth = new float[1];

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = ViewUtil.getAvatar((int) IMAGE_WIDTH);
        paint.setTextSize(ViewUtil.dp2px(17));
        //获取基线
        paint.getFontMetrics(fontMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, getWidth() - IMAGE_WIDTH, IMAGE_OFFSET, paint);
        int length = text.length();
        float verticalOffset = - fontMetrics.top;
        for (int start = 0; start < length; ) {
            int maxWidth;
            float textTop = verticalOffset + fontMetrics.top;
            float textBottom = verticalOffset + fontMetrics.bottom;
            if (textTop > IMAGE_OFFSET && textTop < IMAGE_OFFSET + IMAGE_WIDTH
                    || textBottom > IMAGE_OFFSET && textBottom < IMAGE_OFFSET + IMAGE_WIDTH) {
                // 文字和图片在同一行
                maxWidth = (int) (getWidth() - IMAGE_WIDTH);
            } else {
                // 文字和图片不在同一行
                maxWidth = getWidth();
            }
            int count = paint.breakText(text, start, length, true, maxWidth, null);
            canvas.drawText(text, start, start + count, 0, verticalOffset, paint);
            start += count;
            verticalOffset += paint.getFontSpacing();
        }
    }
}
