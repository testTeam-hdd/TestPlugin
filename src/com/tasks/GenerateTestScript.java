package com.tasks;

import com.Utils.EmptyUtils;
import com.Utils.PsiUtil;
import com.Vo.TestScript;
import com.intellij.psi.PsiType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * dongdong Created by 下午3:34  2018/7/9
 */
public class GenerateTestScript {

    public static final String[] TYPE = {"byte", "int", "double", "char", "float", "long", "Long", "short", "boolean", "String", "void"};

    private String testClass;//测试类
    private String testMethod;//测试方法
    private String testScriptDescription;//测试脚本描述
    private List<String> dbList;//需要插入的db数据列表
    private Map<PsiType, String> allRequestParem;//被测接口的所有请求参数
    private List<String> request;//请求类型为对象的请求对象集合
    private Object response;//响应对象
    private List<String> dbCheckList;//需要校验的数据对象列表
    private String packageName;//测试类包名
    private String testPackageName;//被测试类包名
    private List<String> requestPackageName;//请求对象包名集合
    private String responsePackageName;//响应对象包名
    private List<String> dbMapperPackageName;//dbMapper对象包名集合
    private List<String> dbPackageName;//db实体类包名集合
    private String author;//作者
    private Date date;//日期
    private String centent;

    public GenerateTestScript(TestScript testScript) {
        this.dbCheckList = testScript.getDbCheckList();
        this.dbList = testScript.getDbList();
        this.testClass = testScript.getTestClass();
        this.testMethod = testScript.getTestMethod();
        this.testScriptDescription = testScript.getTestScriptDescription();
        this.packageName = testScript.getPackageName();
        this.testPackageName = testScript.getTestPackageName();
        this.requestPackageName = testScript.getRequestPackageName();
        this.responsePackageName = testScript.getResponsePackageName();
        this.author = testScript.getAuthor();
        this.date = new Date();
        this.request = testScript.getRequest();
        this.response = testScript.getResponse();
        this.allRequestParem = testScript.getAllRequestParem();
        this.dbMapperPackageName = testScript.getDbMapperPackageName();
        this.dbPackageName = testScript.getDbPackageName();
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

        for (String db:dbMapperPackageName) {
            sb.append("import ");
            sb.append(db);
            sb.append(";\r\n");
        }

        for (String db:dbPackageName) {
            sb.append("import ");
            sb.append(db);
            sb.append(";\r\n");
        }

        sb.append("import com.miz.autotest.base.AutoBaseTest;");
        sb.append("\r\n");
        if (dbCheckList.size() != 0) {
            sb.append("import com.miz.testframework.database.DBcheckUtil;");
        }
        if (!EmptyUtils.isEmpty(response)) {
            sb.append("\r\n");
            sb.append("import com.miz.testframework.objckeck.ObjectCheckUtil;");
        }
        if (!EmptyUtils.isEmpty(responsePackageName)) {
            sb.append("\r\n");
            sb.append("import ");
            sb.append(responsePackageName);
            sb.append(";");
        }
        if (!EmptyUtils.isEmpty(request)) {
            sb.append("\r\n");
            sb.append("import com.miz.testframework.util.CSVUtil;");
        }
        if (!EmptyUtils.isEmpty(requestPackageName)) {
            sb.append("\r\n");
            for (String requestPackage : requestPackageName) {
                sb.append("import ");
                sb.append(requestPackage);
                sb.append(";");
                sb.append("\r\n");
            }
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

        for (String db:dbList) {
            sb.append("\t@Autowired");
            sb.append("\r\n");
            sb.append("\tprivate ");
            sb.append(db);
            sb.append("Mapper");
            sb.append(" ");
            sb.append(subString(db));
            sb.append("Mapper;");
            sb.append("\r\n");
        }

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
        if (!EmptyUtils.isEmpty(allRequestParem)) {
            for (PsiType key : allRequestParem.keySet()) {
                if (Arrays.asList(TYPE).contains(key.getPresentableText())) {
                    sb.append("final ");
                    sb.append(key.getPresentableText());
                    sb.append(allRequestParem.get(key));
                    sb.append(",");
                } else if(!Arrays.asList(TYPE).contains(key.getPresentableText())&&!PsiUtil.isEnum(key)&&!PsiUtil.isCollection(key)){
                    sb.append("final String");
                    sb.append(" ");
                    sb.append(subString(key.getPresentableText()));
                    sb.append(",");
                }
            }
        }
        if (!EmptyUtils.isEmpty(dbList)) {
            for (String db : dbList) {
                sb.append("final String ");
                sb.append(subString(db));
                sb.append(",");
            }
        }
        if (!EmptyUtils.isEmpty(dbCheckList)) {
            for (String dbCheck : dbCheckList) {
                sb.append("final String  ");
                sb.append(subString(dbCheck));
                sb.append(",");
            }
        }
        sb.append("int index ) {");
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(request)) {
            for (String req : request) {
                sb.append("\t\t");
                sb.append(req);
                sb.append(" ");
                sb.append("my");
                sb.append(req);
                sb.append("= CSVUtil.requestfromCSV(");
                sb.append(subString(req));
                sb.append(",");
                sb.append(req);
                sb.append(".class,index);");
                sb.append("\r\n");
            }
        }
        sb.append("\r\n");
        sb.append("\t\tthis.cleanDB();");
        sb.append("\r\n");
        for (String db:dbList) {
            sb.append("\t\tsuper.insertDB(");
            sb.append(subString(db));
            sb.append(",");
            sb.append(subString(db));
            sb.append("Mapper,");
            sb.append(db);
            sb.append(".class);");
            sb.append("\r\n");
        }
        sb.append("\t\ttry {");
        sb.append("\r\n");
        sb.append("\t\t\t");
        if (!EmptyUtils.isEmpty(response)) {
            if (Arrays.asList(TYPE).contains(response)) {
                sb.append(response);
                sb.append(" ");
                sb.append("myResponse");
                sb.append(" = ");
            } else {
                sb.append(response);
                sb.append(" ");
                sb.append("my");
                sb.append(response);
                sb.append(" ");
                sb.append(" = ");
            }

        }
        sb.append(subString(testClass));
        sb.append(".");
        sb.append(testMethod);
        sb.append("(");
        for (PsiType key : allRequestParem.keySet()) {
            int requestIndex = 1;
            if (Arrays.asList(TYPE).contains(key.getPresentableText())) {
                sb.append(allRequestParem.get(key));
            } else {
                sb.append("my");
                sb.append(key.getPresentableText());
            }
            if (!(requestIndex == allRequestParem.size())) {
                sb.append(",");
            }

            requestIndex += 1;
        }
        sb.append(");\r\n");
        if (!EmptyUtils.isEmpty(response)) {
            sb.append("\t\t\tAssert.assertTrue(ObjectCheckUtil.check(");
            sb.append("response,");
            if (!Arrays.asList(TYPE).contains(response)) {
                sb.append("my");
                sb.append(response);
            } else {
                sb.append("myResponse");
            }
            sb.append(",");
            sb.append("index));\r\n");
        }
        for (String dbCheck : dbCheckList) {
            sb.append("\t\t\tAssert.assertTrue(DBcheckUtil.DBCheckWithoutCondition(");
            sb.append(subString(dbCheck));
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
    public void writeToFile(String classPath, String className) {
        try {
            this.spliceScript();
            File floder = new File(classPath);
            if (!floder.exists()) {
                floder.mkdirs();
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
    public static String subString(String str) {
        char substring = str.substring(0, 1).charAt(0);
        if (Character.isUpperCase(substring)) {
            substring = Character.toLowerCase(substring);
        }
        str = substring + str.substring(1);
        return str;
    }
}
