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
 * Created by Administrator on 2016/6/3.
 */
public class TagLookingAdapter extends RecyclerView.Adapter<TagLookingAdapter.ViewHolder> {
    private JournalDB journalDB;
    public List<Note> mDatas ;

    public TagLookingAdapter(String title) {
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        if (journalDB.loadNote(title) == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = journalDB.loadNote(title);
            mDatas.remove(mDatas.size()-1);//不含今天
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.tag_looking_item, parent,
                false));
        return holder;
    }

    @Override


    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setText(String.valueOf(mDatas.get(position).getTime()));//
        holder.content.setText(mDatas.get(position).getDefinition().toString());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView content;

        public ViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.look_tag_time);
            content = (TextView) view.findViewById(R.id.look_tag_content);
        }
    }
}
