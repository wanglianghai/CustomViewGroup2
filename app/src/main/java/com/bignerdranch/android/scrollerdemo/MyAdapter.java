package com.bignerdranch.android.scrollerdemo;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21/021.
 */

public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MyAdapter(@Nullable List<String> data) {
        super(R.layout.item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
