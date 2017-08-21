package com.bignerdranch.android.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/8/21/021.
 */

public class PageView extends ViewGroup {
    private int mScaleTouchSlop;
    private float lastX;
    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration c = ViewConfiguration.get(context);
        mScaleTouchSlop = c.getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0, height = 0, childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.setClickable(true);
            measureChild(v, widthMeasureSpec, heightMeasureSpec);
            width += v.getMeasuredWidth();
            height = v.getMeasuredHeight();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0, top = 0, childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.layout(left, top, left += v.getMeasuredWidth(), top + v.getMeasuredHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float distance = lastX - event.getRawX();
                lastX = event.getRawX();
                scrollBy((int) distance, 0);
                break;
        }
        return super.onTouchEvent(event);
    }
}
