package com.example.administrator.myjournal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.administrator.myjournal.util.ActivityCollector;


/**
 * Created by Administrator on 2016/5/30.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
