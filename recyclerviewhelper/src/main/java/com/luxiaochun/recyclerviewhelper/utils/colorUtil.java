package com.luxiaochun.recyclerviewhelper.utils;

import android.content.Context;
import android.util.TypedValue;

import com.luxiaochun.recyclerviewhelper.R;

/**
 * ProjectName: RecyclerViewUtils
 * PackageName: com.luxiaochun.recyclerviewhelper.utils
 * Author: jun
 * Date: 2018-09-04 16:11
 * Copyright: (C)HESC Co.,Ltd. 2016. All rights reserved.
 */
public class colorUtil {
    /**
     * 获取主题颜色
     *
     * @return
     */
    public static int getColorPrimary(Context context) {
        if (context == null) {
            return -1;
        }
        if (context.getTheme() == null) {
            return -1;
        } else {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            return typedValue.data;
        }
    }
}
