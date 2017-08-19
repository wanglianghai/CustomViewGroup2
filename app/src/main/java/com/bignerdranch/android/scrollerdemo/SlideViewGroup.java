package com.bignerdranch.android.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/19/019.
 */

public class SlideViewGroup extends ViewGroup {
    public SlideViewGroup(Context context) {
        super(context);
    }

    public SlideViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0, height = 0, childCount = 0;
        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            width += childView.getMeasuredWidth();
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
}
