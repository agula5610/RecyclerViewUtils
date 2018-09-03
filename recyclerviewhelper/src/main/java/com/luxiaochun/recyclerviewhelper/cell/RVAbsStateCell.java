package com.luxiaochun.recyclerviewhelper.cell;

import android.view.View;

import com.luxiaochun.recyclerviewhelper.base.RVBaseCell;


/**
 * ProjectName: 2017_zhifa_standard
 * Author: jun
 * Date: 2018-01-11 08:53
 */
public abstract class RVAbsStateCell extends RVBaseCell<Object> {
    protected View mView;

    public RVAbsStateCell(Object o) {
        super(o);
    }

    /**
     * 设置自定义的布局
     *
     * @param resId
     */
    protected abstract void setCustomView(int resId);


    @Override
    public void releaseResource() {
        if (mView != null) {
            mView = null;
        }
    }
}
