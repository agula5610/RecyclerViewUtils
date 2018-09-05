package com.luxiaochun.recyclerviewhelper.base;

/**
 * ProjectName: 2017_zhifa_standard
 * Author: jun
 * Date: 2018-01-11 08:53
 */
public abstract class RVAbsStateCell extends RVBaseCell<Object> {

    public RVAbsStateCell(Object o) {
        super(o);
    }

    /**
     * 设置自定义的布局
     *
     * @param resId
     */
    protected abstract void setCustomView(int resId);
}
