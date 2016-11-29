package com.mbsgood.dropdownmenu.doubleView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mbsgood.dropdownmenu.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/*
* User: ChenCHaoXue
* Create date: 2016-11-29 
* Time: 15:30 
* From VCard
*/
public class DoubleDropActivity extends AppCompatActivity implements DoublePopupView.OnSelectListener {
    FrameLayout content;
    View maskView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double);
        content = (FrameLayout) findViewById(R.id.content);
        maskView = findViewById(R.id.maskView);
        maskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popView(View.GONE,R.anim.slide_top_out);
            }
        });
    }

    public void goDrop(View view) {
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            test.add("test" + i);
        }
        LinkedHashMap<String, List<String>> listLinkedHashMap = new LinkedHashMap<>();
        listLinkedHashMap.put("全部", new ArrayList<String>());
        for (String string : test) {
            listLinkedHashMap.put(string, test);
        }
        DoublePopupView dropPopupView = new DoublePopupView(this);
        dropPopupView.setData(listLinkedHashMap);
        content.addView(dropPopupView);
        dropPopupView.setOnSelectListener(this);

        popView(View.VISIBLE,R.anim.slide_top_in);
    }

    @Override
    public void onSelect(String area, String zone) {
        Toast.makeText(this, area + "----------<" + zone, Toast.LENGTH_SHORT).show();
        popView(View.GONE,R.anim.slide_top_out);
    }

    private void popView(int visibility,int animation) {
        content.setVisibility(visibility);
        content.setAnimation(AnimationUtils.loadAnimation(this, animation));
        maskView.setVisibility(visibility);
    }
}
