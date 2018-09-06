package com.luxiaochun.recyclerviewhelper.adapter;

import android.view.View;

import com.luxiaochun.recyclerviewhelper.cell.EmptyCell;
import com.luxiaochun.recyclerviewhelper.cell.ErrorCell;
import com.luxiaochun.recyclerviewhelper.cell.LoadingCell;


/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-11 08:43
 */
public class RVAdapter extends RVBaseAdapter {

    private EmptyCell mEmptyCell;
    private ErrorCell mErrorCell;
    private LoadingCell mLoadingCell;
    //LoadMore 是否已显示
    private boolean mIsShowError = false;
    private boolean mIsShowLoading = false;
    private boolean mIsShowEmpty = false;

    public RVAdapter() {
        super();
        mEmptyCell = new EmptyCell(null);
        mErrorCell = new ErrorCell(null);
        mLoadingCell = new LoadingCell(null);
    }

    /**
     * 显示LoadingView
     * 请求数据时调用，数据请求完毕时调用{@link #hideLoading }
     */
    public void showLoading() {
        clear();
        mIsShowLoading = true;
        add(mLoadingCell);
    }

    /**
     * 列表Loading 状态显示的View，默认全屏高度
     *
     * @param loadingView 自定义的加载视图
     */
    public void showLoading(View loadingView) {
        if (loadingView == null) {
            showLoading();
            return;
        }
        clear();
        mIsShowLoading = true;
        mLoadingCell.setCustomView(loadingView.getId());
        add(mLoadingCell);
    }

    /**
     * hide Loading view
     */
    public void hideLoading() {
        if (mData.contains(mLoadingCell)) {
            mData.remove(mLoadingCell);
            mIsShowLoading = false;
        }
    }

    public boolean isShowLoading() {
        return mIsShowLoading;
    }

    /**
     * 显示错误提示View
     * 当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showError}
     */
    public void showError() {
        clear();
        mIsShowError = true;
        add(mErrorCell);
    }

    /**
     * 指定列表发生错误时显示的View，默认为全屏高度
     *
     * @param errorView  发生错误时显示的视图
     */
    public void showError(View errorView) {
        if (errorView == null) {
            showError();
            return;
        }
        clear();
        mIsShowError = true;
        mErrorCell.setCustomView(errorView.getId());
        add(mErrorCell);
    }

    /**
     * 隐藏错误提示
     */
    public void hideErorr() {
        if (mData.contains(mErrorCell)) {
            remove(mErrorCell);
            mIsShowError = false;
        }
    }

    public boolean isShowError() {
        return mIsShowError;
    }

    /**
     * 显示空view
     * <p>当页面没有数据的时候，调用{@link #showEmpty()}显示空View，给用户提示</p>
     */
    public void showEmpty() {
        clear();
        mIsShowEmpty = true;
        add(mEmptyCell);
    }

    /**
     * 显示指定的空状态View,默认显示屏幕高度
     *
     * @param emptyView
     * @see {@link #showEmpty(View)}
     */
    public void showEmpty(View emptyView) {
        if (emptyView == null) {
            showEmpty();
            return;
        }
        clear();
        mIsShowEmpty = true;
        mEmptyCell.setCustomView(emptyView.getId());
        add(mEmptyCell);
    }

    /**
     * 隐藏空View
     */
    public void hideEmpty() {
        if (mData.contains(mEmptyCell)) {
            remove(mEmptyCell);
            mIsShowEmpty = false;
        }
    }

    public boolean isShowEmpty() {
        return mIsShowEmpty;
    }

    /**
     * 检查列表是否已经包含了这3种Cell
     */
    private void checkNotContainSpecailCell() {
        if (mData.contains(mEmptyCell)) {
            mData.remove(mEmptyCell);
        }
        if (mData.contains(mErrorCell)) {
            mData.remove(mErrorCell);
        }
        if (mData.contains(mLoadingCell)) {
            mData.remove(mLoadingCell);
        }
    }

    /**
     * 清空所有状态
     */
    @Override
    public void clear() {
        mIsShowError = false;
        mIsShowLoading = false;
        mIsShowEmpty = false;
        super.clear();
    }
}
