package com.example.asus.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

public class HealthSecondActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    // TabLayout指示器添加文本
    public static String leftTitle = "null";
    public static String rightTitle = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        setContentView(R.layout.health_second_activity);
        Intent intent = this.getIntent();
        String content = intent.getStringExtra("health");
        initView(); // 初始化控件
        initViewPager(content); // 初始化ViewPager
    }
    //初始化ViewPager
    private void initViewPager(String content) {
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        fragments.add(new FragmentLeft());
        fragments.add(new FragmentRight());
        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mViewPager.setAdapter(myPagerAdapter);
        // TabLayout 指示器
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        // 使用 TabLayout 和 ViewPager 相关联
        mTabLayout.setupWithViewPager(mViewPager);

        if(content.equals("健康速递")){
            leftTitle = "食品安全";
            rightTitle = "健康速递";
        }
        else if(content.equals("营养科普")){
            leftTitle = "营养科普";
            rightTitle = "养生食谱";
        }
        else if(content.equals("人群养生")){
            leftTitle = "男性";
            rightTitle = "女性";
        }
        else if(content.equals("运动常识")){
            leftTitle = "运动常识";
            rightTitle = "有氧瑜伽";
        }
        else if(content.equals("心灵氧吧")){
            leftTitle = "心理百科";
            rightTitle = "心灵氧吧";
        }
        else if(content.equals("中医理论")){
            leftTitle = "体质养生";
            rightTitle = "两性养生";
        }
        mTabLayout.getTabAt(0).setText(leftTitle);
        mTabLayout.getTabAt(1).setText(rightTitle);
    }
    //初始化控件
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.health_second_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.health_second_viewPager);
    }
}
