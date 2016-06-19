package com.example.administrator.myjournal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.helper.OnStartDragListener;
import com.example.administrator.myjournal.helper.SimpleItemTouchHelperCallback;
import com.example.administrator.myjournal.model.Hint;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.MyApplication;
import com.example.administrator.myjournal.util.TagAdapter;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TagActivity extends BaseActivity implements OnStartDragListener, TagAdapter.OnItemClickLitener {

    private RecyclerView mRecyclerView;
    private TagAdapter mAdapter;
    private JournalDB journalDB = JournalDB.getInstance(MyApplication.getContext());
    private ItemTouchHelper mItemTouchHelper;
    private TextView title;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("标签");
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new TagAdapter());
        mAdapter.setOnItemClickLitener(this);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.tag_add_float);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(TagActivity.this, AddTagActivity.class);
                startActivityForResult(addIntent, 1);
            }
        });

        title = (TextView) findViewById(R.id.title);
        title.setText("标签");
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView tag = (TextView) view.findViewById(R.id.tag_title);
        TextView definition = (TextView) view.findViewById(R.id.tag_content);
        Intent intent = new Intent(TagActivity.this, LookTagActivity.class);
        intent.putExtra("tag", tag.getText().toString());
        intent.putExtra("definition", definition.getText().toString());
        startActivity(intent);
    }
    //有些id的名字一样，是不是小心改名?能不一样还是不一样的好。
//
//    @Override
//    public void onItemLongClick(final View view, final int position) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(TagActivity.this);
//        dialog.setTitle("提醒");
//        dialog.setMessage("确定要删除吗？删除此标签的同时会删除此标签下的所有的记录！！！");
//        dialog.setCancelable(true);
//        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                TextView text = (TextView) view.findViewById(R.id.tag_title);
//                journalDB.deleteTag(text.getText().toString());
//                mAdapter.removeData(position);
//            }
//        });
//        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        dialog.show();
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        mRecyclerView.setAdapter(mAdapter = new TagAdapter());
//        mAdapter.setOnItemClickLitener(this);
//    }有了这个拖拽失效

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
//                    mRecyclerView.setAdapter(mAdapter = new TagAdapter());
//                    mAdapter.setOnItemClickLitener(this);
//                    LogUtil.e("TagActivity", "onActivityResult");
//                    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
//                    mItemTouchHelper = new ItemTouchHelper(callback);
//                    mItemTouchHelper.attachToRecyclerView(mRecyclerView);
                    Hint hint = new Hint();
                    hint.setTag(data.getStringExtra("backHint"));
                    hint.setDefinition(data.getStringExtra("backDefinition"));
//java.lang.IndexOutOfBoundsException: Invalid index 3, size is 3
                    mAdapter.addData(hint);
                }
                break;
            default:
        }
    }
}
