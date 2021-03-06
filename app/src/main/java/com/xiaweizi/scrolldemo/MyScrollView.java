package com.xiaweizi.scrolldemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.xiaweizi.scrolldemo.MyView
 *     e-mail : 1012126908@qq.com
 *     time   : 2018/05/08
 *     desc   : 自定义可以展示滑动轨迹的 view
 * </pre>
 */

public class MyScrollView extends View{

    private static final String TAG = "MyScrollView";

    private Paint mPaint;
    private Path mPath;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        int color = ta.getColor(R.styleable.MyView_paint_color, Color.RED);
        ta.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: " + "x: " + event.getX() + "\ty: " + event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 更改画笔的颜色
     */
    public int changeColor() {
        int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.DKGRAY};
        int color = new Random().nextInt(5);
        Log.i(TAG, "changeColor: " + color);
        mPaint.setColor(colors[color]);
        return colors[color];
    }

}
