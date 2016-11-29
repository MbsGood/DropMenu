package com.mbsgood.dropdownmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/*
* User: ChenCHaoXue
* Create date: 2016-11-29 
* Time: 13:52 
* From VCard
*/
public abstract class BaseListAdapter<T> extends BasicAdapter<T> {


    public LayoutInflater mInflater;

    public BaseListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
