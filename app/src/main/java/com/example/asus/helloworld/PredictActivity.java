package com.example.asus.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class PredictActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.predict_activity);
        Intent intent = this.getIntent();
        String content = intent.getStringExtra("disease");
        switch (content){
            case "胃部":
                replaceFragment(new PredictStomachFragment());
                break;
            default:
                break;
        }
    }
    //切换fragment的方法
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.predict_second_fragment, fragment);
        transaction.commit();
    }
}
