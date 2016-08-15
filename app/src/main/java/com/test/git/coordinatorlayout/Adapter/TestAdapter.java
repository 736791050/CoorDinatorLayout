package com.test.git.coordinatorlayout.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.test.git.coordinatorlayout.R;

import java.util.List;

/**
 * Created by lk on 16/8/13.
 */
public class TestAdapter extends BaseAdapter<String, RecyclerView.ViewHolder> {

    public TestAdapter(Context context, List<String> mMessages, int mResources) {
        super(context, mMessages, mResources);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(int viewType, ViewGroup parent) {
        return new MyViewHolder(getView());
    }

    @Override
    protected int setItemViewType(int position) {
        return 0;
    }

    @Override
    protected void setOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_contents;
        private FrameLayout itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = (FrameLayout) itemView;
            tv_contents = (TextView) itemView.findViewById(R.id.tv_contents);
        }

        private void fillContents(int position){
            tv_contents.setText("测试数据:" + position);
        }
    }
}
