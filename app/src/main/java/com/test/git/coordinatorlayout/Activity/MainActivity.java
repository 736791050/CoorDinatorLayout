package com.test.git.coordinatorlayout.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.git.coordinatorlayout.Adapter.BaseAdapter;
import com.test.git.coordinatorlayout.Adapter.TestAdapter;
import com.test.git.coordinatorlayout.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CoordinatorActivity.class));
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.mRecyclerView);
        //添加布局管理器
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //初始化数据
        List<String> mMessages = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            mMessages.add("" + i);
        }
        TestAdapter mAdapter = new TestAdapter(this, mMessages, R.layout.item_text);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemOnClickLinsteners(new BaseAdapter.ItemOnclickLinstener() {
            @Override
            public void setItemOnclickLinsteners(View v, int postion, int type) {
                startActivity(new Intent(MainActivity.this, CoordinatorActivity.class));
            }
        });
    }
}
