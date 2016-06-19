package com.example.administrator.myjournal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Hint;
import com.example.administrator.myjournal.model.Note;
import com.example.administrator.myjournal.util.CurrentTime;
import com.example.administrator.myjournal.util.DisplayEditText;
import com.example.administrator.myjournal.util.LogUtil;
import com.example.administrator.myjournal.util.MyApplication;

/**
 * Created by Administrator on 2016/6/3.
 */
public class EditTodayActivity extends BaseActivity {
//如何复用代码:类，接口？？？
    public static final int CHOOSE_PHOTO = 1;
    private TextView hintView;
    //private InsertPicEditText editText;
    private EditText editText;
    
    private JournalDB journalDB;
    private Note note;
    private String tagText;
    private long time;
    //private DisplayEditText displayEdit;
    private InputMethodManager inputManager;
    private Button completeBack;
    private boolean writing = false;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_today);
        init();
    }

    private void init() {
        hintView = (TextView) findViewById(R.id.today_edit_hint);
        editText = (EditText) findViewById(R.id.today_edit_content);

        time = CurrentTime.getTime();
        Intent receiveIntent = getIntent();
        tagText = receiveIntent.getStringExtra("tag");

        Toolbar toolbar = (Toolbar) findViewById(R.id.today_edit_bar);
        setSupportActionBar(toolbar);
        // android:navigationIcon="@drawable/previous"
        // android:navigationContentDescription="@string/Back"
        // 无作用，要求21以上
//        toolbar.setNavigationIcon(R.drawable.previous);
//        toolbar.setNavigationContentDescription(getResources().getString(R.string.Back));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        // 图标太大
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(tagText);
        completeBack = (Button) findViewById(R.id.back);
        completeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (writing) {
                    completeBack.setBackgroundResource(R.drawable.previous);
                    writing = false;
                    inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//                    if (editText.getText().toString().isEmpty()) {
//                        journalDB.saveNote(note, false);
//                    } else {
                        note.setDefinition(editText.getText().toString());
                        journalDB.saveNote(note, false);//修改
//                    }
//                    editText.clearFocus();
                    editText.setFocusable(false);
//                    completeBack.requestFocus();
                    fab.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }
            }
        });

        journalDB = JournalDB.getInstance(MyApplication.getContext());
        Hint hint = journalDB.loadHint(tagText);
        hintView.setText(hint.getDefinition());
        note = journalDB.loadNote(time, tagText);
        editText.setText(note.getDefinition());

        inputManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                inputManager.showSoftInput(editText, 0);
                view.setVisibility(View.GONE);
                writing = true;
                completeBack.setBackgroundResource(R.drawable.ok);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.export_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        View view = (View) findViewById(id);
//        //if (view.)
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if ("保存".equals(certainButton.getText().toString())) {
////            if (preContent.isEmpty()) {
////                if (editText.getText().toString().isEmpty()) {
////                    Toast.makeText(EditTodayActivity.this, "未保存，不能保存空的内容", Toast.LENGTH_SHORT).show();
////                } else {
////                    note.setTime(time);
////                    note.setTag(tagText);
////                    note.setDefinition(editText.getText().toString());
////                    journalDB.saveNote(note, true);//插入
////                    preContent = editText.getText().toString();
////                }
////            } else {
////                if (editText.getText().toString().isEmpty()) {
////                    journalDB.deleteNote(time, tagText);
////                    preContent = "";
////                } else {
////                    note.setDefinition(editText.getText().toString());
////                    journalDB.saveNote(note, false);//修改
////                    preContent = editText.getText().toString();
////                }
////            }
////            if (!preContent.isEmpty() && !editText.getText().toString().isEmpty()) {
////                certainButton.setText("编辑");//toast要break出去！！！
////                editText.setFocusable(false);
////                certainButton.requestFocus();
////            }
//              if (editText.getText().toString().isEmpty()) {
//                  journalDB.deleteNote(time, tagText);
//              } else {
//                  note.setDefinition(editText.getText().toString());
//                  journalDB.saveNote(note, false);//修改
//              }
//              certainButton.setText("编辑");//toast要break出去！！！
//              editText.setFocusable(false);
//              certainButton.requestFocus();
//        } else {
//            certainButton.setText("保存");
//            editText.setFocusable(true);
//            editText.setFocusableInTouchMode(true);
//            editText.requestFocus();
//            inputManager = (InputMethodManager) this
//                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.showSoftInput(editText, 0);
//
//        }
//    }

    @Override
    public void onBackPressed() {
        if (!writing) {
            Intent backIntent = new Intent();
            backIntent.putExtra("content", editText.getText().toString());
            setResult(RESULT_OK, backIntent);
        }
        finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.day_tag_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        switch (id) {
//            case R.id.day_tag_insert:
//                Intent addPhotoIntent = new Intent("android.intent.action.GET_CONTENT");
//                addPhotoIntent.setType("image/*");
//                startActivityForResult(addPhotoIntent, CHOOSE_PHOTO);
//                break;
//            case R.id.day_tag_photo:
//
//                break;
//            default:
//        }
//        return true;
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CHOOSE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        handleImageOnKitKat(data);
//                    } else {
//                        handleImageBeforeKitKat(data);
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data){
//        String imagePath=null;
//        Uri uri=data.getData();
//        if(DocumentsContract.isDocumentUri(this, uri)){
//            String docId=DocumentsContract.getDocumentId(uri);
//            if("com.android.providers.media.documents".equals(uri.getAuthority())){
//                String id=docId.split(":")[1];
//                String selection= MediaStore.Images.Media._ID+"="+id;
//                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
//            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
//                Uri contentUri= ContentUris.withAppendedId(Uri.parse("cintent://downloads/public_downloads"), Long.valueOf(docId));
//                imagePath=getImagePath(contentUri,null);
//            }
//        }else if("content".equalsIgnoreCase(uri.getScheme())){
//            imagePath=getImagePath(uri,null);
//        }
//        displayEdit.displayImage(imagePath);
//    }
//
//    private void handleImageBeforeKitKat(Intent data){
//        Uri uri=data.getData();
//        String imagePath=getImagePath(uri, null);
//        displayEdit.displayImage(imagePath);
//    }
//
//    private String getImagePath(Uri uri,String selection){
//        String path=null;
//        Cursor cursor=getContentResolver().query(uri, null,selection,null,null);
//        if(cursor!=null){
//            if(cursor.moveToFirst()){
//                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                LogUtil.e("TodayEditActivity", "getImagePath:"+path);
//            }
//            cursor.close();
//        }
//        LogUtil.e("TodayEditActivity", path);
//        return path;
//    }

}
