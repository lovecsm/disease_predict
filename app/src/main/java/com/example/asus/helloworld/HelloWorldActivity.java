package com.example.asus.helloworld;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class HelloWorldActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        setContentView(R.layout.activity_hello_world);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        if (toolbar != null) {
            toolbar.setTitle("疾病预测");
            toolbar.setSubtitle("依据症状预测疾病");
        }
        replaceFragment(new PredictFragment()); //进入第一个视图默认显示预测界面
        navView.setCheckedItem(R.id.nav_predict);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_predict:
                        //TODO:预测疾病
                        if (toolbar != null) {
                            toolbar.setTitle("疾病预测");
                            toolbar.setSubtitle("依据症状预测疾病");
                        }
                        replaceFragment(new PredictFragment());
                        break;
                    case R.id.nav_ask:
                        //TODO:咨询医师
                        if (toolbar != null) {
                            toolbar.setTitle("咨询医师");
                            toolbar.setSubtitle("在线名医快速问诊");
                        }
                        replaceFragment(new AskFragment());
                        break;
                    case R.id.nav_find:
                        //TODO:疾病查询
                        if (toolbar != null) {
                            toolbar.setTitle("疾病查询");
                            toolbar.setSubtitle("疑难杂症在这里查");
                        }
                        replaceFragment(new FindDiseaseFragment());
                        break;
                    case R.id.nav_knowledge:
                        //TODO:养身知识
                        if (toolbar != null) {
                            toolbar.setTitle("养身知识");
                            toolbar.setSubtitle("保养身心预防疾病");
                        }
                        replaceFragment(new HealthKnowledge());
                        break;
                    case R.id.nav_about:
                        //TODO:关于
                        if (toolbar != null) {
                            toolbar.setTitle("疾病预测");
                            toolbar.setSubtitle("关于作者");
                        }
                        replaceFragment(new AboutFragment());
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.exit:
                Toast.makeText(this, "已退出", Toast.LENGTH_SHORT).
                        show();
                finish();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_layout, fragment);
        transaction.commit();
    }
boolean isExit;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Fragment current = getSupportFragmentManager().findFragmentById(R.id.right_layout);
            if (current instanceof AskFragment) {
                ((AskFragment) current).goBack();
                return true;
            }
            else{
                if (!isExit) {
                    isExit = true;
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                isExit = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    finish();
                }
                return false;
            }

        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }
}