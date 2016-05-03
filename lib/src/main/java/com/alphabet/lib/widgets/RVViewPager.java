package com.alphabet.lib.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by alphabet on 5/3/16.
 */
public class RVViewPager extends RecyclerView {

    private int mCurrentPosition;

    public RVViewPager(Context context) {
        super(context);
    }

    public RVViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RVViewPager(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean fling = super.fling(velocityX, velocityY);
        if (fling) {
            if (getLayoutManager().canScrollHorizontally()) {
                adjustPositionX(velocityX);
            } else {
                adjustPositionY(velocityY);
            }
        }
        return fling;
    }

    private void adjustPositionY(int velocityY) {

    }

    private void adjustPositionX(int velocityX) {
        int childCount = getChildCount();
        if (childCount > 0) {
            if (getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                linearLayoutManager.findFirstVisibleItemPosition();
                linearLayoutManager.findLastVisibleItemPosition();
            }
        }
    }
}
