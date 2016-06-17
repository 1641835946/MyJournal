package com.example.administrator.myjournal.util;

import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class TodayHelper {
    private JournalDB journalDB = JournalDB.getInstance(MyApplication.getContext());
    public static long loginTime ;
    //同一次的编辑在同一天
    public List<Note> mDatas;
    public List<String> hintQueue;

    static {loginTime = CurrentTime.getTime();}//???确定是每次启动时加载一次吗？
    public TodayHelper() {
        journalDB.saveTime(loginTime);
        initData(loginTime);
    }

    public TodayHelper(long time) {
        initData(time);
    }

    private void initData(long time) {
        // 输入：某天
        // 输出：无（其实特殊的输出即非正常的输出的含义要要说明，可解耦好像是不知道这些才行？？？）
        // 作用：按照hint的排列顺序，初始化List<Note>（一天）
        // 不写这个，需要再一次看代码
        hintQueue = journalDB.loadHintWithString();
        //写成journalDB.loadHintWithString();找了好一会儿的错误。二分法很重要！！！不知道是前面出了纰漏。
        //还有一个原因是粗心吧，一改哪哪都有错。
        mDatas = new ArrayList<>();
        if (hintQueue != null) {
            LogUtil.e("TodayAdapter这里", "mDatas:"+mDatas.size());
            for (String hintStr : hintQueue) {
                Note noteQueue = journalDB.loadNote(time, hintStr);
                if (noteQueue == null) {
                    noteQueue = new Note(time, hintStr, "");
                    journalDB.saveNote(noteQueue, true);
                }
                mDatas.add(noteQueue);
            }
            LogUtil.e("TodayHelper:", mDatas.get(0).getDefinition());
        }
        LogUtil.e("TodayAdapter这里", "mDatas is null ");
    }
}
