package com.example.asus.helloworld;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ASUS on 2018/6/22.
 */

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button contactButton = (Button) getActivity().findViewById(R.id.contact);
        Button introduceButton = (Button) getActivity().findViewById(R.id.introduce);
        contactButton.setOnClickListener(new ButtonListener());
        introduceButton.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.contact:
                    if (checkApkExist(getContext(), "com.tencent.mobileqq")) {
                        Intent intent = new Intent();
                        intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=1273278618"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "本机未安装QQ应用", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.introduce:
                    dialog("软件介绍", "本软件是一个以疾病预测为核心，拥有咨询医师、疾病查询、养身知识多方面辅助功能的聚合类健康管理app，运用了大数据和移动端app开发技术，让用户可以随时随地实现对健康状况的轻松把握。");
                    break;
                default:
                    break;
            }
        }
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    //弹窗
    private void dialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}




