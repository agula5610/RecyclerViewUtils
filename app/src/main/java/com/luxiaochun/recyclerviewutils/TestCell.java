package com.luxiaochun.recyclerviewutils;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.luxiaochun.recyclerviewhelper.base.RVBaseCell;
import com.luxiaochun.recyclerviewhelper.base.RVBaseViewHolder;


/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-15 15:06
 */
public class TestCell extends RVBaseCell<PersonBean> {

    public TestCell(PersonBean bean) {
        super(bean);
    }

    @Override
    public void releaseResource() {

    }

    @Override
    public int getItemType() {
        return R.layout.rv_item;
    }

    @Override
    public void bindViewHolder(final RVBaseViewHolder holder, final int position) {
        holder.setText(R.id.store_name_tv, mData.getName());
        holder.setText(R.id.store_address_tv, mData.getAge());
        holder.setText(R.id.store_owner_tv, mData.getPhone());
        final View mItemView = holder.getmItemView();
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( mItemView.getContext(), position + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mItemView.getContext(), MainActivity.class);
                mItemView.getContext().startActivity(intent);
            }
        });
    }
}
