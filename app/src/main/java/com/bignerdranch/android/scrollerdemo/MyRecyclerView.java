package com.bignerdranch.android.scrollerdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2017/8/21/021.
 */

public class MyRecyclerView extends RecyclerView {
    private float downX;
    private float downY;
    private int mScaleTouchSlop;

    private boolean mChild;
    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration c = ViewConfiguration.get(context);
        mScaleTouchSlop = c.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(downY - e.getRawY()) < 150) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
