package com.mbsgood.dropdownmenu.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/*
* User: ChenCHaoXue
* Create date: 2016-11-29 
* Time: 14:24 
* From VCard
*/
public abstract class BasicAdapter<T> extends BaseAdapter {
    protected List<T> items;

    public BasicAdapter() {
        this.items = new ArrayList<>();
    }

    public void BasicAdapter(List<T> items) {
        this.items = items;
    }

    public void addMoreItems(List<T> newItems) {
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }
}
