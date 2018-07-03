package com.example.asus.helloworld;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.example.asus.helloworld.FileReadWrite.getIp;


public class PredictStomachFragment extends Fragment {
    private Activity activity;
    private Button backButton, commitButton;
    CheckBox weakness = null;
    CheckBox hematochezia = null;
    CheckBox cold = null;
    CheckBox acid = null;
    CheckBox fever = null;
    CheckBox emesis = null;
    CheckBox coldness = null;
    CheckBox ulcer = null;
    CheckBox nausea = null;
    CheckBox tract = null;
    CheckBox crater = null;
    CheckBox fibrillation = null;
    CheckBox collywobbles = null;
    CheckBox mass = null;
    CheckBox stomachache = null;
    CheckBox bleeding = null;
    CheckBox microStomachache = null;
    CheckBox ventosity = null;
    CheckBox pallor = null;

    //症状数组
    final String symbols[] = { "下肢无力,", "便血,", "冷汗,", "反酸,", "发烧,", "呕吐,", "四肢发冷,", "幽门管溃疡,", "恶心,", "消化道穿孔,",
            "溃疡外观呈火山口样,", "肌肉纤维震颤,", "肚子疼,", "腹部肿块,", "胃疼,", "胃肠道出血,", "胃部隐痛,", "腹胀,", "面色苍白\n" };
    final int sign[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    String userSymbol = ""; // 保存结果的字符串

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.predict_stomach_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity(); //获取当前Activity
        id();                     //绑定CheckBox的ID
        listen();                 //监听CheckBox

    }


    //绑定控件ID
    private void id(){
        backButton = (Button) activity.findViewById(R.id.back_button);
        commitButton = (Button) activity.findViewById(R.id.commit_button);
        weakness = (CheckBox) activity.findViewById(R.id.weakness);
        hematochezia = (CheckBox) activity.findViewById(R.id.hematochezia);
        cold = (CheckBox) activity.findViewById(R.id.cold);
        acid = (CheckBox) activity.findViewById(R.id.acid);
        fever = (CheckBox) activity.findViewById(R.id.fever);
        emesis = (CheckBox) activity.findViewById(R.id.emesis);
        coldness = (CheckBox) activity.findViewById(R.id.coldness);
        ulcer = (CheckBox) activity.findViewById(R.id.ulcer);
        nausea = (CheckBox) activity.findViewById(R.id.nausea);
        tract = (CheckBox) activity.findViewById(R.id.tract);
        crater = (CheckBox) activity.findViewById(R.id.crater);
        fibrillation = (CheckBox) activity.findViewById(R.id.fibrillation);
        collywobbles = (CheckBox) activity.findViewById(R.id.collywobbles);
        mass = (CheckBox) activity.findViewById(R.id.mass);
        stomachache = (CheckBox) activity.findViewById(R.id.stomachache);
        bleeding = (CheckBox) activity.findViewById(R.id.bleeding);
        microStomachache = (CheckBox) activity.findViewById(R.id.microStomachache);
        ventosity = (CheckBox) activity.findViewById(R.id.ventosity);
        pallor = (CheckBox) activity.findViewById(R.id.pallor);
    }

    //对控件进行监听
    private void listen(){
        backButton.setOnClickListener(new ButtonListener());
        commitButton.setOnClickListener(new ButtonListener());
        weakness.setOnCheckedChangeListener(listener);
        hematochezia.setOnCheckedChangeListener(listener);
        cold.setOnCheckedChangeListener(listener);
        acid.setOnCheckedChangeListener(listener);
        fever.setOnCheckedChangeListener(listener);
        emesis.setOnCheckedChangeListener(listener);
        coldness.setOnCheckedChangeListener(listener);
        ulcer.setOnCheckedChangeListener(listener);
        nausea.setOnCheckedChangeListener(listener);
        tract.setOnCheckedChangeListener(listener);
        crater.setOnCheckedChangeListener(listener);
        fibrillation.setOnCheckedChangeListener(listener);
        collywobbles.setOnCheckedChangeListener(listener);
        mass.setOnCheckedChangeListener(listener);
        stomachache.setOnCheckedChangeListener(listener);
        bleeding.setOnCheckedChangeListener(listener);
        microStomachache.setOnCheckedChangeListener(listener);
        ventosity.setOnCheckedChangeListener(listener);
        pallor.setOnCheckedChangeListener(listener);
    }

    //对提交和返回按钮进行监听
    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_button:
                    activity.finish(); //返回上层
                    break;
                case R.id.commit_button:
                    post();//向服务器提交数据
                    break;
            }
        }
    }
    //对CheckBox监听
    private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.weakness:
                    if (isChecked) {
                        sign[0] = 1;
                    } else {
                        sign[0] = 0;
                    }
                    break;
                case R.id.hematochezia:
                    if (isChecked) {
                        sign[1] = 1;
                    } else {
                        sign[1] = 0;
                    }
                    break;
                case R.id.cold:
                    if (isChecked) {
                        sign[2] = 1;
                    } else {
                        sign[2] = 0;
                    }
                    break;
                case R.id.acid:
                    if (isChecked) {
                        sign[3] = 1;
                    } else {
                        sign[3] = 0;
                    }
                    break;
                case R.id.fever:
                    if (isChecked) {
                        sign[4] = 1;
                    } else {
                        sign[4] = 0;
                    }
                    break;
                case R.id.emesis:
                    if (isChecked) {
                        sign[5] = 1;
                    } else {
                        sign[5] = 0;
                    }
                    break;
                case R.id.coldness:
                    if (isChecked) {
                        sign[6] = 1;
                    } else {
                        sign[6] = 0;
                    }
                    break;
                case R.id.ulcer:
                    if (isChecked) {
                        sign[7] = 1;
                    } else {
                        sign[7] = 0;
                    }
                    break;
                case R.id.nausea:
                    if (isChecked) {
                        sign[8] = 1;
                    } else {
                        sign[8] = 0;
                    }
                    break;
                case R.id.tract:
                    if (isChecked) {
                        sign[9] = 1;
                    } else {
                        sign[9] = 0;
                    }
                    break;
                case R.id.crater:
                    if (isChecked) {
                        sign[10] = 1;
                    } else {
                        sign[10] = 0;
                    }
                    break;
                case R.id.fibrillation:
                    if (isChecked) {
                        sign[11] = 1;
                    } else {
                        sign[11] = 0;
                    }
                    break;
                case R.id.collywobbles:
                    if (isChecked) {
                        sign[12] = 1;
                    } else {
                        sign[12] = 0;
                    }
                    break;
                case R.id.mass:
                    if (isChecked) {
                        sign[13] = 1;
                    } else {
                        sign[13] = 0;
                    }
                    break;
                case R.id.stomachache:
                    if (isChecked) {
                        sign[14] = 1;
                    } else {
                        sign[14] = 0;
                    }
                    break;
                case R.id.bleeding:
                    if (isChecked) {
                        sign[15] = 1;
                    } else {
                        sign[15] = 0;
                    }
                    break;
                case R.id.microStomachache:
                    if (isChecked) {
                        sign[16] = 1;
                    } else {
                        sign[16] = 0;
                    }
                    break;
                case R.id.ventosity:
                    if (isChecked) {
                        sign[17] = 1;
                    } else {
                        sign[17] = 0;
                    }
                    break;
                case R.id.pallor:
                    if (isChecked) {
                        sign[18] = 1;
                    } else {
                        sign[18] = 0;
                    }
                    break;
                default:
                    break;
            }

        }
    };

    private void post(){
        userSymbol = "";                    //清空症状
        for (int i = 0; i < 19; i++) {      //构建症状
            userSymbol += (sign[i] + "");
        }
        Snackbar.make(getView(), "连接中", Snackbar.LENGTH_SHORT).show();
        sendRequest();
    }

    private void sendRequest() {
        final String appData = userSymbol;
        final String ip = "http://" + getIp("server.ip") + "/app_data";
        if(ip.equals("http:///app_data")){
            dialog("错误","请先到设置中配置服务器IP地址。");
            return;
        }
		//增加一个对IP地址合法性的判断
        String testIp = "(((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))):\\d+)|(http.*)";
        Pattern p = Pattern.compile(testIp);
        Matcher matcher = p.matcher(ip);
        if (matcher.find()) {
            // 开启线程来发起网络请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    HttpURLConnection connection = null;
                    BufferedReader reader = null;
                    try {
                        URL url = new URL(ip);
                        Log.i("file",url+"");
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        //向服务器发送数据
                        //Toast.makeText(getActivity(),"连接中",Toast.LENGTH_SHORT).show();
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes("appData="+appData);
                        //获取服务器返回的数据
                        InputStream in = connection.getInputStream();
                        // 对获取到的输入流进行读取
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        show(response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog("错误","连接超时！请检查服务器IP地址正确与否");
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                    Looper.loop();
                }
            }).start();}else {
            dialog("错误", "IP地址不合法,请重新设置");
        }
    }
    //预测结果展示
    private void show(String response) {
        Log.i("res",response);
        String Regex = ">(\\w+)<?";
        Pattern p = Pattern.compile(Regex);
        Matcher matcher = p.matcher(response);
        if (matcher.find()) {
            String showResult = "";
            if(matcher.group(1).equals("youmenluoganjun")) {
                showResult = "幽门螺杆菌感染";
            }
            else if(matcher.group(1).equals("mengzhongdu")) {
                showResult = "锰中毒";
            }
            else if(matcher.group(1).equals("kuiyangbingchuankong")) {
                showResult = "溃疡病穿孔";
            }
            else if(matcher.group(1).equals("weizhifangliu")) {
                showResult = "胃脂肪瘤";
            }
            else if(matcher.group(1).equals("weikuiyang")) {
                showResult = "胃溃疡";
            }
            else if(matcher.group(1).equals("health")) {
                showResult = "你很健康！继续保持哦";
            }
            else {
                //showResult = "服务器数据已更新，请联系管理员";
                showResult = matcher.group(1);
            }
            dialog("预测结果", showResult);
            Log.i("res",showResult);
        } else {
            Toast.makeText(getContext(), "正则表达式未匹配", Toast.LENGTH_SHORT);
        }
    }

    public void dialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        builder.show();
    }


}
