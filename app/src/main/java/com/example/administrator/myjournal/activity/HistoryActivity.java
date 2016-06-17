package com.example.administrator.myjournal.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.util.Export;
import com.example.administrator.myjournal.util.LogUtil;
import com.example.administrator.myjournal.util.MyApplication;
import com.example.administrator.myjournal.util.TodayHelper;

import java.util.Calendar;

public class HistoryActivity extends BaseActivity {

    private int year;
    private int month;
    private int day;
    private Calendar cal;
    private JournalDB journalDB;
    private TextView contentView;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.histor_bar);
        toolbar.setTitle("所有");// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(toolbar);

        /* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 */
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);

        contentView = (TextView) findViewById(R.id.history_content);
        content = new Export().allDay();
        contentView.setText(content);

        cal = Calendar.getInstance();
        journalDB = JournalDB.getInstance(MyApplication.getContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.e("HistoryActivity:fab", "执行了");
                new DatePickerDialog(HistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        HistoryActivity.this.year = year;
                        month = monthOfYear + 1;
                        day = dayOfMonth;
                        long time = HistoryActivity.this.year * 10000 + month * 100 + day;
                        if (journalDB.hasTime(time) && time != TodayHelper.loginTime) {
                            Intent intent = new Intent(HistoryActivity.this, LookDayActivity.class);
                            intent.putExtra("time", time);
                            startActivity(intent);
                        } else {
                            Toast.makeText(HistoryActivity.this, "记录不存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) - 1).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.today, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.today_menu) {
            Intent share = new Export().exportMethod(content);
            try {
                startActivity(Intent.createChooser(share, "分享一下"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Can't find share component to share", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
