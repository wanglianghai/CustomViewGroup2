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
 * Created by guanaj on 2017/4/18.
 */

public class EasySwipeMeunLayout extends ViewGroup {

    private static final String TAG = "EasySwipeMeunLayout";
    private int mScaledTouchSlop;
    private int mContentWidth;
    private float lastx;
    //用来创建scroller动画
    private Scroller mScroller;
    private int mRightMenuWidths;

    public EasySwipeMeunLayout(Context context) {
        this(context, null);
    }

    public EasySwipeMeunLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasySwipeMeunLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //创建辅助类对象
        //用户需滑动的最小的距离才被认为是滑动了，单位像素
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mHeight = 0;
        mContentWidth = 0;
        //获取child view 数量
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.setClickable(true);
                //测量child view
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                mHeight = Math.max(mHeight, childView.getMeasuredHeight());

                if (i == 0) {
                    //第一个为内容view
                    mContentWidth = childView.getMeasuredWidth();
                } else {
                    //第二个为左滑按钮
                    mRightMenuWidths = childView.getMeasuredWidth();
                }
        }
        setMeasuredDimension(getPaddingLeft() + getPaddingRight() + mContentWidth,
                mHeight + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                if (i == 0) {
                    childView.layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());

                } else {
                    childView.layout(mContentWidth, getPaddingTop(), mContentWidth + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());
                }
            }
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastx = event.getRawX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //对左边界进行处理
                float distance = lastx - event.getRawX();
                if (Math.abs(distance) > mScaledTouchSlop) {
                    // 当手指拖动值大于mScaledTouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    return true;
                }
                break;

            }

        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                float distance = lastx - event.getRawX();

                lastx = event.getRawX();
                scrollBy((int) distance, 0);
                break;
            }
            case MotionEvent.ACTION_UP:
                //getScrollX()左屏幕边界为原点，左正右负，用系统的，直接算的有误差
                //而且用边界算方便
                //Log.i(TAG, "onTouchEvent: " + getScrollX());
                if (getScrollX() <= 0) {
                    //对右边界进行处理，不让其滑出
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
                } else if (getScrollX() > 0 && getScrollX() >= mRightMenuWidths / 3) {
                    //删除按钮滑出区域大于1／3，滑出删除按钮
                    mScroller.startScroll(getScrollX(), 0, mRightMenuWidths - getScrollX(), 0);
                } else {
                    //删除按钮滑出区域小于1／3，滑回原来的位置
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);

                }
                //有动画要重绘
                invalidate();
                //通知View重绘-invalidate()->onDraw()->computeScroll()

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        //判断Scroller是否执行完毕：
        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通知View重绘-invalidate()->onDraw()->computeScroll()
            invalidate();
        }
    }
}
