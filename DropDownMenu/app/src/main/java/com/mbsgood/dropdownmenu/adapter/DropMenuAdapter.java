package com.mbsgood.dropdownmenu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbsgood.dropdownmenu.R;

/*
* User: ChenCHaoXue
* Create date: 2016-11-29 
* Time: 13:59 
* From VCard
*/
public class DropMenuAdapter extends BaseListAdapter<String> {
    public DropMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.lv_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tvName=(TextView)convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(getItem(position));
        return convertView;
    }
    class ViewHolder{
        TextView tvName;
    }
}
