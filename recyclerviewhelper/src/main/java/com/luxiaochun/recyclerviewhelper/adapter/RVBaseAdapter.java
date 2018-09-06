package com.luxiaochun.recyclerviewhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.luxiaochun.recyclerviewhelper.base.RVCell;
import com.luxiaochun.recyclerviewhelper.viewholder.ViewHolderRocket;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-10 16:06
 */
public class RVBaseAdapter<C extends RVCell> extends RecyclerView.Adapter<ViewHolderRocket> {
    private static final String TAG = "RVBaseAdapter";
    protected List<C> mData;

    public RVBaseAdapter() {
        mData = new ArrayList<>();
    }

    public List<C> getData() {
        return mData;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    @Override
    public ViewHolderRocket onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderRocket(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderRocket holder, int position) {
        mData.get(position).bindViewHolder(holder, position);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolderRocket holder) {
        super.onViewDetachedFromWindow(holder);
        Log.e(TAG, "onViewDetachedFromWindow invoke...");
        //释放资源
        int position = holder.getAdapterPosition();
        //越界检查
        if (position < 0 || position >= mData.size()) {
            return;
        }
        mData.get(position).releaseResource();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * add one cell
     *
     * @param cell
     */
    public void add(C cell) {
        mData.add(cell);
        int index = mData.indexOf(cell);
        notifyItemInserted(index);
    }

    public void add(int index, C cell) {
        mData.add(index, cell);
        notifyItemInserted(index);
    }

    /**
     * remove a cell
     *
     * @param cell
     */
    public void remove(C cell) {
        int indexOfCell = mData.indexOf(cell);
        remove(indexOfCell);
    }

    public void remove(int index) {
        mData.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * @param start
     * @param count
     */
    public void remove(int start, int count) {
        if ((start + count) > mData.size()) {
            return;
        }
        mData.subList(start, start + count).clear();
        notifyItemRangeRemoved(start, count);
    }


    /**
     * add a cell list
     *
     * @param cells
     */
    public void addAll(List<C> cells) {
        if (cells == null || cells.size() == 0) {
            return;
        }
        Log.e(TAG, "addAll cell size:" + cells.size());
        mData.addAll(cells);
        notifyItemRangeInserted(mData.size() - cells.size(), mData.size());
    }

    public void addAll(int index, List<C> cells) {
        if (cells == null || cells.size() == 0) {
            return;
        }
        mData.addAll(index, cells);
        notifyItemRangeInserted(index, index + cells.size());
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
}
