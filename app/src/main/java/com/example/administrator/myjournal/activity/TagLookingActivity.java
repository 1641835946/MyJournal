package com.example.administrator.myjournal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.util.MyApplication;
import com.example.administrator.myjournal.util.TagAdapter;
import com.example.administrator.myjournal.util.TagLookingAdapter;

/**
 * Created by Administrator on 2016/6/3.
 */
public class TagLookingActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TagLookingAdapter mAdapter;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_looking_layout);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        titleView = (TextView) findViewById(R.id.look_tag_title);
        titleView.setText(title);
        mRecyclerView = (RecyclerView) findViewById(R.id.look_tag_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new TagLookingAdapter(title));//adapter的构造函数
    }
}
