package com.test.git.coordinatorlayout.View;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.OverScroller;

/**
 * Created by lk on 16/8/22.
 */
public class ViewParent extends FrameLayout implements NestedScrollingParent {
    private final OverScroller mScroller;
    private int mHeaderHeight;
    private View viewHeader;
    private RecyclerView mRecyclerView;
    private static final String TAG = "ViewParent";

    public ViewParent(Context context) {
        this(context, null);
    }

    public ViewParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for(int i = 0; i < getChildCount(); i ++) {
            if(i == 0) {
                viewHeader = getChildAt(0);
            }else {
                View vchild = getChildAt(i);
                if(vchild instanceof RecyclerView){
                    mRecyclerView = (RecyclerView)vchild;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量分割栏的高度
        getChildAt(1).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
        //设置滚动列表高度
        params.height = getMeasuredHeight() - getChildAt(1).getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取头部高度
        mHeaderHeight = viewHeader.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i(TAG, "onStartNestedScroll: ");
        //判断是否纵向滑动
        return (nestedScrollAxes & SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.i(TAG, "onNestedScrollAccepted: ");
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.i(TAG, "onStopNestedScroll: ");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll: ");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.i(TAG, "onNestedPreScroll: ");
        //dy > 0 向上滑动  getScrollY() < mHeaderHeight 头部未完全隐藏
        boolean hide = dy > 0 && getScrollY() < mHeaderHeight;
        //dy < 0 向下滑动  getScrollY() >= 0 头部未完全显示
        //ViewCompat.canScrollVertically(target, -1)  -1 表示scrolling up, 如果target可以向上滑动返回true, 否则返回false.
        boolean show = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (show) {//显示
            if(getScrollY() + dy < 0){
                int sy = - getScrollY();
                scrollBy(0, sy);
                consumed[1] = sy;
            }else {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }


        if(hide){//隐藏
            if(dy + getScrollY() > mHeaderHeight){
                int sy = mHeaderHeight - getScrollY();
                scrollBy(0, sy);
                consumed[1] = sy;
            }else {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i(TAG, "onNestedFling: ");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i(TAG, "onNestedPreFling: ");
        Log.i(TAG, "onNestedFling: ");
        //头部隐藏,则交给滚动列表处理
        if(getScrollY() >= mHeaderHeight) {
            return false;
        }
        //滚动
        mScroller.fling(0, getScrollY(), 0, (int) velocityY, 0, 0, 0, mHeaderHeight);
        invalidate();
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.i(TAG, "getNestedScrollAxes: ");
        return 0;
    }

    float a = 0.5f;//视差因子
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        if(y >= 0 && y <= mHeaderHeight){
            viewHeader.scrollTo(0, (int) (- y * a));
        }
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}
