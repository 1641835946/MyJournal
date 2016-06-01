package com.example.administrator.myjournal.model;

import com.example.administrator.myjournal.util.CurrentTime;

/**
 * Created by Administrator on 2016/5/30.
 */
public class Note {
    private long time;
    private String tag;
    private String content;

    public Note() {

    }

    public Note(String tag, String content) {
        time = CurrentTime.getTime();
        setTag(tag);
        setContent(content);
    }

    public Note(long time, String tag, String content) {
        setTime(time);
        setTag(tag);
        setContent(content);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }

    public long getTime() {
        return time;
    }
}
