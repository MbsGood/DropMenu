package com.mbsgood.dropdownmenu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbsgood.dropdownmenu.R;

import java.util.List;

/*
* User: ChenCHaoXue
* Create date: 2016-11-29 
* Time: 15:16 
* From VCard
*/
public class CommonAdapter extends BaseListAdapter<String> {
    private int selectItem = 0;

    public CommonAdapter(Context context) {
        super(context);
    }

    public void updateData(List<String> mData) {
//        getItem(selectItem) = mData;
        selectItem = -1;
        notifyDataSetChanged();
    }
    public void setSelectPosition(int position) {
        this.selectItem = position;
        notifyDataSetChanged();
    }

    public String getSelectData() {
        return getItem(selectItem);
    }

    public int getSelectItem() {
        return selectItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lv_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(getItem(position));

        if (selectItem == position) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
    }
}
