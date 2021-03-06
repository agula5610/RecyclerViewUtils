package com.luxiaochun.recyclerviewhelper.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ProjectName: JiuZhou
 * Author: jun
 * Date: 2018-01-10 15:59
 */
public class ViewHolderRocket extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View mItemView;

    public ViewHolderRocket(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        mItemView = itemView;
    }

    public View getmItemView() {
        return mItemView;
    }

    public View getView(int resId) {
        return retrieveView(resId);
    }

    protected <V extends View> V retrieveView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = mItemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (V) view;
    }

    public TextView getTextView(int resId){
        return retrieveView(resId);
    }

    public ImageView getImageView(int resId){
        return retrieveView(resId);
    }

    public Button getButton(int resId){
        return retrieveView(resId);
    }

    public LinearLayout getLinearLayout(int resId){return retrieveView(resId);}

    public void setText(int resId,CharSequence text){
        getTextView(resId).setText(text);
    }

    public void setText(int resId,int strId){
        getTextView(resId).setText(strId);
    }
}
