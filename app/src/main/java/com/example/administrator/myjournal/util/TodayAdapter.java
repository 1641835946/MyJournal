package com.example.administrator.myjournal.util;

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
import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

    private JournalDB journalDB;
    public List<Note> mDatas;
    public static long loginTime = 0;//同一次的编辑在同一天

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public TodayAdapter() {

        if (CurrentTime.getTime() > loginTime)
            loginTime = CurrentTime.getTime();
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        mDatas = journalDB.loadNote(loginTime);
        if (mDatas == null ) {
            LogUtil.e("TodayAdapter", "mdatas is null");
            mDatas = new ArrayList<>();
            List<String> mHints = journalDB.loadHint();
            LogUtil.e("AllAdapter。看这里", mHints.toString());
            if (mHints != null) {
                // 有些地方依赖于当前的设计，有必要注释！！！
                // 这里是每天第一次打开程序，就要把日期存储。ok
                // 为了不必每次保存前都要判断是update还是insert，ok
                // 在每天第一次打开程序就先insert。ok
                // 且标签的数量增加时，应插入note数据 ok
                // 为了同步，防止如标签增删了todayactivity却没有改变，
                // 在todayactivity离开时应杀死。
                // 如果allactivity中包含今天的内容，那么也应该杀死。不含
                // 标签如果实现了拖拽功能，必须要记录顺序。
                for (int i = 0; i < mHints.size(); i++) {
                    Note saveNote = new Note(loginTime, mHints.get(i), "");
                    journalDB.saveNote(saveNote, true);
                }
                mDatas = journalDB.loadNote(loginTime);
            }
            LogUtil.e("TodayAdapter.看这里", mDatas.toString());
            journalDB.saveTime(loginTime);
            // 既然写在这里，todayActivity必须是第一个启动的Activity。
        }
        LogUtil.e("TodayAdapter.看这里", mDatas.toString()+"isnot null");

//        Note note1 = new Note();
//        note1.setTime(20150603);
//        note1.setDefinition("hello world");
//        note1.setTag("晚");
//        Note note2 = new Note();
//        note2.setTime(20150604);
//        note2.setDefinition("liang");
//        note2.setTag("晚");
//        Note note3 = new Note();
//        note3.setTime(20150605);
//        note3.setDefinition("jie");
//        note3.setTag("晚");
        //mDatas = new ArrayList<>() ;
        //忘了这句，就mDatas.add(str1):Caused by: java.lang.NullPointerException
//        mDatas.add(note1);
//        mDatas.add(note2);
//        mDatas.add(note3);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.today_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(mDatas.get(position).getTag());
        holder.content.setText(mDatas.get(position).getDefinition());
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    LogUtil.e("TodayAdapter", "onclick");
                }
            });
        }
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
            title = (TextView) view.findViewById(R.id.today_title);
            content = (TextView) view.findViewById(R.id.today_content);
        }
    }

    public void addData(int position, Note note) {
        mDatas.add(position, note);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}
