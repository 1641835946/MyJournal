package com.example.administrator.myjournal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.MyApplication;
import com.example.administrator.myjournal.util.TagAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TagActivity extends BaseActivity implements TagAdapter.OnItemClickLitener {

    private RecyclerView mRecyclerView;
    private TagAdapter mAdapter;
    private JournalDB journalDB = JournalDB.getInstance(MyApplication.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new TagAdapter());
        mAdapter.setOnItemClickLitener(this);

        Note note = journalDB.loadNote(2222222, "liang洁");
        if (note == null) Log.e("tagActivity", "false");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TagActivity.this, AddTagActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView text = (TextView) view.findViewById(R.id.tag_title);
        Intent intent = new Intent(TagActivity.this, TagLookingActivity.class);
        intent.putExtra("title", text.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(final View view, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(TagActivity.this);
        dialog.setTitle("提醒");
        dialog.setMessage("确定要删除吗？删除此标签的同时会删除此标签下的所有的记录！！！");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView text = (TextView) view.findViewById(R.id.tag_title);
                journalDB.deleteTag(text.getText().toString());
                mAdapter.removeData(position);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
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
                    mRecyclerView.setAdapter(mAdapter = new TagAdapter());
                    mAdapter.setOnItemClickLitener(this);
                    //mAdapter.addData(, data.getStringExtra("backHintString"));
                }
                break;
            default:
        }
    }
}
