package com.test.git.coordinatorlayout.Activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.test.git.coordinatorlayout.Adapter.TestAdapter;
import com.test.git.coordinatorlayout.R;
import com.test.git.coordinatorlayout.Utils.Local;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_head_img;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FrameLayout fr_title_head;
    private ValueAnimator titleAnimateOut, titleAnimateIn;
    private int title_height;
    private boolean isLoadingAnimate;//是否在执行动画
    private ViewGroup.MarginLayoutParams frParams;
    private ImageView iv_detail_back, iv_detail_back_b;
    private AppBarLayout mAppbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        setViews();

        //标题高度
        title_height = Local.dip2px(48);
        //初始化数据
        List<String> mMessages = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            mMessages.add("" + i);
        }
        TestAdapter mAdapter = new TestAdapter(this, mMessages, R.layout.item_text);
        mRecyclerView.setAdapter(mAdapter);
        //添加监听
        setLinsteners();

        loadStartAnimation();
    }

    //开场动画
    private void loadStartAnimation() {
        final ViewGroup.MarginLayoutParams rvParams = (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
        final ViewGroup.MarginLayoutParams rvParams1 = (ViewGroup.MarginLayoutParams) mAppbarLayout.getLayoutParams();
        rvParams.topMargin = - Local.getWidthPx();
        mRecyclerView.setLayoutParams(rvParams);

        rvParams1.topMargin = - Local.getWidthPx();
        mAppbarLayout.setLayoutParams(rvParams1);

        final ValueAnimator valueAnima = ValueAnimator.ofInt(-Local.getWidthPx(), 0);
        valueAnima.setDuration(600).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rvParams.topMargin = (int) valueAnimator.getAnimatedValue();
                mRecyclerView.setLayoutParams(rvParams);
                mRecyclerView.setAlpha(valueAnimator.getAnimatedFraction());

                rvParams1.topMargin = (int) valueAnimator.getAnimatedValue();
                mAppbarLayout.setLayoutParams(rvParams1);
            }
        });
        valueAnima.start();
    }

    /**
     * 获取View
     */
    private void setViews() {
        iv_head_img = (ImageView)findViewById(R.id.iv_head_img);

        iv_detail_back = (ImageView)findViewById(R.id.iv_detail_back);
        iv_detail_back_b = (ImageView)findViewById(R.id.iv_detail_back_b);

        //设置图片的高度为屏幕宽度
        iv_head_img.getLayoutParams().height = Local.getWidthPx();

        mAppbarLayout = (AppBarLayout)findViewById(R.id.mAppbarLayout);

        mRecyclerView = (RecyclerView)findViewById(R.id.mRecyclerView);
        //添加布局管理器
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        fr_title_head = (FrameLayout)findViewById(R.id.fr_title_head);
        //获取params
        frParams = (ViewGroup.MarginLayoutParams)fr_title_head.getLayoutParams();

    }

    /**
     * 添加滚动监听
     */
    private void setLinsteners() {
        iv_detail_back.setOnClickListener(this);
        iv_detail_back_b.setOnClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //后去tag,如果为null,则返回
                if(fr_title_head.getTag() == null)return;
                //获取列表距离顶部是否为0,true表示在顶部,列表可以自己控制动画,否则由behavior来控制.
                boolean isIntop = (boolean) fr_title_head.getTag();
                //获取列表第一个可见的item位置
                int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                //列表滚动到距离顶部小于导航栏高度,隐藏导航栏(用来实现下拉导航栏一起下移,所以做的调整,这里是边缘case).
                if(firstVisibleItem == 0 && mLinearLayoutManager.findViewByPosition(firstVisibleItem).getTop() <= title_height){
                    Log.i("ShortContentsVGBehavior", "hide");
                    frParams.topMargin = - title_height;
                    fr_title_head.setLayoutParams(frParams);
                    return;
                }
                //isLoadingAnimate是一个标志位,用来控制是否执行动画,避免列表滚动中频繁执行动画,导致效果不好.
                if(!isLoadingAnimate && isIntop){//控制
                    if(dy > 0){//列表向下滚动,隐藏导航栏
                        AnimateOut();
                    }else{//列表向上滚动,显示导航栏
                        AnimateIn();
                    }
                }

            }
        });
    }

    //显示动画
    private void AnimateIn() {
        if(titleAnimateIn == null) {
            titleAnimateIn = new ValueAnimator().ofInt( - title_height, 0).setDuration(200);
            titleAnimateIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    frParams.topMargin = (int) valueAnimator.getAnimatedValue();
                    fr_title_head.setLayoutParams(frParams);
                }
            });
            titleAnimateIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    isLoadingAnimate = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isLoadingAnimate = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
        if(frParams.topMargin == - title_height) {
            titleAnimateIn.start();
        }
    }

    //隐藏动画
    private void AnimateOut() {
        if(titleAnimateOut == null) {
            titleAnimateOut = new ValueAnimator().ofInt(0, - title_height).setDuration(200);
            titleAnimateOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    frParams.topMargin = (int) valueAnimator.getAnimatedValue();
                    fr_title_head.setLayoutParams(frParams);
                }
            });
            titleAnimateOut.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    isLoadingAnimate = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isLoadingAnimate = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
        if(frParams.topMargin == 0) {
            titleAnimateOut.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_detail_back:
            case R.id.iv_detail_back_b:
                finish();
                break;
        }
    }
}
