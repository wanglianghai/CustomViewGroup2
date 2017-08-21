package com.bignerdranch.android.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/8/19/019.
 */

public class SlideViewGroup extends ViewGroup {
    private static final String TAG = "SlideViewGroup";
    private Scroller mScroller;
    private VelocityTracker mVelocity;

    private int mScaledTouchSlop;
    private float mHideViewWidth;
    private float lastX;
    public SlideViewGroup(Context context) {
        this(context, null);
    }

    public SlideViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //创建辅助类对象
        //用户需滑动的最小的距离才被认为是滑动了，单位像素
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        mScroller = new Scroller(context);
        mVelocity = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0, height = 0, childCount;
        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //点击事件要设置
            childView.setClickable(true);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            width += childView.getMeasuredWidth();
            if (i == 1) {
                mHideViewWidth = childView.getMeasuredWidth();
            }
            height = childView.getHeight();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0, top = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(left, top, left += childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
    }

    //Return true to steal motion events from the children and have
    // them dispatched to this ViewGroup through onTouchEvent().
    //判断滑动距离
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getRawX() - lastX) > mScaledTouchSlop) {
//                    Log.i(TAG, "onInterceptTouchEvent: ");
                    return true;
                }

                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocity.addMovement(event);
        mVelocity.computeCurrentVelocity(1000);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //小于0向左
                float distance = event.getRawX() - lastX;
                lastX = event.getRawX();
                scrollBy((int) -distance, 0);
                break;

            case MotionEvent.ACTION_UP:
                float velocity = mVelocity.getXVelocity();
                Log.i(TAG, "onTouchEvent velocity: " + velocity);
                if ((getScrollX() < mHideViewWidth / 3 || velocity > 100) && velocity > -100) {
                    //scrollBy(-getScrollX(), 0);
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
                } else {
                    mScroller.startScroll(getScrollX(), 0, (int) (mHideViewWidth - getScrollX()), 0);
                }

                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
