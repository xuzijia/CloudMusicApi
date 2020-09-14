package com.cloudmusic.utils;


import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
        JSONObject jsonObject = new JSONObject();
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
                    ScriptObjectMirror object= (ScriptObjectMirror)engine.get("flashvars_"+number);
                    Object quality_480p = object.get("quality_480p");
                    Object quality_240p = object.get("quality_240p");
                    Object quality_720p = object.get("quality_720p");
                    Object quality_1080p = object.get("quality_1080p");
                    System.out.println("240p: "+quality_240p);
                    System.out.println("480p: "+quality_480p);
                    System.out.println("720p: "+quality_720p);
                    System.out.println("1080p: "+quality_1080p);
                    jsonObject.put("240p",quality_240p);
                    jsonObject.put("480p",quality_480p);
                    jsonObject.put("720p",quality_720p);
                    jsonObject.put("1080p",quality_1080p);

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
        return jsonObject;
    }

}
