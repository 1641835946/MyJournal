package com.example.administrator.myjournal.model;

import com.example.administrator.myjournal.util.CurrentTime;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class Note extends Hint{
    //有两重用处，一是保存标签和提示，二是保存标签及日记内容
    private long time;
//    private String tag;
//    private String content;

    public Note() {}

    public Note(long time, String tag, String content) {
        super(tag, content);
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
