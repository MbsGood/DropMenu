package com.mbsgood.dropdownmenu.singleDropView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mbsgood.dropdownmenu.R;

/*
* User: ChenCHaoXue
* Create date: 2016-11-29
* Time: 11:33
* From VCard
*/
public class DropDownMenu extends LinearLayout implements View.OnClickListener {

    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器，包含popupMenuViews，maskView
    private FrameLayout containerView;
    //弹出菜单父布局
    private FrameLayout popupMenuViews;
    //遮罩半透明View，点击可关闭DropDownMenu
    private View maskView;
    //tabMenuView里面选中的tab位置，-1表示未选中
    private int current_tab_position = -1;

    //分割线颜色
    private int dividerColor = 0xffcccccc;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0xb3000000;
    //tab字体大小
    private int menuTextSize = 14;

    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;

    private final int leftTvResId = R.id.tv_left_tip;
    private final int rightTvResId = R.id.tv_right_tip;
    private OnTabSelectListener onTabSelectListener;


    public DropDownMenu(Context context) {
        this(context, null);
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);

        //为DropDownMenu添加自定义属性
        int menuBackgroundColor = 0xffffffff;
        int underlineColor = 0xffcccccc;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        underlineColor = a.getColor(R.styleable.DropDownMenu_ddunderlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.DropDownMenu_dddividerColor, dividerColor);
        textSelectedColor = a.getColor(R.styleable.DropDownMenu_ddtextSelectedColor, textSelectedColor);
        textUnselectedColor = a.getColor(R.styleable.DropDownMenu_ddtextUnselectedColor, textUnselectedColor);
        menuBackgroundColor = a.getColor(R.styleable.DropDownMenu_ddmenuBackgroundColor, menuBackgroundColor);
        maskColor = a.getColor(R.styleable.DropDownMenu_ddmaskColor, maskColor);
        menuTextSize = a.getDimensionPixelSize(R.styleable.DropDownMenu_ddmenuTextSize, menuTextSize);
        menuSelectedIcon = R.mipmap.icon_drop_up;
        menuUnselectedIcon = R.mipmap.icon_drop_down;
        a.recycle();

        //初始化tabMenuView并添加到tabMenuView
        tabMenuView = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpTpPx(44));
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setBackgroundColor(menuBackgroundColor);
        tabMenuView.setLayoutParams(params);
        addView(tabMenuView, 0);

        //为tabMenuView添加下划线
        View underLine = new View(getContext());
        underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        underLine.setBackgroundColor(underlineColor);
        addView(underLine, 1);

        //初始化containerView并将其添加到DropDownMenu
        containerView = new FrameLayout(context);
        containerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(containerView, 2);

        init();
    }

    private void init() {
        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        maskView.setVisibility(GONE);
        containerView.addView(maskView);

        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setVisibility(GONE);
        popupMenuViews.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpTpPx(267)));
        containerView.addView(popupMenuViews);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() == 3) {
            if (containerView.getChildCount() == 1) {
                throw new IllegalStateException("You can only attach one child");
            }
            containerView.addView(child, 0);
            return;
        }

        super.addView(child, index, params);
    }

    public void addTab(@NonNull String tabTitle, @DrawableRes int tabIcon, View popupView) {
        addTab(tabTitle, null, tabIcon, popupView);
    }

    public void addTab(@NonNull String leftTabTitle, String rightTabTitle, @DrawableRes int tabIcon, View popupView) {
        RelativeLayout tab = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.drop_dowm_tab, tabMenuView, false);
        TextView leftText = (TextView) tab.findViewById(leftTvResId);
        TextView rightText = (TextView) tab.findViewById(rightTvResId);
        leftText.setText(leftTabTitle);
        if (tabIcon != -1) {
            leftText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(tabIcon), null, null, null);
        }
        if (rightTabTitle != null) {
            rightText.setText(rightTabTitle);
        }
        rightText.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
        tab.setOnClickListener(this);
        tabMenuView.addView(tab);

        if (popupView != null) {
            popupView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            popupMenuViews.addView(popupView);
        } else {
            View emptyView = new View(getContext());
            emptyView.setId(0);
            popupMenuViews.addView(emptyView);
        }
    }

    public void setTabText(String textLeft) {
        setTabText(textLeft, "");
    }

    /**
     * 改变tab文字
     */
    public void setTabText(String textLeft, String textRight) {
        if (current_tab_position != -1) {
            RelativeLayout currentLayout = (RelativeLayout) tabMenuView.getChildAt(current_tab_position);
            ((TextView) currentLayout.findViewById(leftTvResId)).setText(textLeft);
            ((TextView) currentLayout.findViewById(rightTvResId)).setText(textRight);
            closeMenu();
        }
    }

    public void setTabClickable(boolean clickable) {
        for (int i = 0; i < tabMenuView.getChildCount(); i++) {
            tabMenuView.getChildAt(i).setClickable(clickable);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (current_tab_position != -1) {
            ((TextView) tabMenuView.getChildAt(current_tab_position).findViewById(rightTvResId))
                    .setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnselectedIcon), null);
            popupMenuViews.setVisibility(View.GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_top_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            current_tab_position = -1;
        }

    }

    /**
     * DropDownMenu是否处于可见状态
     *
     * @return
     */
    public boolean isShowing() {
        return current_tab_position != -1;
    }

    @Override
    public void onClick(View target) {
        for (int i = 0; i < tabMenuView.getChildCount(); i++) {
            RelativeLayout tempLayout = (RelativeLayout) tabMenuView.getChildAt(i);
            if (target == tempLayout) {
                if (current_tab_position == i) {
                    closeMenu();
                } else {
                    if (current_tab_position == -1) {
                        maskView.setVisibility(VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
                    }
                    popupMenuViews.setVisibility(View.VISIBLE);
                    popupMenuViews.getChildAt(i).setVisibility(View.VISIBLE);
                    popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_top_in));
                    ((TextView) tempLayout.findViewById(rightTvResId)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                            getResources().getDrawable(menuSelectedIcon), null);
                    current_tab_position = i;
                    if (onTabSelectListener != null) {
                        onTabSelectListener.onTabSelect(current_tab_position, popupMenuViews.getChildAt(i).getId() == 0);
                    }
                }
            } else {
                ((TextView) tempLayout.findViewById(rightTvResId)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(menuUnselectedIcon), null);
                popupMenuViews.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    public interface OnTabSelectListener {
        void onTabSelect(int position, boolean isEmpty);
    }
}
