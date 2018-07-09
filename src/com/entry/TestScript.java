package com.entry;

import clojure.lang.Obj;

import java.util.List;

/**
 * dongdong Created by 下午3:13  2018/7/9
 */
public class TestScript {
    private String testClass;
    private String testMethod;
    private String testScriptDescription;
    private List<String> dbList;
    private Object request;
    private Object response;
    private List<String> dbCheckList;
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getTestScriptDescription() {
        return testScriptDescription;
    }

    public void setTestScriptDescription(String testScriptDescription) {
        this.testScriptDescription = testScriptDescription;
    }

    public List<String> getDbList() {
        return dbList;
    }

    public void setDbList(List<String> dbList) {
        this.dbList = dbList;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public List<String> getDbCheckList() {
        return dbCheckList;
    }

    public void setDbCheckList(List<String> dbCheckList) {
        this.dbCheckList = dbCheckList;
    }

}
