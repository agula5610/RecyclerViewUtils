package com.luxiaochun.recyclerviewhelper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.luxiaochun.recyclerviewhelper.R;
import com.luxiaochun.recyclerviewhelper.base.Cell;
import com.luxiaochun.recyclerviewhelper.base.RVSimpleAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-10 16:06
 */
public abstract class RvBaseFragment<T> extends Fragment {
    public static final String TAG = "RvBaseFragment";
    private FrameLayout titleLayout;
    protected RecyclerView mRecyclerView;
    protected RVSimpleAdapter mBaseAdapter;
    protected SmartRefreshLayout refreshLayout;
    /**
     * RecyclerView 最后可见Item在Adapter中的位置
     */
    private int mLastVisiblePosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rv_base_fragment_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleLayout = view.findViewById(R.id.title_layout);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.base_fragment_rv);
        mRecyclerView.setLayoutManager(initLayoutManger());

        mBaseAdapter = initAdapter();
        mRecyclerView.setAdapter(mBaseAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000);//传入false表示加载失败
            }
        });
        refreshLayout.setRefreshHeader(new ClassicsHeader(this.getActivity()));
//设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(this.getActivity()));
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                setRefreshing(true);
//                onPullRefresh();
//            }
//        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    mLastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    mLastVisiblePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    mLastVisiblePosition = findMax(lastPositions);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && canShowLoadMore()) {
                    //计算拇指滚动距离。大于0表示在拖拽
                    if (recyclerView.computeVerticalScrollOffset() > 0) {
                        boolean isBottom = false;
                        isBottom = !recyclerView.canScrollVertically(1);
                        if (isBottom) {
                            // 说明滚动到底部,触发加载更多
                            showLoadMore();
                            onLoadMore();
                        }
                    }
                }
            }
        });
        onRecyclerViewInitialized();
    }

    /**
     * 设置头布局，不在列表中
     *
     * @param view
     */
    public void addTitleView(View view) {
        if (view == null) {
            return;
        }
        titleLayout.addView(view);
    }

    /**
     * 判断是否可以显示LoadMore
     *
     * @return
     */
    private boolean canShowLoadMore() {
        if (mBaseAdapter.isShowEmpty() || mBaseAdapter.isShowLoadMore() || mBaseAdapter.isShowError() || mBaseAdapter.isShowLoading()) {
            Log.i(TAG, "can not show loadMore");
            return false;
        }
        return true;
    }

    /**
     * hide load more progress
     */
    public void hideLoadMore() {
        if (mBaseAdapter != null) {
            mBaseAdapter.hideLoadMore();
        }
    }

    /**
     * show load more progress
     */
    private void showLoadMore() {
        View loadMoreView = customLoadMoreView();
        if (loadMoreView == null) {
            mBaseAdapter.showLoadMore();
        } else {
            mBaseAdapter.showLoadMore(loadMoreView);
        }

    }

    protected View customLoadMoreView() {
        //如果需要自定义LoadMore View,子类实现这个方法
        return null;
    }

    /**
     * 获取组数最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 子类可以自己指定Adapter,如果不指定默认RVSimpleAdapter
     *
     * @return
     */
    protected RVSimpleAdapter initAdapter() {
        return new RVSimpleAdapter();
    }

    /**
     * 子类自己指定RecyclerView的LayoutManager,如果不指定，默认为LinearLayoutManager,VERTICAL 方向
     *
     * @return
     */
    protected RecyclerView.LayoutManager initLayoutManger() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    /**
     * RecyclerView 初始化完毕，可以在这个方法里绑定数据
     */
    public abstract void onRecyclerViewInitialized();

    /**
     * 下拉刷新
     */
    public abstract void onPullRefresh();

    /**
     * 上拉加载更多
     */
    public abstract void onLoadMore();

    /**
     * 根据实体生成对应的Cell
     *
     * @param list 实体列表
     * @return cell列表
     */
    protected abstract List<Cell> getCells(List<T> list);

}
