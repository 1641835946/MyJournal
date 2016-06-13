package com.example.administrator.myjournal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.TodayHelper;

import java.util.List;

public class DayLookActivity extends AppCompatActivity {

    private TodayHelper helper;
    private TextView title;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_look);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        long data = intent.getLongExtra("time", 0);
        helper = new TodayHelper(data);

        title = (TextView) findViewById(R.id.day_title);
        content = (EditText) findViewById(R.id.day_content);

        title.setText(""+data);
        String contentStr = "";
        List<Note> list = helper.mDatas;
        for(Note note : list) {
            contentStr = contentStr + "\n" + note.getTag() + ":" + "\n" + "  " + note.getDefinition();
        }
        content.setText(contentStr);
    }

}
