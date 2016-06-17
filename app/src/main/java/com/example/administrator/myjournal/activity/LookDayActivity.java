package com.example.administrator.myjournal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.util.Export;
import com.example.administrator.myjournal.util.TodayHelper;

public class LookDayActivity extends AppCompatActivity {

    private TodayHelper helper;
    private TextView contentView;
    private String content;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.day_look_bar);
        toolbar.setTitle("一天");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        time = intent.getLongExtra("time", 0);
        helper = new TodayHelper(time);

        contentView = (TextView) findViewById(R.id.day_content);
        content = new Export().aDay(time);
        contentView.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.today, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.today_menu) {
            //导出当天的所有，用印象笔记，以日期命名。
            Intent share = new Export().exportMethod(time, content);
            try {
                startActivity(Intent.createChooser(share, "分享一下"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Can't find share component to share", Toast.LENGTH_SHORT).show();
            }
//            if (share != null) {
//                startActivity(Intent.createChooser(share, "分享一下"));
//            } else {
//                Toast.makeText(TodayActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
