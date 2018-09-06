package com.luxiaochun.recyclerviewhelper.base;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-10 16:12
 */
public abstract class RVCell<T> implements Cell {
    public T mData;

    public RVCell(T t) {
        mData = t;
    }

}