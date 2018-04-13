package com.yunqukuailian.app.model.ModeView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Tidom on 2018/3/26/026.
 */

public class MyReCycleView extends RecyclerView {
    public MyReCycleView(Context context) {
        super(context);
    }

    public MyReCycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyReCycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandsSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandsSpec);
    }
}
