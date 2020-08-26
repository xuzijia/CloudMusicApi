package com.cloudmusic.utils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ScriptEngineUtil {
    public static String getSecuritySign(String data) throws IOException {

        ScriptEngineManager maneger = new ScriptEngineManager();
        ScriptEngine engine = maneger.getEngineByName("JavaScript");

        Reader scriptReader = new InputStreamReader(ScriptEngineUtil.class.getResourceAsStream("/qq/sign.js"));
        if (engine != null) {
            try {
                // JS引擎解析文件
                engine.eval(scriptReader);
                if (engine instanceof Invocable) {
                    Invocable invocable = (Invocable) engine;
                    // JS引擎调用方法
                    Object result = invocable.invokeFunction("getSecuritySign",data);
                    return result.toString();
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                scriptReader.close();
            }
        } else {
            System.out.println("ScriptEngine create error!");
        }
        return null;
    }
}
