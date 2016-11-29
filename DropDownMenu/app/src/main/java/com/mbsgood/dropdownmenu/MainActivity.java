package com.mbsgood.dropdownmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mbsgood.dropdownmenu.doubleView.DoubleDropActivity;
import com.mbsgood.dropdownmenu.singleDropView.DropMenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
    * 单个
    * */
    public void goDropView(View view) {
        startActivity(new Intent(this, DropMenuActivity.class));
    }

    /*
     * 多个
     * */
    public void goDoubleDropView(View view) {
        startActivity(new Intent(this, DoubleDropActivity.class));
    }
}
