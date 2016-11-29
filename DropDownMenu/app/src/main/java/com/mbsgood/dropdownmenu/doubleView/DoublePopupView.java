package com.mbsgood.dropdownmenu.doubleView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mbsgood.dropdownmenu.R;
import com.mbsgood.dropdownmenu.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DoublePopupView extends LinearLayout implements AdapterView.OnItemClickListener {

    private ListView listViewLeft;
    private ListView listViewRight;
    private CommonAdapter leftAdapter;
    private CommonAdapter rightAdapter;
    private LinkedHashMap<String, List<String>> mData;
    private OnBusinessSelectListener mOnBusinessSelectListener;

    public DoublePopupView(Context context) {
        this(context, null);
    }

    public DoublePopupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoublePopupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundResource(android.R.color.white);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_popup_second_level, this);
        listViewLeft = (ListView) findViewById(R.id.lv_sort);
        listViewRight = (ListView) findViewById(R.id.lv_sort_detail);
        listViewLeft.setOnItemClickListener(this);
        listViewRight.setOnItemClickListener(this);
    }

    public void setData(LinkedHashMap<String, List<String>> data) {
        this.mData = data;
        List<String> sortList = new ArrayList<>(data.keySet());
        leftAdapter = new CommonAdapter(getContext());
        leftAdapter.addMoreItems(sortList);
        listViewLeft.setAdapter(leftAdapter);

        rightAdapter = new CommonAdapter(getContext());
        rightAdapter.addMoreItems(sortList);
        listViewRight.setAdapter(rightAdapter);
    }

    public void setOnSelectListener(OnBusinessSelectListener mOnBusinessSelectListener) {
        this.mOnBusinessSelectListener = mOnBusinessSelectListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lv_sort) {
            if (leftAdapter.getSelectItem() == position) return;
            leftAdapter.setSelectPosition(position);
            rightAdapter.updateData(mData.get(leftAdapter.getSelectData()));
            if (mOnBusinessSelectListener != null && position == 0) {
                mOnBusinessSelectListener.onBusinessSelect("", "");
            }
        } else if (parent.getId() == R.id.lv_sort_detail) {
            rightAdapter.setSelectPosition(position);
            if (mOnBusinessSelectListener != null) {
                String zone = rightAdapter.getSelectData();
                if (TextUtils.equals(zone, "全部")) {
                    mOnBusinessSelectListener.onBusinessSelect(leftAdapter.getSelectData(), "");
                } else {
                    mOnBusinessSelectListener.onBusinessSelect(leftAdapter.getSelectData(), zone);
                }
            }
        }
    }

    public interface OnBusinessSelectListener {
        void onBusinessSelect(String area, String zone);
    }

}
