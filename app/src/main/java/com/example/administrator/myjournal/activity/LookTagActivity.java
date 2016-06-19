package com.example.administrator.myjournal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.Export;
import com.example.administrator.myjournal.util.LogUtil;
import com.example.administrator.myjournal.util.TodayHelper;

/**
 * Created by Administrator on 2016/6/3.
 */
public class LookTagActivity extends BaseActivity {

    private String tag;
    private TextView contentView;
    private TextView definitionView;
    private String content;
    private String definition;
    private TextView title;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_tag);
        //java.lang.IllegalStateException: ScrollView can host only one direct child

        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        definition = intent.getStringExtra("definition");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tag_look_bar);
        setSupportActionBar(toolbar);
        definitionView = (TextView)findViewById(R.id.look_tag_definition);
        definitionView.setText(definition);

        content = new Export().allTag(tag);
        contentView = (TextView) findViewById(R.id.look_tag_content);
        contentView.setText(content);

        title = (TextView) findViewById(R.id.title);
        title.setText(tag);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.look_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.export_menu) {
            Intent share = new Export().exportMethod(tag, content);
            try {
                startActivity(Intent.createChooser(share, "分享一下"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Can't find share component to share", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            Intent change = new Intent(LookTagActivity.this, ChangeDefinitionActivity.class);
            change.putExtra("tagName", tag);
            change.putExtra("tagDefinition", definition);
            startActivityForResult(change, 1);//最好显示定义，返回新的定义
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String text = data.getStringExtra("content");
                    LogUtil.e("LookTagActivity", text);
                    definitionView.setText(text);
                }
                break;
            default:
        }
    }
}
