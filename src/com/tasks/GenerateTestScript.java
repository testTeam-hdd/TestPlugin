package com.tasks;

import com.Utils.EmptyUtils;
import com.Vo.TestScript;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * dongdong Created by 下午3:34  2018/7/9
 */
public class GenerateTestScript {
    private String testClass;
    private String testMethod;
    private String testScriptDescription;
    private List<String> dbList;
    private Object request;
    private Object response;
    private List<String> dbCheckList;
    private String packageName;
    private String testPackageName;
    private String requestPackageName;
    private String responsePackageName;
    private String author;
    private Date date;

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

    private StringBuffer spliceScript() {
        StringBuffer sb = new StringBuffer("pachage ");
        sb.append(packageName);
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
        }
        sb.append("\r\n");
        if (!EmptyUtils.isEmpty(request)) {
            sb.append("import com.miz.testframework.util.CSVUtil;");
        }
        sb.append("\r\n");
        sb.append("import ");
        if (!EmptyUtils.isEmpty(requestPackageName)) {
            sb.append(requestPackageName);
        }
        sb.append("\r\n");
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
        sb.append("protected static Logger logger = LoggerFactory.getLogger(");
        sb.append(testMethod);
        sb.append("NormalTest.class);");
        sb.append("\r\n");
        sb.append("@Autowired");
        sb.append("\r\n");
        sb.append("private ");
        sb.append(testClass);
        sb.append(" ");
        sb.append(testClass);
        sb.append(";");
        sb.append("\r\n");
        sb.append("@Test(dataProvider = \"CsvDataProvider\", description =\"");
        sb.append(testScriptDescription);
        sb.append("\")\r\n");
        sb.append("public void ");
        sb.append(testMethod);
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        sb.append("");
        return sb;
    }

    public static void main(String[] args) {
        TestScript testScript = new TestScript();
        testScript.setAuthor("dongdong");
        List<String> list = new ArrayList<String>();
        list.add("123");
        testScript.setDbCheckList(list);
        testScript.setDbList(list);
        testScript.setPackageName("com.Utils");
        testScript.setRequest(testScript);
        testScript.setRequestPackageName("com.Utils");
        testScript.setResponse(testScript);
        testScript.setResponsePackageName("com.Utils");
        testScript.setTestClass("Invest");
        testScript.setTestMethod("invest");
        testScript.setTestPackageName("com.Utils");
        testScript.setTestScriptDescription("哈哈");
        GenerateTestScript generateTestScript = new GenerateTestScript(testScript);
        generateTestScript.spliceScript();
        StringBuffer stringBuffer = generateTestScript.spliceScript();
        System.out.println(stringBuffer);
    }
}
