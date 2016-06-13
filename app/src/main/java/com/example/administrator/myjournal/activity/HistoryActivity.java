package com.example.administrator.myjournal.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.util.HistoryAdapter;
import com.example.administrator.myjournal.util.LogUtil;
import com.example.administrator.myjournal.util.MyApplication;
import com.example.administrator.myjournal.util.TodayHelper;

import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity {

    //private JournalDB journalDB;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private int year;
    private int month;
    private int day;
    private Calendar cal;
    private JournalDB journalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.histor_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.all_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new HistoryAdapter());
        LogUtil.e("HistoryActivity", "执行了");
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
                            Intent intent = new Intent(HistoryActivity.this, DayLookActivity.class);
                            intent.putExtra("time", time);
                            startActivity(intent);
                        } else {
                            Toast.makeText(HistoryActivity.this, "记录不存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

}
