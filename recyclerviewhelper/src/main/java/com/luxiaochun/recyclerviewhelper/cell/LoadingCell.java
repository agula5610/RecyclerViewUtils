package com.luxiaochun.recyclerviewhelper.cell;


import com.luxiaochun.recyclerviewhelper.R;
import com.luxiaochun.recyclerviewhelper.base.RVAbsStateCell;
import com.luxiaochun.recyclerviewhelper.viewholder.RVBaseViewHolder;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-10 16:06
 */
public class LoadingCell extends RVAbsStateCell {
    private static int DefaultResId = R.layout.rv_loading_layout;

    public LoadingCell(Object o) {
        super(o);
    }

    @Override
    public void setCustomView(int resId) {
        DefaultResId = resId;
    }


    @Override
    public void releaseResource() {

    }

    @Override
    public int getItemType() {
        return DefaultResId;
    }

    @Override
    public void bindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
