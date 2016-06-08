package com.example.administrator.myjournal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Hint;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.CurrentTime;
import com.example.administrator.myjournal.util.LogUtil;
import com.example.administrator.myjournal.util.MyApplication;

/**
 * Created by Administrator on 2016/6/3.
 */
public class TodayEditActivity extends BaseActivity implements View.OnClickListener {

    private TextView tagView;
    private TextView hintView;
    private EditText editText;
    private Button certainButton;
    private JournalDB journalDB;
    private String preContent;
    private Note note;
    private String tagText;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_edit_layout);
        init();
    }

    private void init() {
        tagView = (TextView) findViewById(R.id.today_edit_tag);
        hintView = (TextView) findViewById(R.id.today_edit_hint);
        editText = (EditText) findViewById(R.id.today_edit_content);
        certainButton = (Button) findViewById(R.id.today_edit_certain);
        time = CurrentTime.getTime();
        Intent receiveIntent = getIntent();
        tagText = receiveIntent.getStringExtra("tag");
        tagView.setText(tagText);
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        Hint hint = journalDB.loadHint(tagText);
        hintView.setText(hint.getDefinition());
        note = journalDB.loadNote(time, tagText);
        LogUtil.e("TodayEditActivity", note.toString());
        //if (note.getDefinition() == "") {
            editText.setText(note.getDefinition());
            preContent = note.getDefinition();
        //}
        certainButton.setOnClickListener(this);
        editText.setFocusable(false);
        certainButton.requestFocus();
    }

    @Override
    public void onClick(View v) {
        if ("保存".equals(certainButton.getText().toString())) {
//            if (preContent.isEmpty()) {
//                if (editText.getText().toString().isEmpty()) {
//                    Toast.makeText(TodayEditActivity.this, "未保存，不能保存空的内容", Toast.LENGTH_SHORT).show();
//                } else {
//                    note.setTime(time);
//                    note.setTag(tagText);
//                    note.setDefinition(editText.getText().toString());
//                    journalDB.saveNote(note, true);//插入
//                    preContent = editText.getText().toString();
//                }
//            } else {
//                if (editText.getText().toString().isEmpty()) {
//                    journalDB.deleteNote(time, tagText);
//                    preContent = "";
//                } else {
//                    note.setDefinition(editText.getText().toString());
//                    journalDB.saveNote(note, false);//修改
//                    preContent = editText.getText().toString();
//                }
//            }
//            if (!preContent.isEmpty() && !editText.getText().toString().isEmpty()) {
//                certainButton.setText("编辑");//toast要break出去！！！
//                editText.setFocusable(false);
//                certainButton.requestFocus();
//            }
              if (editText.getText().toString().isEmpty()) {
                  journalDB.deleteNote(time, tagText);
                  preContent = "";
              } else {
                  note.setDefinition(editText.getText().toString());
                  journalDB.saveNote(note, false);//修改
                  preContent = editText.getText().toString();
              }
              certainButton.setText("编辑");//toast要break出去！！！
              editText.setFocusable(false);
              certainButton.requestFocus();
        } else {
            certainButton.setText("保存");
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {
        if ("编辑".equals(certainButton.getText().toString())) {
            Intent backIntent = new Intent();
            backIntent.putExtra("content", editText.getText().toString());
            setResult(RESULT_OK, backIntent);
        }
        finish();
    }
}
