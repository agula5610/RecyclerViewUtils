package com.luxiaochun.recyclerviewhelper.cell;


import com.luxiaochun.recyclerviewhelper.R;
import com.luxiaochun.recyclerviewhelper.base.RVAbsStateCell;
import com.luxiaochun.recyclerviewhelper.viewholder.ViewHolderRocket;


/**
 * ProjectName: Linger
 * PackageName: cn.com.luxiaochun.linger
 * Author: jun
 * Date: 2020-05-12 09:00
 */
public class PlaceHolderCell extends RVAbsStateCell {
    private int defaultResId = R.layout.rv_placeholder_layout;

    public PlaceHolderCell(Object o) {
        super(o);
    }

    @Override
    public void setCustomView(int resId) {
        defaultResId = resId;
    }

    @Override
    public void releaseResource() {

    }

    @Override
    public int getItemType() {
        return defaultResId;
    }

    @Override
    public void bindViewHolder(ViewHolderRocket holder, int position) {

    }
}
