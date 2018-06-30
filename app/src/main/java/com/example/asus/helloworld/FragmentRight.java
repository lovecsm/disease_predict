package com.example.asus.helloworld;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentRight extends Fragment {
    private List<HealthSecondItem> HealthSecondItemList = new ArrayList<>();
    private HealthSecondItemAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public boolean isLoading;
    private Handler handler = new Handler();
    private static int page = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.health_second_fragment_right, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_right);
        swipeLayout.setRefreshing(true);
        runInBg();
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_right);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HealthSecondItemAdapter(HealthSecondItemList);
        recyclerView.setAdapter(adapter);
        initLoadMore();
        reload();//对下拉进行监听，加载新的数据
    }
    private void reload(){

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新刷新页面
                HealthSecondItemList.clear();
                runInBg();
                page++;
            }
        });
    }
    /************************网络请求方法*************************/
    public void runInBg() {
        String url = "";
        if(HealthSecondActivity.rightTitle.equals("健康速递")){
            url = "http://m.ttys5.com/list.php?page="+page+"&style=0&classid=9&bclassid=0&totalnum=13763";
        }
        else if(HealthSecondActivity.rightTitle.equals("养生食谱")){
            url = "http://m.ttys5.com/list.php?page="+page+"&style=0&classid=22&bclassid=0&totalnum=10035";
        }
        else if(HealthSecondActivity.rightTitle.equals("女性")){
            url = "http://m.ttys5.com/list.php?page="+page+"&style=0&classid=29&bclassid=0&totalnum=2301";
        }
        else if(HealthSecondActivity.rightTitle.equals("有氧瑜伽")){
            url = "http://m.ttys5.com/list.php?page="+page+"&style=0&classid=25&bclassid=0&totalnum=4326";
        }
        else if(HealthSecondActivity.rightTitle.equals("心灵氧吧")){
            url = "http://m.ttys5.com/list.php?page="+page+"&style=0&classid=30&bclassid=0&totalnum=2819";
        }
        else if(HealthSecondActivity.rightTitle.equals("两性养生")){
            url = "http://m.ttys5.com/list.php?page="+page+"style=0&classid=34&bclassid=0&totalnum=395";
        }
        else{       //tabLayout标签出错，链接出错
            return;
        }
        testGetHtml(url);//加载链接
        Log.i("matcher","runInBg() 方法运行。");
    }
    public void testGetHtml(final String urlpath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("matcher","网络线程开启成功。");
                    URL url;
                    url = new URL(urlpath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 1000);
                    conn.setRequestMethod("GET");
                    Log.i("matcher","网络状态码"+conn.getResponseCode());
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        byte[] data = readStream(inputStream);
                        String html = new String(data, "gbk");
                        Log.i("matcher","请求成功。");
                        show(html);
                    } else {
                        Toast.makeText(getContext(),"连接出现问题，请检查网络后再试",Toast.LENGTH_SHORT).show();
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
    public void show(String res) {
        String prefixion = "http://m.ttys5.com/";
        String Regex="href=\"(show.php?.+)\">[^<]*<img src=\"(.+)\" width.*?/>[^>]*?>(.+?)</.*>[^>]*>(.+?)</";
        Pattern p=Pattern.compile(Regex);
        Matcher matcher=p.matcher(res);
        Log.i("matcher","正则表达式运行。");
        boolean PatternIsRun = false;
        while (matcher.find()) {
            //Log.i("matcher","正则表达式查到内容");
            //group(1)->文章地址
            //group(2)->图片地址
            //group(3)->标题
            //group(4)->摘要
            String title = matcher.group(3);
            String content = matcher.group(4);
            String imgUrl = matcher.group(2);
            String essayUrl = prefixion + matcher.group(1).replaceAll("&amp;","&");
            //Log.i("链接替换",essayUrl);
            HealthSecondItemList.add(new HealthSecondItem(title, content, imgUrl, essayUrl));
            PatternIsRun = true;
            //Log.i("matcher", matcher.group(3)+matcher.group(4)+matcher.group(2)+matcher.group(1));
        }
        if(!PatternIsRun){
            Toast.makeText(getContext(),"已没有更多内容",Toast.LENGTH_SHORT).show();
            swipeLayout.setRefreshing(false);
            return;
        }
        showResult();
        swipeLayout.setRefreshing(false);
    }
    private void showResult() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行 UI 操作，将结果显示到界面上
                adapter.notifyDataSetChanged();
                //HealthSecondItemList.clear();
            }
        });
    }
    /*输入流转为字节流*/
    private byte[] readStream(InputStream in) throws IOException {
        //此处开始转换
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = in.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] byt = swapStream.toByteArray(); //byt为转换之后的结果
        //responseText.setText("转换完毕");
        return byt;
    }

    private void initLoadMore() {
        //通过recyclerView的onscrolllistener的监听来实现上拉加载更多的功能
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
                // 获取最后一个可见条目
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");
                    //获取刷新状态
                    Snackbar.make(getView(), "正在加载...", Snackbar.LENGTH_SHORT).show();
                    boolean isRefreshing = swipeLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                runInBg();
                                page++;
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
    }
}
