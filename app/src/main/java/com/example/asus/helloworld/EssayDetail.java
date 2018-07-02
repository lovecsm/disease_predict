package com.example.asus.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EssayDetail extends AppCompatActivity {
    //装载所有动态添加的Item的LinearLayout容器
    private LinearLayout addHotelNameView;
    private int childIndex = 0;//定位每一个item
    private boolean isText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        addHotelNameView = (LinearLayout) findViewById(R.id.ll_addView);
        Intent intent = this.getIntent();
        String url = intent.getStringExtra("seeMore");
        Log.i("内容",url);
        //添加Item
        GetHtml(url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    if (childIndex == 0) {
                        TextView title = (TextView) findViewById(R.id.head_title);
                        addText(title, "很抱歉，由于网络或内部错误，您暂时无法查看该文章。");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //接收网络线程传来的网页源代码
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //do something,refresh UI;
                    match(msg.toString());   //调用正则表达式粗提取文章内容方法对文本粗提取
                    break;
                default:
                    break;
            }
        }
    };

    //请求网页获取网页源代码方法
    public void GetHtml(final String urlpath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url;
                    url = new URL(urlpath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 1000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        byte[] data = readStream(inputStream);
                        String html = new String(data,"gbk");
                        Message message = new Message();
                        message.obj = html;
                        mHandler.sendMessage(message);  //从网络线程传递网页源码给主线程
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }}
        }).start();
    }
    //正则表达式粗提取文章内容方法
    private void match(String res){
        Log.i("html",res);
        TextView title = (TextView) findViewById(R.id.head_title);   //标题
        ImageView headImg = (ImageView) findViewById(R.id.head_img); //顶部图片
        TextView time = (TextView) findViewById(R.id.time);          //发布时间
        TextView from = (TextView) findViewById(R.id.from);          //来源
        TextView bottom = (TextView) findViewById(R.id.bottom);      //页面底部提示文字
        //正则表达式粗提取文章内容及顶部信息
        String Regex="(?s)text-align:center\"><img src=\"(.*?)\".*contop\">(.*?)</p.*h2>(.*?)</h2.*?span>(.*?)</span(.*?)((</div>)|(<a href=\"http://www.ttys5.com))";
        Pattern p=Pattern.compile(Regex);
        Matcher matcher=p.matcher(res);
        String con="";
        if (matcher.find()){
            title.setText(matcher.group(3));                            //设置标题
            Glide.with(getApplicationContext()).load(matcher.group(1)).into(headImg);   //加载顶部图
            time.setText(matcher.group(2));                             //设置时间
            from.setText(matcher.group(4));                             //设置来源
            Log.i("match",matcher.group(5));
            con = matcher.group(5);                                     //正文内容
        }
        else{
            Log.i("matcher","粗匹配失败");
        }
        Regex = "((?<=<p></p><p>)|(?<=<p>)|(?<=src=\")|(?<=<strong>))(.*?)((\" )|(</p>)|(<br))"; //匹配文中链接和文字
        p = Pattern.compile(Regex);
        matcher = p.matcher(con);
        while(matcher.find()){
            //System.out.println(matcher.group(2));
            Log.i("单条内容",matcher.group(2));
            if(matcher.group(2).contains("http://")){                   //包含网址则认为是图片链接
                isText = false;                                         //图像内容
            }else{
                isText = true;                                          //文本内容
            }
            //对正文进行图文过滤
            String temp = matcher.group(2).replaceAll("(&[a-zA-Z]+?;)|(<[a-zA-Z]+?>)", "");
            temp = temp.replaceAll("(</[a-zA-Z]+?>)|(<img.*\")", "");
            addViewItem(null,temp);
        }
        if (childIndex>5)
            bottom.setText("---到底了---");
    }
    //InputStream转Byte数组方法
    private static byte[] readStream(InputStream in) throws IOException {
        //此处开始转换
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = in.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] byt = swapStream.toByteArray(); //in_b为转换之后的结果
        return byt;
    }

    //添加控件第一步（过滤图文），视情况选择添加文本或图像
    private void addViewItem(View view,String url) {
        ImageView imageView = null;
        TextView textView = null;
        if (isText) {                                                   //是文本则添加TextView布局
            View targetView = View.inflate(this, R.layout.item_text, null);
            addHotelNameView.addView(targetView);
        } else {                                                        //是图像则添加ImageView布局
            View targetView = View.inflate(this, R.layout.item_image, null);
            addHotelNameView.addView(targetView);
        }
        View childAt = addHotelNameView.getChildAt(childIndex);         //获取控件ID
        childIndex++;
        if (isText) {
            textView = (TextView) childAt.findViewById(R.id.instance_text); //添加文本实体
            addText(textView,url);
        } else {
            imageView = (ImageView) childAt.findViewById(R.id.instance_image);//添加图像实体
            addImage(childAt, url, imageView);
        }
    }
    //添加图片控件方法
    private void addImage(final View childAt, final String url,final ImageView imageView) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行 UI 操作，将结果显示到界面上
                Glide.with(childAt).load(url).into(imageView);
            }
        });
    }
    //添加文本控件方法
    private void addText(final TextView textView, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行 UI 操作，将结果显示到界面上
                if(!text.equals("")) {
                    textView.setText(text);
                }
            }
        });
    }
}