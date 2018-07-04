package com.plugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * dongdong Created by 下午5:12  2018/6/25
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir");
        String classPath = path +"/src/com/test/";
        String className = "Next";
        File floder = new File(classPath);
        if (!floder.exists()) {
            floder.mkdirs();
        }

        File file = new File(classPath + "/" + className);
        if (!file.exists()) {
            file.createNewFile();
        }
        String content = "GoodBey My Love!";
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }
}
