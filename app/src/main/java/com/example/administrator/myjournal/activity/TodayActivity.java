package com.example.administrator.myjournal.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.LogUtil;
import com.example.administrator.myjournal.util.MyApplication;
import com.example.administrator.myjournal.util.TagAdapter;
import com.example.administrator.myjournal.util.TodayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TodayActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TodayAdapter mAdapter;
    private JournalDB journalDB = JournalDB.getInstance(MyApplication.getContext());
    private int mPosition;
    private String mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_layout);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.today_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new TodayAdapter());
        mAdapter.setOnItemClickLitener(new TodayAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mPosition = position;
                LogUtil.e("todayactivity", "点击了");
                TextView tag = (TextView) view.findViewById(R.id.today_title);
                mTag = tag.getText().toString();
                Intent intent = new Intent(TodayActivity.this, TodayEditActivity.class);
                intent.putExtra("tag", mTag);
                startActivityForResult(intent, 1);
                LogUtil.e("todayactivity", "跳转了");
            }
        });
        List<Long> timeList = journalDB.loadTime();
        List<String> hintList = journalDB.loadHint();
        List<Note> noteList = journalDB.loadNote();
        if (timeList != null) {
            LogUtil.e("测试数据库todayactivity", "timetable isn't null");
            //之前20160607没有，检查是否存在，否就添加。现在有了，但不一定明天还行
            for (int i = 0; i <timeList.size(); i++) {
                LogUtil.e("time", timeList.get(i).toString());
            }
        }
        if (hintList != null) {
            LogUtil.e("测试数据库todayactivity", "hint isn't null");
            for (int i = 0; i <hintList.size(); i++) {
                LogUtil.e("hint", hintList.get(i));
            }
        }
        if (noteList != null) {//插入有问题，不是update是insert
            LogUtil.e("测试数据库todayactivity", "note isn't null");
            for (int i = 0; i <noteList.size(); i++) {
                LogUtil.e("note", "time:"+noteList.get(i).getTime()+"tag:"+noteList.get(i).getTag()+"content" +noteList.get(i).getDefinition());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    mAdapter.removeData(mPosition);
                    mAdapter.addData(mPosition, new Note(TodayAdapter.loginTime, mTag, data.getStringExtra("content")));
//                    LogUtil.e("todayactivity", "第1");
//                    mRecyclerView.setAdapter(mAdapter = new TodayAdapter());
//                    LogUtil.e("todayactivity", "第2");
//                    mAdapter.setOnItemClickLitener(new TodayAdapter.OnItemClickLitener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            LogUtil.e("todayactivity", "点击了");
//                            TextView tag = (TextView) findViewById(R.id.today_title);
//                            Intent intent = new Intent(TodayActivity.this, TodayEditActivity.class);
//                            intent.putExtra("tag", tag.getText().toString());
//                            startActivityForResult(intent, 1);
//                            LogUtil.e("todayactivity", "跳转了");
//                        }
//                    });//对于不了解的东西，如果不是拼写错误，就可能是使用不当的问题
                }
                break;
            default:
        }
    }
}

