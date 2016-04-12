package com.zeus.multiuseapp.notepad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zeus.multiuseapp.R;

/**
 * Created by Zeus on 4/6/2016.
 */
public class LinedEditText extends EditText {

    private Rect mRect;
    private Paint mPaint;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.parseColor(getContext().getString(R.string.line_color)));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = getHeight();
        int line_Height = getLineHeight();

        int count = height / line_Height;

        if (getLineCount() > count) {
            count = getLineCount();
        }

        Rect r = mRect;
        Paint paint = mPaint;
        int baseline = getLineBounds(0, r);

        for (int i = 0; i < count; i++) {
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            baseline += getLineHeight();
        }
        super.onDraw(canvas);
    }
}
