package com.cloudmusic.controller.download;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 资源下载相关接口
 */
@RequestMapping("/down")
@Controller
public class DownloadController {


    @RequestMapping("/qq/mv")
    public void downloadMv(String url, String name, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String userAgent = request.getHeader("User-agent");
        byte[] bytes = userAgent.contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
        name = new String(bytes, "ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment;fileName="+name+".mp4");
        //下载文件的地址
        Connection connection = Jsoup.connect(url);
        Connection.Response res = connection.method(Connection.Method.GET).ignoreContentType(true).maxBodySize(1024*1024*1024*1024).timeout(3*1000*1000).execute();
        BufferedInputStream bufferedInputStream = res.bodyStream();
        System.out.println(res.contentType());
        saveFile(bufferedInputStream,response.getOutputStream());//保存文件的地址
    }


    /**
     * 保存文件到本地
     * @param bufferedInputStream
     */
    public static void saveFile(BufferedInputStream bufferedInputStream,OutputStream outputStream) throws IOException {
        //一次最多读取1k
        byte[] buffer = new byte[1024];
        //实际读取的长度
        int readLenghth;
        //创建的一个写出的缓冲流
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        //文件逐步写入本地
        while ((readLenghth = bufferedInputStream.read(buffer,0,1024)) != -1){//先读出来，保存在buffer数组中
            bufferedOutputStream.write(buffer,0,readLenghth);//再从buffer中取出来保存到本地
        }
        //关闭缓冲流
        bufferedOutputStream.close();
        outputStream.close();
        bufferedInputStream.close();
        bufferedOutputStream.flush();
    }
}
