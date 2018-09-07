package com.luxiaochun.recyclerviewutils;

import android.os.Bundle;
import android.widget.Toast;

import com.luxiaochun.recyclerviewhelper.base.Cell;
import com.luxiaochun.recyclerviewhelper.fragment.RvFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-15 14:44
 */
public class RecyclerViewFragment extends RvFragment {

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRecyclerViewInitialized() {
        mBaseAdapter.showLoading();
        ArrayList<PersonBean> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PersonBean bean = new PersonBean();
            bean.setName("姓名" + i + "");
            bean.setAge("20");
            bean.setPhone("18956321458");
            dataList.add(bean);
        }
        mBaseAdapter.removeLoading();
        mBaseAdapter.addAll(getCells(dataList));
    }

    @Override
    public void onRefresh() {
        mBaseAdapter.clear();
        ArrayList<PersonBean> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PersonBean bean = new PersonBean();
            bean.setName("姓名" + i + "");
            bean.setAge("20");
            bean.setPhone("18956321458");
            dataList.add(bean);
        }
        mBaseAdapter.addAll(getCells(dataList));
        Toast.makeText(this.getActivity(), "更新啦", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        ArrayList<PersonBean> dataList = new ArrayList<>();
        for (int i = 10 * (pageNumber - 1); i < pageNumber * 10; i++) {
            PersonBean bean = new PersonBean();
            bean.setName("姓名" + i + "");
            bean.setAge("20");
            bean.setPhone("18956321458");
            dataList.add(bean);
        }
        mBaseAdapter.addAll(getCells(dataList));
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
