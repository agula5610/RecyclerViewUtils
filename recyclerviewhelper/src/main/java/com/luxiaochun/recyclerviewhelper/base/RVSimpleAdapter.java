package com.luxiaochun.recyclerviewhelper.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.luxiaochun.recyclerviewhelper.cell.EmptyCell;
import com.luxiaochun.recyclerviewhelper.cell.ErrorCell;
import com.luxiaochun.recyclerviewhelper.cell.LoadMoreCell;
import com.luxiaochun.recyclerviewhelper.cell.LoadingCell;


/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-11 08:43
 */
public class RVSimpleAdapter extends RVBaseAdapter {

    private EmptyCell mEmptyCell;
    private ErrorCell mErrorCell;
    private LoadingCell mLoadingCell;
    private LoadMoreCell mLoadMoreCell;
    //LoadMore 是否已显示
    private boolean mIsShowLoadMore = false;
    private boolean mIsShowError = false;
    private boolean mIsShowLoading = false;
    private boolean mIsShowEmpty = false;

    public RVSimpleAdapter() {
        super();
        mEmptyCell = new EmptyCell(null);
        mErrorCell = new ErrorCell(null);
        mLoadingCell = new LoadingCell(null);
        mLoadMoreCell = new LoadMoreCell(null);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        //处理GridView 布局
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return (viewType == ErrorCell.DefaultResId || viewType == EmptyCell.DefaultResId || viewType == LoadingCell.DefaultResId
                            || viewType == LoadMoreCell.DefaultResId) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        // 处理StaggeredGridLayoutManager 显示这个Span
        int position = holder.getAdapterPosition();
        int viewType = getItemViewType(position);
        if (isStaggeredGridLayout(holder)) {
            if (viewType == ErrorCell.DefaultResId || viewType == EmptyCell.DefaultResId || viewType == LoadingCell.DefaultResId
                    || viewType == LoadMoreCell.DefaultResId) {

                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                //设置显示整个span
                params.setFullSpan(true);
            }
        }

    }

    /**
     * 判断是否是瀑布流布局
     *
     * @param holder
     * @return
     */
    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    /**
     * 显示LoadingView
     * <p>请求数据时调用，数据请求完毕时调用{@link #hideLoading }</p>
     */
    public void showLoading() {
        clear();
        mIsShowLoading = true;
        add(mLoadingCell);
    }

    /**
     * 列表Loading 状态显示的View，默认全屏高度
     *
     * @param loadingView
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
     * <p>当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showError}</p>
     */
    public void showError() {
        clear();
        mIsShowError = true;
        add(mErrorCell);
    }

    /**
     * 指定列表发生错误时显示的View，默认为全屏高度
     *
     * @param errorView
     * @see {@link #showError(View)}
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
     * 显示LoadMoreView
     * <p>当列表滑动到底部时，调用{@link #showLoadMore()} 提示加载更多，加载完数据，调用{@link #hideLoadMore()}
     * 隐藏LoadMoreView,显示列表数据。</p>
     */
    public void showLoadMore() {
        if (mData.contains(mLoadMoreCell)) {
            return;
        }
        checkNotContainSpecailCell();
        add(mLoadMoreCell);
        mIsShowLoadMore = true;
    }

    /**
     * 指定显示的LoadMore View
     *
     * @param loadMoreView
     */
    public void showLoadMore(View loadMoreView) {
        if (loadMoreView == null) {
            showLoadMore();
            return;
        }
        checkNotContainSpecailCell();
        //设置默认高度
        mLoadMoreCell.setCustomView(loadMoreView.getId());
        mIsShowLoadMore = true;
        add(mLoadMoreCell);
    }

    /**
     * 隐藏LoadMoreView
     * <p>调用{@link #showLoadMore()}之后，加载数据完成，调用{@link #hideLoadMore()}隐藏LoadMoreView</p>
     */
    public void hideLoadMore() {
        if (mData.contains(mLoadMoreCell)) {
            remove(mLoadMoreCell);
            mIsShowLoadMore = false;
        }
    }

    /**
     * LoadMore View 是否已经显示
     *
     * @return
     */
    public boolean isShowLoadMore() {
        return mIsShowLoadMore;
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
     * 检查列表是否已经包含了这4种Cell
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
        if (mData.contains(mLoadMoreCell)) {
            mData.remove(mLoadMoreCell);
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
        mIsShowLoadMore = false;
        super.clear();
    }
}
