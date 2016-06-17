package com.example.administrator.myjournal.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Note;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class Export {

    private Intent share;
    private JournalDB journalDB;
    private String extraText;
    private String title;
    //需要导出今天（time），标签（历史：tag），某天（time),所有
    public Export() {
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
    }
    public String aDay(long time){
        TodayHelper helper = new TodayHelper(time);
        List<Note> noteList = helper.mDatas;
        String day = "" + time;
        for (Note mNote : noteList) {
            if (mNote.getDefinition().isEmpty()) {
                day = day + "\n" + mNote.getTag() + ":\n无记录";
            } else {
                day = day + "\n" + mNote.getTag() + ":\n" +mNote.getDefinition();
            }
        }
        return day + "\n";
    }

    public String allDay() {
        List<Long> timeList = journalDB.loadTime();
        String allDay = "";
        if (timeList == null) {
            Toast.makeText(MyApplication.getContext(), "查找不到", Toast.LENGTH_SHORT).show();
        } else {
            for (long time : timeList) {
                allDay = allDay + aDay(time) + "\n";
            }
        }
        return allDay;
    }

    public String allTag(String tag) {
        String allTag = "";
        List<Long> timeList = journalDB.loadTime();
        if (timeList == null) {
            Toast.makeText(MyApplication.getContext(), "查找不到", Toast.LENGTH_SHORT).show();
        } else {
            for (long time : timeList) {
                Note mNote = journalDB.loadNote(time, tag);
                allTag = allTag + mNote.getTime() + ":\n" + mNote.getDefinition() + "\n\n";
            }
        }
        return allTag;
    }

    private void exportEvernote() {
        List<ResolveInfo> resInfo = MyApplication.getContext().getPackageManager().queryIntentActivities(share, 0);
        // 原代码是在Activity中，所以没有MyApplication.getContext()，
        // 而我虽然启动Intent是在Activity中，但这里。。。
        // 不一致的Context
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains("evernote") ||
                        info.activityInfo.name.toLowerCase().contains("evernote") ) {
                    share.putExtra(Intent.EXTRA_TEXT, extraText);
                    share.putExtra(Intent.EXTRA_SUBJECT, title);
//                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(myPath)) ); // Optional, just if you wanna share an image.
                    share.setPackage(info.activityInfo.packageName);
                    break;
                }
            }
            // MyApplication.getContext().startActivity(Intent.createChooser(share, "Select"));
            // android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity
            // context requires the FLAG_ACTIVITY_NEW_TASK flag.
        }
    }

    public Intent exportMethod(String extraText) {
        title = "到" + TodayHelper.loginTime + "为止的所有日记";
        this.extraText = extraText;
        //extraText = allDay();
        exportEvernote();
        return share;
    }

    public Intent exportMethod(long time, String extraText) {
        title = "" + time;
        this.extraText = extraText;
        // extraText = aDay(time);
        exportEvernote();
        return share;
    }

    public Intent exportMethod(long time) {
        title = "" + time;
        extraText = aDay(time);
        exportEvernote();
        return share;
    }

    public Intent exportMethod(String tag, String extraText) {
        title = "到" + TodayHelper.loginTime + "为止的所有" + tag + "记录";
        this.extraText = extraText;
        //extraText = allTag(tag);
        exportEvernote();
        return share;
    }
}
