package com.test.git.coordinatorlayout.View;

import android.support.v4.view.NestedScrollingParent;
import android.view.View;

/**
 * Created by lk on 16/8/23.
 */
public class ViewParentTest implements NestedScrollingParent {
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

    }

    @Override
    public void onStopNestedScroll(View target) {

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }


    /**
     * 引用此接口的条件:
     * 1.继承ViewGroup或ViewGroup子类
     * 2.子View中包含可以滚动的ChildView(例如:RecyclerView)
     */
    public interface NestedScrollingParent {
        /**
         * 列表滚动时首先调用
         * nestedScrollAxes用来判断滑动方向:SCROLL_AXIS_HORIZONTAL, SCROLL_AXIS_VERTICAL, SCROLL_AXIS_NONE
         * @param child
         * @param target
         * @param nestedScrollAxes
         * @return
         */
        public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes);

        /**
         * onStartNestedScroll之后调用
         * @param child
         * @param target
         * @param nestedScrollAxes
         */
        public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes);

        /**
         * 滚动停止调用
         * @param target
         */
        public void onStopNestedScroll(View target);

        /**
         * 滚动之前调用,一般用此方法来消耗滚动
         * consumed[0]用来消耗x方法, consumed[1]用来消耗y方法.
         * 如果dy或dy未被完全消耗,则传递给剩余部分给滚动View自行处理
         * @param target
         * @param dx
         * @param dy
         * @param consumed
         */
        public void onNestedPreScroll(View target, int dx, int dy, int[] consumed);

        /**
         *滚动中
         * @param target
         * @param dxConsumed
         * @param dyConsumed
         * @param dxUnconsumed
         * @param dyUnconsumed
         */
        public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed);


        /**
         * 快速滑动前调用
         * @param target
         * @param velocityX
         * @param velocityY
         * @return
         */
        public boolean onNestedPreFling(View target, float velocityX, float velocityY);

        /**
         * onNestedPreFling之后调用
         * @param target
         * @param velocityX
         * @param velocityY
         * @param consumed
         * @return
         */
        public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed);

        /**
         * 获取当前滚动方向
         * @return
         */
        public int getNestedScrollAxes();
    }
}
