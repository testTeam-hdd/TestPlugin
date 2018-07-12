package com.tasks;

import com.Utils.EmptyUtils;
import com.Vo.RequestParam;
import com.Vo.TestScript;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * dongdong Created by 下午3:34  2018/7/9
 */
public class GenerateTestScript {
    private String testClass;
    private String testMethod;
    private String testScriptDescription;
    private List<String> dbList;
    private List<RequestParam> request;
    private Map<String, String> response;
    private List<String> dbCheckList;
    private String packageName;
    private String testPackageName;
    private String requestPackageName;
    private String responsePackageName;
    private String author;
    private Date date;
    private int requestIndex;
    private String centent;

    public GenerateTestScript(TestScript testScript) {
        this.dbCheckList = testScript.getDbCheckList();
        this.dbList = testScript.getDbList();
        this.request = testScript.getRequest();
        this.response = testScript.getResponse();
        this.testClass = testScript.getTestClass();
        this.testMethod = testScript.getTestMethod();
        this.testScriptDescription = testScript.getTestScriptDescription();
        this.packageName = testScript.getPackageName();
        this.testPackageName = testScript.getTestPackageName();
        this.requestPackageName = testScript.getRequestPackageName();
        this.responsePackageName = testScript.getResponsePackageName();
        this.author = testScript.getAuthor();
        this.date = new Date();
    }

    private void spliceScript() {
        StringBuffer sb = new StringBuffer("package ");
        sb.append(packageName);
        sb.append(";");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("import org.slf4j.Logger;");
        sb.append("\r\n");
        sb.append("import org.slf4j.LoggerFactory;");
        sb.append("\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;");
        sb.append("\r\n");
        sb.append("import org.testng.Assert;");
        sb.append("\r\n");
        sb.append("import org.testng.annotations.Test;");
        sb.append("\r\n");
        sb.append("import ");
        sb.append(testPackageName);
        sb.append(";");
        sb.append("\r\n");
        sb.append("import com.miz.autotest.base.AutoBaseTest;");
        sb.append("\r\n");
        if (dbCheckList.size() != 0) {
            sb.append("import com.miz.testframework.database.DBcheckUtil;");
        }
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(response)) {
            sb.append("import com.miz.testframework.objckeck.ObjectCheckUtil;");
        }
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(responsePackageName)) {
            sb.append("import ");
            sb.append(responsePackageName);
            sb.append(";");
        }
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(request)) {
            sb.append("import com.miz.testframework.util.CSVUtil;");
        }
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(requestPackageName)) {
                sb.append("import ");
                sb.append(requestPackageName);
                sb.append(";");
                sb.append("\r\n");
        }
        sb.append("/**");
        sb.append("\r\n");
        sb.append(" * @author ");
        sb.append(author);
        sb.append("\r\n");
        sb.append(" * @date ");
        sb.append(date);
        sb.append("\r\n");
        sb.append(" */");
        sb.append("\r\n");
        sb.append("public class ");
        sb.append(testMethod);
        sb.append("NormalTest extends AutoBaseTest{");
        sb.append("\r\n");
        sb.append("\tprotected static Logger logger = LoggerFactory.getLogger(");
        sb.append(testMethod);
        sb.append("NormalTest.class);");
        sb.append("\r\n");
        sb.append("\t@Autowired");
        sb.append("\r\n");
        sb.append("\tprivate ");
        sb.append(testClass);
        sb.append(" ");
        sb.append(subString(testClass));
        sb.append(";");
        sb.append("\r\n");
        sb.append("\t@Test(dataProvider = \"CsvDataProvider\", description =\"");
        sb.append(testScriptDescription);
        sb.append("\")\r\n");
        sb.append("\tpublic void ");
        sb.append(testMethod);
        sb.append("(final String caseId, final String description,");
        if (!EmptyUtils.isEmpty(response)) {
            sb.append("final String ");
            sb.append("response,");
        }
        if (!EmptyUtils.isEmpty(request)) {
            int index = 1;
            for (RequestParam req : request) {
                if (req.getType().equals("Object")) {
                    sb.append("final String ");
                    sb.append("request");
                    sb.append(",");
                    requestIndex = index;
                } else {
                    sb.append("final ");
                    sb.append(req.getType());
                    sb.append(" request");
                    sb.append(index);
                    sb.append(",");
                    index++;
                }
            }
        }
        if (!EmptyUtils.isEmpty(dbList)) {
            for (String db : dbList) {
                sb.append("final String ");
                sb.append(db);
                sb.append(",");
            }
        }
        if (!EmptyUtils.isEmpty(dbCheckList)) {
            for (String dbCheck : dbCheckList) {
                sb.append("final String  ");
                sb.append(dbCheck);
                sb.append(",");
            }
        }
        sb.append("int index ) {");
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(request)) {
            for (RequestParam req : request) {
                if (req.getType().equals("Object")) {
                    sb.append("\t\t");
                    sb.append(req.getValue());
                    sb.append(" ");
                    sb.append(subString(req.getValue()));
                    sb.append("= CSVUtil.requestfromCSV(request,");
                    sb.append(req.getValue());
                    sb.append(".class,index);");
                }
            }
        }
        sb.append("\r\n");
        sb.append("\t\tthis.cleanDB();");
        sb.append("\r\n");
        sb.append("\t\ttry {");
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(response)) {
            for (String req : response.keySet()) {
                sb.append("\t\t\t");
                if (req.equals("Object")) {
                    sb.append(response.get(req));
                    sb.append(" ");
                    sb.append("my");
                    sb.append(subString(response.get(req)));
                    sb.append(" = ");
                } else {
                    sb.append(req);
                    sb.append(" myResponse");
                    sb.append(" = ");
                }
            }
        }
        sb.append(subString(testClass));
        sb.append(".");
        sb.append(testMethod);
        sb.append("(");
        for (int i = 1; i <= request.size(); i++) {
            if (i == requestIndex) {
                for (RequestParam req : request) {
                    if (req.getType().equals("Object")) {
                        sb.append(subString(req.getValue()));
                    }
                }
            }else{
                sb.append("request");
                sb.append(i);
            }
            if (!(i == request.size())) {
                sb.append(",");
            }
        }
        sb.append(");\r\n");
        if (!EmptyUtils.isEmpty(response)) {
            sb.append("\t\t\tAssert.assertTrue(ObjectCheckUtil.check(");
            sb.append("response,");
            for (String req : response.keySet()) {
                if (req.equals("Object")) {
                    sb.append("my");
                    sb.append(subString(response.get(req)));
                } else {
                    sb.append("myResponse");
                }
            }
            sb.append(",");
            sb.append("index));\r\n");
        }
        for (String dbCheck : dbCheckList) {
            sb.append("\t\t\tAssert.assertTrue(DBcheckUtil.DBCheckWithoutCondition(");
            sb.append(dbCheck);
            sb.append(", index + 1), \"DB结果校验失败\");\r\n");
        }
        sb.append("\t\t} catch (Exception e) {\r\n");
        sb.append("\t\t\tlogger.error(e.getMessage());\r\n");
        sb.append("\t\t\tAssert.assertTrue(false);\r\n");
        sb.append("\t\t} finally {\r\n");
        sb.append("\t\t\tthis.cleanDB();\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        sb.append("\tpublic void cleanDB() {\r\n");
        sb.append("\r\n");
        sb.append("\t}\r\n");
        sb.append("}\r\n");
        this.centent = sb.toString();
    }
    /**
     * 生成测试脚本
     *
     * @param classPath 类文件路径
     * @param className 类文件名称
     */
    public void writeToFile( String classPath, String className) {
        try {
            this.spliceScript();
            File floder = new File(classPath);
            if (!floder.exists()) {
                floder.mkdirs();
            }else{
                classPath.toLowerCase();
            }

            File file = new File(classPath + "/" + className);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.centent);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //首字母转小写
    private String subString(String str) {
        char substring = str.substring(0, 1).charAt(0);
        if (Character.isUpperCase(substring)) {
            substring = Character.toLowerCase(substring);
        }
        str = substring + str.substring(1);
        return str;
    }
}
