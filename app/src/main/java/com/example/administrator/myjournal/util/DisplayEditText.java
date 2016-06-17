package com.example.administrator.myjournal.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/13.
 */
public class DisplayEditText {

//    private InsertPicEditText editText;
//    private Context context;
//
//    public DisplayEditText(Context context, InsertPicEditText editText) {
//        this.context = context;
//        this.editText = editText;
//    }
//
//    //open failed: EACCES (Permission denied)
//    public void getImagePath(String text) {
//        LogUtil.e("DisplayEditText", "执行了000");
//        LogUtil.e("DisplayEditText", text);
//        if (!TextUtils.isEmpty(text)) {
//            String[] array = text.split("\\|");
//            if (array != null) {
//                int i = 0;
//                while (i < array.length) {
//                    if (!array[i].isEmpty()) {
//                        if (array[i].length() > 3 && (array[i].substring(0, 3)).equals("img")) {
//                            LogUtil.e("DisplayEditText", "执行了111");
//                            displayImage(array[i].substring(3));
//                            LogUtil.e("DisplayEditText", "执行了222");
//                        } else editText.append(array[i]);
//                    }
//                    i++;
//                }
//            }
//        }
//    }
//
//    public void displayImage(String imagePath){
//        if(imagePath!=null){
//            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
//            SpannableString mSpan1 = editText.displayBitmap(bitmap, imagePath);
//            if (mSpan1 != null) {
//                int start = editText.getSelectionStart();
//                Editable et = editText.getText();
//                et.insert(start, mSpan1);
//                editText.setText(et);
//                editText.setSelection(start + mSpan1.length());
//                LogUtil.e("DisplayEditText", "执行了333");
//            }
//        }else{
//            Toast.makeText(context, "获取图片失败", Toast.LENGTH_SHORT).show();
//        }
//    }
}
