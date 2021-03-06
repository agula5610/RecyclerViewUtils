package com.luxiaochun.recyclerviewhelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.luxiaochun.recyclerviewhelper.base.Cell;
import com.luxiaochun.recyclerviewhelper.adapter.RVAdapter;
import com.luxiaochun.recyclerviewhelper.utils.colorUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
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
public abstract class RvFragment<T> extends Fragment {
    public static final String TAG = "RvFragment";
    private FrameLayout titleLayout;
    protected RecyclerView mRecyclerView;
    protected RVAdapter mBaseAdapter;
    protected SmartRefreshLayout refreshLayout;
    protected int pageNumber = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rv_base_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initRefreshListener();
        initRecycleView();
        onRecyclerViewInitialized();
    }

    private void initView(View view) {
        titleLayout = view.findViewById(R.id.title_layout);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        mRecyclerView = view.findViewById(R.id.base_fragment_rv);
        mRecyclerView.setLayoutManager(initLayoutManger());
    }

    private void initRefreshListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) { //下拉刷新
                refreshlayout.finishRefresh(500);
                pageNumber = 1;
                RvFragment.this.onRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) { //上拉加载
                refreshlayout.finishLoadMore(500);
                pageNumber++;
                RvFragment.this.onLoadMore();
            }
        });
        refreshLayout.setRefreshHeader(new ClassicsHeader(this.getActivity()).setPrimaryColor(colorUtil.getColorPrimary(this.getActivity())));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this.getActivity()).setPrimaryColor(getResources().getColor(R.color.material_gray_300)));
    }

    /**
     * 设置下拉刷新Header
     *
     * @param header
     */
    protected void setRefreshHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }

    /**
     * 设置上拉加载Footer
     * @param footer
     */
    protected void setRefreshFooter(RefreshFooter footer) {
        refreshLayout.setRefreshFooter(footer);
    }

    private void initRecycleView() {
        mBaseAdapter = initAdapter();
        mRecyclerView.setAdapter(mBaseAdapter);
    }

    /**
     * 设置头布局，不在列表中
     *
     * @param view
     */
    protected void addTitleView(View view) {
        if (view == null) {
            return;
        }
        titleLayout.addView(view);
    }

    /**
     * 子类可以自己指定Adapter,如果不指定默认RVSimpleAdapter
     *
     * @return
     */
    protected RVAdapter initAdapter() {
        return new RVAdapter();
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
     * 是否启用下拉刷新功能
     *
     * @param enable
     */
    protected void setEnableRefresh(boolean enable) {
        refreshLayout.setEnableRefresh(enable);
    }

    /**
     * 是否启用上拉加载功能
     *
     * @param enable
     */
    protected void setEnableLoadMore(boolean enable) {
        refreshLayout.setEnableLoadMore(enable);
    }

    /**
     * RecyclerView 初始化完毕，可以在这个方法里绑定数据
     */
    public abstract void onRecyclerViewInitialized();

    /**
     * 下拉刷新
     */
    public abstract void onRefresh();

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
