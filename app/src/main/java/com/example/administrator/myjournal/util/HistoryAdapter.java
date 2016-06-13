package com.example.administrator.myjournal.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private JournalDB journalDB;
    private List<Note> mDatas;

    public HistoryAdapter() {
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        mDatas = new ArrayList<>();
        List<Long> timeList;
        timeList = journalDB.loadTime();
        List<String> tagList;
        tagList = journalDB.loadHintWithString();
        if (timeList != null && tagList != null) {
            LogUtil.e("HistoryAdapter", "并不是null");
            LogUtil.e("HistoryAdapter", ""+timeList.size());
            LogUtil.e("timeList.0", ""+timeList.get(0));
            for (int i = 0; i < timeList.size() ; i++) {//不含今天。
                String noteStr = "";
                LogUtil.e("HistoryAdapter：打出来了吗？0", noteStr);//不知怎的，没有执行！！！
                for (int j = 0; j <tagList.size() - 1; j++) {
                    LogUtil.e("HistoryAdapter：打出来了吗？1", noteStr);//不知怎的，没有执行！！！
                    Note note = journalDB.loadNote(timeList.get(i),tagList.get(j));
                    noteStr = noteStr + "\n" + note.getTag() + ":" + "\n" + note.getDefinition();
                    LogUtil.e("HistoryAdapter：打出来了吗？", noteStr);//不知怎的，没有执行！！！
                }
                Note loadNote = new Note();
                long time = timeList.get(i);
                loadNote.setTag("" + time / 10000 + "." + time % 10000 / 100 + "." + time % 100);
                loadNote.setDefinition(noteStr);
                mDatas.add(loadNote);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.day_item, parent,
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
