package com.luxiaochun.recyclerviewhelper.base;

import com.luxiaochun.recyclerviewhelper.viewholder.ViewHolderRocket;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-10 16:07
 */
public interface Cell {
    /**
     * 回收资源
     */
    void releaseResource();

    /**
     * 获取viewType
     *
     * @return
     */
    int getItemType();

    /**
     * 绑定ViewHolder
     *
     * @param holder
     * @param position
     * @return
     */
    void bindViewHolder(ViewHolderRocket holder, int position);

}
