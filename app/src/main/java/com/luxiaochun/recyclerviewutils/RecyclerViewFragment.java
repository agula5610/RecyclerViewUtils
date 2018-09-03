package com.luxiaochun.recyclerviewutils;

import android.os.Bundle;

import com.luxiaochun.recyclerviewhelper.base.Cell;
import com.luxiaochun.recyclerviewhelper.fragment.RvBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-15 14:44
 */
public class RecyclerViewFragment extends RvBaseFragment {
    private RecyclerViewFragment mFragment;


    public static RecyclerViewFragment newInstance(String param1) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
        if (getArguments() != null) {
//            searchContent = getArguments().getString("param1");
        }
    }

    @Override
    public void onRecyclerViewInitialized() {
        mBaseAdapter.showLoading();
        ArrayList<PersonBean> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            PersonBean bean = new PersonBean();
            bean.setName("姓名" + i + "");
            bean.setAge("20");
            bean.setPhone("18956321458");
            dataList.add(bean);
        }
        mBaseAdapter.hideLoading();
        mBaseAdapter.setData(getCells(dataList));
    }

    @Override
    public void onPullRefresh() {
    }

    @Override
    public void onLoadMore() {
        hideLoadMore();
    }

    @Override
    protected List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PersonBean bean = (PersonBean) list.get(i);
            cells.add(new TestCell(bean));
        }
        return cells;
    }
}
