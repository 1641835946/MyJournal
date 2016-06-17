package com.example.administrator.myjournal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.util.Export;

/**
 * Created by Administrator on 2016/6/3.
 */
public class TagLookingActivity extends BaseActivity {

    private TextView titleView;
    private String title;
    private TextView contentView;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_looking_layout);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        titleView = (TextView) findViewById(R.id.look_tag_title);
        titleView.setText(title);
        content = new Export().allTag(title);
        contentView = (TextView) findViewById(R.id.look_tag_content);
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
            Intent share = new Export().exportMethod(title, content);
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
