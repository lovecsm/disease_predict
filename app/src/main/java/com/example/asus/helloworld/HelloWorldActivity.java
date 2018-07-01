package com.example.asus.helloworld;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import static com.example.asus.helloworld.FileReadWrite.saveFile;

public class HelloWorldActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ProgressBar web_progress_bar;
    private boolean firstLaunch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        setContentView(R.layout.activity_hello_world);
        if(null==FileReadWrite.getIp("server.ip")){
            firstLaunch = true;
        }
        if(firstLaunch){
            askPermission("注意","为了您能够正常使用，我们需要在您的设备上存取相关数据文件，请放心，这不会损坏您的设备。接下来请您点击允许。");
        }
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        web_progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
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
                        //预测疾病
                        if (toolbar != null) {
                            toolbar.setTitle("疾病预测");
                            toolbar.setSubtitle("依据症状预测疾病");
                        }
                        replaceFragment(new PredictFragment());
                        web_progress_bar.setVisibility(View.GONE);//进度条消失
                        break;
                    case R.id.nav_ask:
                        //咨询医师
                        if (toolbar != null) {
                            toolbar.setTitle("咨询医师");
                            toolbar.setSubtitle("在线名医快速问诊");
                        }
                        replaceFragment(new AskFragment());
                        break;
                    case R.id.nav_find:
                        //疾病查询
                        if (toolbar != null) {
                            toolbar.setTitle("疾病查询");
                            toolbar.setSubtitle("疑难杂症在这里查");
                        }
                        replaceFragment(new FindDiseaseFragment());
                        web_progress_bar.setVisibility(View.GONE);//进度条消失
                        break;
                    case R.id.nav_knowledge:
                        //养生知识
                        if (toolbar != null) {
                            toolbar.setTitle("养生知识");
                            toolbar.setSubtitle("保养身心预防疾病");
                        }
                        replaceFragment(new HealthKnowledge());
                        web_progress_bar.setVisibility(View.GONE);//进度条消失
                        break;
                    case R.id.nav_about:
                        //关于
                        if (toolbar != null) {
                            toolbar.setTitle("疾病预测");
                            toolbar.setSubtitle("关于作者");
                            web_progress_bar.setVisibility(View.GONE);//进度条消失
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
                finish();
                break;
            case R.id.setting:
                alertEdit();    //弹出IP地址编辑框
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
            } else {
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

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void alertEdit(){
        final EditText et = new EditText(HelloWorldActivity.this);
        final AlertDialog show = new AlertDialog.Builder(HelloWorldActivity.this).setTitle("请输入服务器IP")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        saveFile(et.getText().toString(), "server.ip");  //保存IP地址到文件
                        Snackbar.make(getCurrentFocus(),"保存成功",Snackbar.LENGTH_SHORT).show();
                        Log.i("file","保存文件");
                    }
                }).setNegativeButton("取消", null).show();
    }
    //请求读写权限
    private void getPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }

    public void askPermission(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HelloWorldActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPermission();
            }
        });
        builder.show();
    }
}