package com.luxiaochun.recyclerviewhelper.adapter;


import com.luxiaochun.recyclerviewhelper.cell.PlaceHolderCell;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-11 08:43
 */
public class RVAdapter extends RVBaseAdapter {

    private PlaceHolderCell mPlaceHolderCell;
    //LoadMore 是否已显示
    private boolean mIsShowPlaceHolder = false;

    public RVAdapter() {
        super();
        mPlaceHolderCell = new PlaceHolderCell(null);
    }

    public void showPlaceHolder() {
        clear();
        mIsShowPlaceHolder = true;
        add(mPlaceHolderCell);
    }

    public void showPlaceHolder(int errorViewId) {
        clear();
        mIsShowPlaceHolder = true;
        mPlaceHolderCell.setCustomView(errorViewId);
        add(mPlaceHolderCell);
    }

    public void removePlaceHolder() {
        if (mData.contains(mPlaceHolderCell)) {
            remove(mPlaceHolderCell);
            mIsShowPlaceHolder = false;
        }
    }

    public boolean isShowPlaceHolder() {
        return mIsShowPlaceHolder;
    }

    /**
     * 清空所有状态
     */
    @Override
    public void clear() {
        super.clear();
    }
}
