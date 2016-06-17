package com.example.administrator.myjournal.util;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myjournal.R;
import com.example.administrator.myjournal.db.JournalDB;
import com.example.administrator.myjournal.helper.ItemTouchHelperAdapter;
import com.example.administrator.myjournal.helper.ItemTouchHelperViewHolder;
import com.example.administrator.myjournal.model.Hint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private JournalDB journalDB;
    public List<Hint> mDatas;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
//        void onItemLongClick(View view , int position);
    }
//
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public TagAdapter() {
        journalDB = JournalDB.getInstance(MyApplication.getContext());
        if (journalDB.loadHint() == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = journalDB.loadHint();
        }
//        String str1 = "hello";
//        mDatas = new ArrayList<String>() ;//忘了这句，就mDatas.add(str1):Caused by: java.lang.NullPointerException
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_tag, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        if (mDatas.isEmpty()) {
//            holder.title.setVisibility(View.GONE);
//        } else {
        holder.title.setText(mDatas.get(position).getTag());
        holder.content.setText(mDatas.get(position).getDefinition());
//        }
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos = holder.getLayoutPosition();
//                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
//                    return false;
//                }
//            });
//
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        TextView title;
        TextView content;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tag_title);
            content = (TextView) view.findViewById(R.id.tag_content);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public void addData(Hint newTag) {
        mDatas.add(mDatas.size(), newTag);
        notifyItemInserted(mDatas.size());
    }
//
//    public void removeData(int position) {
//        mDatas.remove(position);
//        notifyItemRemoved(position);
//    }

    @Override
    public void onItemDismiss(int position) {
        journalDB.deleteTag(mDatas.get(position).getTag());
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        LogUtil.e("TagAdapter", fromPosition+"");
        LogUtil.e("TagAdapter", toPosition+"");
        journalDB.changeHint(mDatas.get(fromPosition), mDatas.get(toPosition));
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDatas, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDatas, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


}
