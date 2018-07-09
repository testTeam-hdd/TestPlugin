package com.tasks;

import com.entry.TestScript;

import java.util.List;

/**
 * dongdong Created by 下午3:34  2018/7/9
 */
public class generateTestScript {
    private String testClass;
    private String testMethod;
    private String testScriptDescription;
    private List<String> dbList;
    private Object request;
    private Object response;
    private List<String> dbCheckList;
    private String packageName;

    public generateTestScript(TestScript testScript) {
        this.dbCheckList = testScript.getDbCheckList();
        this.dbList = testScript.getDbList();
        this.request = testScript.getRequest();
        this.response = testScript.getResponse();
        this.testClass = testScript.getTestClass();
        this.testMethod = testScript.getTestMethod();
        this.testScriptDescription = testScript.getTestScriptDescription();
        this.packageName = testScript.getPackageName();
    }

    private void spliceScript() {

    }
}
