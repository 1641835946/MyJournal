package com.example.administrator.myjournal.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.util.AllAdapter;

/**
 * Created by Administrator on 2016/6/6.
 */
public class AllActivity extends BaseActivity {

    private JournalDB journalDB;
    private RecyclerView mRecyclerView;
    private AllAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.all_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new AllAdapter());
    }
}
