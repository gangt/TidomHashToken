package com.yunqukuailian.app.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * Created by Tidom on 2018/3/24/024.
 */

public class BaseListView extends ListView {
    public BaseListView(Context context) {
        super(context);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandsSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandsSpec);
    }
}
