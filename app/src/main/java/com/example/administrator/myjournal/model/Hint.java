package com.example.administrator.myjournal.model;

/**
 * Created by Administrator on 2016/5/30.
 */
public class Hint {

    private String tag;

    private String definition;

    public Hint() {

    }

    public Hint(String tag,String definition) {
        setTag(tag);
        setDefinition(definition);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getTag() {
        return tag;
    }

    public String getDefinition() {
        return definition;
    }
}
