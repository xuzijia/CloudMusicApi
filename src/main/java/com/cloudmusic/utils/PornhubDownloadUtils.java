package com.cloudmusic.utils;


import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
public class PornhubDownloadUtils {
    /** 信任任何站点 */
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static JSONObject getDownloadData(String url) throws Exception{
        JSONObject returnData = new JSONObject();
        trustEveryone();
        Document post = Jsoup.connect(url).timeout(300000).
               get();
        String data  =post.toString();
        //正则匹配url
        String pattern ="var flashvars_(\\d+) = ([\\d\\D]*)var playerIssuesUrl";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(data);
        if(matcher.find()){
            //拿到scirpt 源代码
            String srcData = matcher.group(0);
            //拿到变量id
            String number = matcher.group(1);

            //执行script代码 拿出变量
            ScriptEngineManager maneger = new ScriptEngineManager();
            ScriptEngine engine = maneger.getEngineByName("JavaScript");

            Reader scriptReader = new InputStreamReader(new ByteArrayInputStream(srcData.getBytes()));
            if (engine != null) {
                try {
                    // JS引擎解析文件
                    engine.eval(scriptReader);
                    JSONObject jsonObject = new JSONObject();
                    ScriptObjectMirror object= (ScriptObjectMirror)engine.get("flashvars_"+number);
                    Object quality_480p = object.get("quality_480p");
                    Object quality_240p = object.get("quality_240p");
                    Object quality_720p = object.get("quality_720p");
                    Object quality_1080p = object.get("quality_1080p");
//                    System.out.println("240p: "+quality_240p);
//                    System.out.println("480p: "+quality_480p);
//                    System.out.println("720p: "+quality_720p);
//                    System.out.println("1080p: "+quality_1080p);
                    //封装数据
                    jsonObject.put("240p",quality_240p);
                    jsonObject.put("480p",quality_480p);
                    jsonObject.put("720p",quality_720p);
                    jsonObject.put("1080p",quality_1080p);
                    returnData.put("video",jsonObject);
                    returnData.put("sourceUrl",object.get("link_url"));
                    returnData.put("title",object.get("video_title"));
                    returnData.put("conver",object.get("image_url"));
                    returnData.put("duration",object.get("video_duration"));

                } catch (ScriptException e) {
                    e.printStackTrace();
                } finally {
                    scriptReader.close();
                }
            } else {
                System.out.println("ScriptEngine create error!");
            }


        }else{
            System.out.println("No Match");
        }
        return returnData;
    }



    public static void main(String[] args) throws Exception {
        System.out.println("-------------------开始批量下载任务-------------------------");
        String prefix="https://cn.pornhub.com";
        //遍历100个页面

        for (int y=10;y<=60;y++){
            System.out.println("正在下载第"+y+"页");
            String url="https://cn.pornhub.com/video/search?search=国产&hd=1&page="+y;
            String html = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).timeout(30000).get().toString();
            List<String> urlList=new ArrayList();
            //System.out.println(html);
            //正则匹配url
            String pattern ="<a href=\"/view_video.php\\?viewkey=(.*)</a>";
            Pattern compile = Pattern.compile(pattern);
            Matcher matcher = compile.matcher(html);
            int i=0;
            while (matcher.find()){
                String href = matcher.group(0);
                Matcher m = Pattern.compile("<a href=\"(.*)\" title").matcher(href);
                while (m.find()) {
                    if(i>3){
                        String data = m.group(1);
                        data=prefix+data;
                        urlList.add(data);
                    }

                }
                i++;

            }
            downVideo(urlList);
            System.out.println("第"+y+"页下载完成");
        }


        System.out.println("-------------------结束批量下载任务-------------------------");
    }


    public static void downVideo(List<String> list) throws Exception {
        //批量下载视频
        String path="/Users/simple/学习视频/";
        String fileName="";
        for(String url:list){

            try {
                JSONObject downloadData = getDownloadData(url);
                String downUrl="";
                fileName=downloadData.getString("title")+".mp4";
                if(downloadData.getJSONObject("video").has("720p")){
                    downUrl=downloadData.getJSONObject("video").getString("720p");
                }else if (downloadData.getJSONObject("video").has("480p")){
                    downUrl=downloadData.getJSONObject("video").getString("480p");
                }else {
                    continue;
                }


                BufferedInputStream bufferedInputStream = Jsoup.connect(downUrl).maxBodySize(2147483647).timeout(3000000).ignoreHttpErrors(true).ignoreContentType(true).execute().bodyStream();
                if(new File(path+fileName).exists()){
                    continue;
                }
                System.out.println(new Date().toString()+" : "+fileName+" <正在开始下载> ："+downUrl);
                saveFile(bufferedInputStream,path+fileName);
            }catch (Exception e){
                System.out.println(fileName+"： 下载失败");
                continue;
            }

        }
    }

    public static void saveFile(BufferedInputStream bufferedInputStream,String savePath) throws IOException {
        FileOutputStream fileOutputStream =null;
        BufferedOutputStream bufferedOutputStream =null;
        try {
            long start = System.currentTimeMillis();
            long size=0;
            //一次最多读取1k
            byte[] buffer = new byte[1024];
            //实际读取的长度
            int readLenghth;
            //根据文件保存地址，创建文件输出流
            fileOutputStream = new FileOutputStream(new File(savePath));
            //创建的一个写出的缓冲流
            bufferedOutputStream =new BufferedOutputStream(fileOutputStream);
            //文件逐步写入本地
            while ((readLenghth = bufferedInputStream.read(buffer,0,1024)) != -1){//先读出来，保存在buffer数组中
                bufferedOutputStream.write(buffer,0,readLenghth);//再从buffer中取出来保存到本地
            }
            long end = System.currentTimeMillis();
            System.out.println("下载完成,耗时："+((end-start)/1000)+"s");
            //关闭缓冲流
        }
        finally {
            if(bufferedInputStream!=null){
                bufferedOutputStream.close();
            }
            if(fileOutputStream!=null){
                fileOutputStream.close();

            }
            if(bufferedInputStream!=null){
                bufferedInputStream.close();
            }

        }


    }

}
