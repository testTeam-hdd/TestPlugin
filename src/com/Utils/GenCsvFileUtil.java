package com.Utils;


import com.miz.testframework.util.CSVUtil;

import java.io.File;

/**
 * Created by chuwenjun on 2018/7/9 上午11:04
 */
public class GenCsvFileUtil {
    /**
     * 根据对象生成csv文件
     */

    public static void createCsvForObject(String methodName,Boolean isNormal,String objectName){
        String suffix;
        String objectcsvPath;
        String testAbsolutePath;
        if(isNormal){
            suffix="Normal";
        }else{
            suffix="FuncException";
        }
        testAbsolutePath=System.getProperty("user.dir");
        objectcsvPath = testAbsolutePath + "/src/test/resources/testers"+ File.separator+suffix.toLowerCase()+File.separator+methodName+File.separator+objectName+".csv";
        CSVUtil.createVerticalCsvForCheck(objectName.getClass(), objectcsvPath);

    }

    /**
     *  生成需要校验表的csv文件
     */

    public static void createCsvForDbCheck(String methodName,Boolean isNormal,String objectName){
        String suffix;
        String objectcsvPath;
        String testAbsolutePath;
        if(isNormal){
            suffix="Normal";
        }else{
            suffix="FuncException";
        }
        testAbsolutePath=System.getProperty("user.dir");
        objectcsvPath = testAbsolutePath + "/src/test/resources/testers"+ File.separator+suffix.toLowerCase()+File.separator+methodName+File.separator+objectName+".csv";
        CSVUtil.createVerticalCsvForCheck(objectName.getClass(), objectcsvPath);

    }

    /**
     * 生成需要插入表的csv文件
     */

    public static void createCsvForDbInsert(String methodName,Boolean isNormal,String objectName){
        String suffix;
        String objectcsvPath;
        String testAbsolutePath;
        if(isNormal){
            suffix="Normal";
        }else{
            suffix="FuncException";
        }
        testAbsolutePath=System.getProperty("user.dir");
        objectcsvPath = testAbsolutePath + "/src/test/resources/testers"+ File.separator+suffix.toLowerCase()+File.separator+methodName+File.separator+objectName+".csv";
        CSVUtil.createVerticalCsvForCheck(objectName.getClass(),objectcsvPath);

    }

    public static void main(String[] args) {
//        createCsvForObject();
    }
}