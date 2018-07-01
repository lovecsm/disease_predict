package com.example.asus.helloworld;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileReadWrite {

    //从文件中获取IP地址
    public static String getIp(String fileName) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + fileName;

        } else // 系统下载缓存根目录
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            //文件不存在
            return null;
        }
        try {
            //读取数据
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] b = new byte[bis.available()];
            bis.read(b);
            return new String(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //文件保存函数
    public static void saveFile(String content, String fileName) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + fileName;
            Log.i("file","SD卡目录");
        } else{ // 系统下载缓存根目录
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + fileName;
            Log.i("file","下载缓存目录");}
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                file.delete();  //删除已经存在的文件
            }
            if (!file.exists()) {
                File dir = new File(file.getParent());
                Log.i("file",dir+"是dir");
                dir.mkdirs();
                file.createNewFile();   //创建文件
                Log.i("file","创建文件");
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(content.getBytes());    //向文件写入数据
            Log.i("file","写入文件");
            outStream.close();
            Log.i("file","写入完成"+file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
