package com.mbsgood.dropdownmenu.singleDropView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mbsgood.dropdownmenu.R;
import com.mbsgood.dropdownmenu.adapter.DropMenuAdapter;

import java.util.ArrayList;

/*
* User: ChenCHaoXue
* Create date: 2016-11-29 
* Time: 11:59 
* From VCard
* 普通下拉列表，可以自定义增加ViewTab
*/
public class DropMenuActivity extends AppCompatActivity {
    private DropDownMenu dropDownMenu;
    private ListView lvItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_menu);
        dropDownMenu = (DropDownMenu) findViewById(R.id.drop_menu);
        /*
        * 实现的方式简单点
        * */
        for (int i = 0; i < 3; i++) {
            initList("test" + i);
        }
        /*
        * 中间的listView自己添加数据即可
        * 数据变更可以在position 实现
        * */
        lvItem=(ListView)findViewById(R.id.lv_item);

    }

    private void initList(String text) {
        ListView testView = new ListView(this);
        testView.setDivider(getResources().getDrawable(R.drawable.list_view_divider));
        testView.setDividerHeight(1);
        testView.setBackgroundColor(Color.WHITE);
        DropMenuAdapter adapter = new DropMenuAdapter(this);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(text + "--->" + i);
        }
        adapter.addMoreItems(list);
        testView.setAdapter(adapter);
        testView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DropMenuActivity.this, "onClick" + position, Toast.LENGTH_SHORT).show();
                //实现中间界面的listView数据变更
            }
        });
        dropDownMenu.addTab(text, -1, testView);
    }
}
