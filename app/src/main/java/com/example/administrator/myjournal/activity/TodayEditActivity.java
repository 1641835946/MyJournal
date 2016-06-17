package com.example.administrator.myjournal.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class TodayEditActivity extends BaseActivity implements View.OnClickListener {
//如何复用代码:类，接口？？？
    public static final int CHOOSE_PHOTO = 1;
    private TextView tagView;
    private TextView hintView;
    //private InsertPicEditText editText;
    private EditText editText;
    private Button certainButton;
    private JournalDB journalDB;
    private Note note;
    private String tagText;
    private long time;
    private DisplayEditText displayEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_edit_layout);
        init();
    }

    private void init() {
        tagView = (TextView) findViewById(R.id.today_edit_tag);
        hintView = (TextView) findViewById(R.id.today_edit_hint);
        //editText = (InsertPicEditText) findViewById(R.id.today_edit_content);
        editText = (EditText) findViewById(R.id.today_edit_content);
        certainButton = (Button) findViewById(R.id.today_edit_certain);

        time = CurrentTime.getTime();
        Intent receiveIntent = getIntent();
        tagText = receiveIntent.getStringExtra("tag");
        tagView.setText(tagText);
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        Hint hint = journalDB.loadHint(tagText);
        hintView.setText(hint.getDefinition());
        note = journalDB.loadNote(time, tagText);
        LogUtil.e("todayEditActivity", "time:"+time+"/tag:"+tagText);
        LogUtil.e("TodayEditActivity", note.toString());
        //if (note.getDefinition() == "") {
        editText.setText(note.getDefinition());
        //}
//        displayEdit = new DisplayEditText(TodayEditActivity.this, editText);
//        displayEdit.getImagePath(note.getDefinition());
        certainButton.setOnClickListener(this);
        editText.setFocusable(false);
        certainButton.requestFocus();
    }

    @Override
    public void onClick(View v) {
        if ("保存".equals(certainButton.getText().toString())) {
//            if (preContent.isEmpty()) {
//                if (editText.getText().toString().isEmpty()) {
//                    Toast.makeText(TodayEditActivity.this, "未保存，不能保存空的内容", Toast.LENGTH_SHORT).show();
//                } else {
//                    note.setTime(time);
//                    note.setTag(tagText);
//                    note.setDefinition(editText.getText().toString());
//                    journalDB.saveNote(note, true);//插入
//                    preContent = editText.getText().toString();
//                }
//            } else {
//                if (editText.getText().toString().isEmpty()) {
//                    journalDB.deleteNote(time, tagText);
//                    preContent = "";
//                } else {
//                    note.setDefinition(editText.getText().toString());
//                    journalDB.saveNote(note, false);//修改
//                    preContent = editText.getText().toString();
//                }
//            }
//            if (!preContent.isEmpty() && !editText.getText().toString().isEmpty()) {
//                certainButton.setText("编辑");//toast要break出去！！！
//                editText.setFocusable(false);
//                certainButton.requestFocus();
//            }
              if (editText.getText().toString().isEmpty()) {
                  journalDB.deleteNote(time, tagText);
              } else {
                  note.setDefinition(editText.getText().toString());
                  journalDB.saveNote(note, false);//修改
              }
              certainButton.setText("编辑");//toast要break出去！！！
              editText.setFocusable(false);
              certainButton.requestFocus();
        } else {
            certainButton.setText("保存");
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }
    }


    @Override
    public void onBackPressed() {
        if ("编辑".equals(certainButton.getText().toString())) {
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
////hello world，git
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
