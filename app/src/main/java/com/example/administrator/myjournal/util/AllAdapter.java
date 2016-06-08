package com.example.administrator.myjournal.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Hint;
import com.example.administrator.myjournal.model.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2016/6/6.
 */
public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {

    private JournalDB journalDB;
    private List<Note> mDatas;

    public AllAdapter() {
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        mDatas = new ArrayList<>();
        List<Long> timeList = new ArrayList<>();
        timeList = journalDB.loadTime();
        List<String> tagList = new ArrayList<>();
        tagList = journalDB.loadHint();
        if (timeList != null && tagList != null) {
            for (int i = 0; i < timeList.size()-1; i++) {//不含今天。
                String noteStr = null;
                for (int j = 0; j <tagList.size(); j++) {
                    Note note = journalDB.loadNote(timeList.get(i),tagList.get(j));
                    noteStr = "\n" + note.getTag() + ":" + "\n" + note.getDefinition();
                }
                Note loadNote = new Note();
                long time = timeList.get(i);
                loadNote.setTag("" + time/10000 + time%10000/100 + time%1000000);
                loadNote.setDefinition(noteStr);
                mDatas.add(loadNote);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.all_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(mDatas.get(position).getTag());
        holder.content.setText(mDatas.get(position).getDefinition());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.all_time);
            content = (TextView) view.findViewById(R.id.all_content);
        }
    }
}
