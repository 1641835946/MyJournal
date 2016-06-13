package com.example.administrator.myjournal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Hint;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.ActivityCollector;
import com.example.administrator.myjournal.util.LogUtil;
import com.example.administrator.myjournal.util.MyApplication;
import com.example.administrator.myjournal.util.TodayAdapter;
import com.example.administrator.myjournal.util.TodayHelper;

/**
 * Created by Administrator on 2016/6/2.
 */
public class AddTagActivity extends BaseActivity {

    private JournalDB journalDB;
    private EditText tag;
    private EditText definition;
    private Button certainButton;
    private String tagText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_tag_layout);
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        //context 不一样会怎样，报错？？？
        tag = (EditText) findViewById(R.id.add_tag);
        definition = (EditText) findViewById(R.id.add_definition);
        certainButton = (Button) findViewById(R.id.add_tag_button);
//        tagText = tag.getText().toString();
//        if (tagText != null)
//            LogUtil.e("AddTagActivity", "is not null");
        certainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagText = tag.getText().toString();
                if (!tagText.isEmpty() && !definition.getText().toString().isEmpty()) {
                    Hint hint = new Hint();
                    hint.setDefinition(definition.getText().toString());
                    hint.setTag(tagText);
                    journalDB.saveHint(hint);
                    TodayHelper todayHelper = new TodayHelper();
                    Note addNote = new Note(todayHelper.loginTime, tagText, "");
                    journalDB.saveNote(addNote, true);
                    LogUtil.e("AddTagActivity", "saveHint");
                    Intent intent = new Intent();
                    intent.putExtra("backHint", tag.getText().toString());
                    intent.putExtra("backDefinition", definition.getText().toString());
                    setResult(RESULT_OK, intent);
                    ActivityCollector.removeActivity(AddTagActivity.this);
                    AddTagActivity.this.finish();
                    LogUtil.e("AddTagActivity", "removeActivity");
                } else {
                    Toast.makeText(AddTagActivity.this, "请检查输入",
                            Toast.LENGTH_SHORT).show();
                    LogUtil.e("AddTagActivity", "" + tag.getText());
                    LogUtil.e("AddTagActivity", "" + tag.getText().toString());
                }
            }
        });
    }
}
