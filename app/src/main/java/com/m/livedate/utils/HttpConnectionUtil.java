package com.m.livedate.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * 项目名:    ThreeCash
 * 包名       com.dfjt.cash.utils
 * 文件名:    HttpConnectionUtil
 * 创建者:    唐洋
 * 创建时间:  2018/7/3 on 9:49
 * 描述:     TODO
 */
public class HttpConnectionUtil {
    /**
     * @param urlPath     下载路径
     * @param downloadDir 下载存放目录
     * @return 返回下载文件
     */
    public static File downloadFile(String urlPath, String downloadDir) {
        File file = null;
            try {
                // 统一资源
                URL url = new URL(urlPath);
                // 连接类的父类，抽象类
                URLConnection urlConnection = url.openConnection();
                // http的连接类
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                // 设定请求的方法，默认是GET
                httpURLConnection.setRequestMethod("GET");
                // 设置字符编码
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
                httpURLConnection.connect();

                // 文件大小
                int fileLength = httpURLConnection.getContentLength();

                // 文件名
                String filePathUrl = httpURLConnection.getURL().getFile();
                String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

                System.out.println("file length---->" + fileLength);

                URLConnection con = url.openConnection();

                BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

                String path = downloadDir + File.separatorChar + fileFullName;
                file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                OutputStream out = new FileOutputStream(file);
                int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }

    }

}
