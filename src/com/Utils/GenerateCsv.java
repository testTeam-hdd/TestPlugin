package com.Utils;

import au.com.bytecode.opencsv.CSVWriter;
import com.Vo.CsvElementVo;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * dongdong Created by 上午10:05  2018/9/14
 */
public class GenerateCsv {

    private List<PsiClass> requests;
    private PsiClass response;
    private List<PsiClass> dbInserts;
    private List<PsiClass> dbChecks;
    private boolean isNormal;
    private String path;
    private String testMethodName;//测试类类名

    public GenerateCsv(CsvElementVo csvElementVo) {
        this.requests = csvElementVo.getRequest();
        this.response = csvElementVo.getResponse();
        this.dbChecks = csvElementVo.getDbCheck();
        this.dbInserts = csvElementVo.getDbInsert();
        this.isNormal = csvElementVo.isNormal();
        this.path = csvElementVo.getPath();
        this.testMethodName = csvElementVo.getTestMethodName();
        getPath();
        generateRequestCsv();
        generateResponseCsv();
        generateDbInsertCsv();
        generateDbCheckCsv();
    }

    /**
     * 根据isNormal确定路径
     */
    private void getPath() {
        if (isNormal) {
            path = path + "normal/" + testMethodName + "/";
        } else {
            path = path + "funcExp/" + testMethodName + "/";
        }
    }

    /**
     * 生成请求csv文件
     */
    private void generateRequestCsv() {
        String requestPath = null;
        int index = 1;
        try {
            if (requests.size() != 0 && requests != null) {
                for (PsiClass request : requests) {
                    requestPath = path + "request" + index + "_" + request.getName() + ".csv";
                    createVerticalCsvForCheck(request, requestPath);
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成响应csv文件
     */
    private void generateResponseCsv() {
        String responsePath = null;
        responsePath = path + "response.csv";
        try {
            if (!EmptyUtils.isEmpty(response)) {
                createVerticalCsvForCheck(response, responsePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成dbInsertcsv文件
     */
    private void generateDbInsertCsv() {
        String dbInsertPath = null;
        int index = 1;
        try {
            if (dbInserts.size() != 0 && dbInserts != null) {
                for (PsiClass dbInsert : dbInserts) {
                    dbInsertPath = path + "dbInsert" + index + "_" + dbInsert.getName() + ".csv";
                    createVerticalCsvForCheck(dbInsert, dbInsertPath);
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成dbCheckcsv文件
     */
    private void generateDbCheckCsv() {
        String dbCheckPath = null;
        int index = 1;
        try {
            if (dbChecks.size() != 0 && dbChecks != null) {
                for (PsiClass dbCheck : dbChecks) {
                    dbCheckPath = path + "dbCheck" + index + "_" + dbCheck.getName() + ".csv";
                    createVerticalCsvForCheck(dbCheck, dbCheckPath);
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成字段为竖向排列的校验文件
     *
     * @param psiClass 需要生成的类
     * @param path     生成文件的绝对路径
     */
    public static void createVerticalCsvForCheck(PsiClass psiClass, String path) {
        try {
            //组装文件路径
            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            file.createNewFile();


            List<String[]> outputValues = new ArrayList<String[]>();
            //组装CSV文件第一行，标题行
            List<String> header = new ArrayList<String>();
            header.add("class");
            header.add("property");
            header.add("flag");
            header.add("exp");

            outputValues.add(header.toArray(new String[header.size()]));
            List<PsiField> fileNames = Arrays.asList(psiClass.getAllFields());
            String className = psiClass.getName();
            header.add(className.substring(className.lastIndexOf(".") + 1, className.length()));
            int i = 1;
            for (PsiField filename : fileNames) {
                if (filename.equals("id") || filename.equals("serialVersionUID")) {
                    continue;
                }
                if (filename.equals("createTime") || filename.equals("updateTime")) {
                    continue;
                }
                List<String> value = new ArrayList<String>();
                if (1 == i) {
                    //如果是第一个生成字段，则内容需要包含class名
                    value.add(className);
                } else {
                    value.add("");
                }
                value.add(filename.getName());
                value.add("Y");

                outputValues.add(value.toArray(new String[value.size()]));

                i++;
            }
            //初始化写入文件
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
            } catch (Exception e) {
                throw e;
            }
            //将生成内容写入CSV文件
            try {
                OutputStreamWriter osw = null;
                osw = new OutputStreamWriter(outputStream);
                CSVWriter csvWriter = new CSVWriter(osw);
                csvWriter.writeAll(outputValues);
                csvWriter.close();
            } catch (Exception e) {
                throw e;
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * 生成字段为横向排列的校验文件
     *
     * @param psiClass 需要生成的类
     * @param path     生成文件的绝对路径
     */

    public static void createTransverseCsvForCheck(PsiClass psiClass, String path) {
        try {
            List<PsiField> fileNames = Arrays.asList(psiClass.getAllFields());
            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            file.createNewFile();
            List<String[]> outputValues = new ArrayList<String[]>();
            List<String> header = new ArrayList<String>();
            String className = psiClass.getName();
            header.add(className.substring(className.lastIndexOf(".") + 1, className.length()));
            for (PsiField name : fileNames) {
                if (name.equals("id") || name.equals("serialVersionUID")) {
                    continue;
                }
                if (name.equals("createTime") || name.equals("updateTime")) {
                    continue;
                }
                header.add(name.getName());
            }
            outputValues.add(header.toArray(new String[header.size()]));
            //初始化写入文件
            OutputStream outputStream = null;
            outputStream = new FileOutputStream(file);
            //将生成内容写入CSV文件
            OutputStreamWriter osw = null;
            osw = new OutputStreamWriter(outputStream);
            CSVWriter csvWriter = new CSVWriter(osw);
            csvWriter.writeAll(outputValues);
            csvWriter.close();

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
