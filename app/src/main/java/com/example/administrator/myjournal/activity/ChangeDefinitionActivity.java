package com.example.administrator.myjournal.activity;

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
import com.example.administrator.myjournal.util.MyApplication;

/**
 * Created by Administrator on 2016/6/17.
 */
public class ChangeDefinitionActivity extends BaseActivity {

    private TextView tagName;

    private TextView oldDefinition;

    private EditText newDefinition;

    private Button certainButton;

    private String definition;

    private JournalDB journalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_definition);

        newDefinition = (EditText) findViewById(R.id.change_definition);
        oldDefinition = (TextView) findViewById(R.id.old_definition);
        tagName = (TextView) findViewById(R.id.tag_name);
        certainButton = (Button) findViewById(R.id.certain_button);

        String tag = getIntent().getStringExtra("tagName");
        definition = getIntent().getStringExtra("tagDefinition");

        journalDB = JournalDB.getInstance(MyApplication.getContext());
        oldDefinition.setText(definition);
        tagName.setText(tag);
        certainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newDefinition.getText().length() == 0) {
                    Toast.makeText(ChangeDefinitionActivity.this, "请检查输入", Toast.LENGTH_SHORT).show();
                } else if (definition.equals(newDefinition.getText().toString())){
                    Toast.makeText(ChangeDefinitionActivity.this, "未修改定义", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangeDefinitionActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Hint hint = new Hint(tagName.getText().toString(), newDefinition.getText().toString());
                    journalDB.changeDefinition(hint);
                    Intent backIntent = new Intent();
                    backIntent.putExtra("content", newDefinition.getText().toString());
                    setResult(RESULT_OK, backIntent);
                    finish();
                }
            }
        });
    }
}
