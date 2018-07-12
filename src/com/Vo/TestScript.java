package com.Vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * dongdong Created by 下午3:13  2018/7/9
 */
public class TestScript {
    private String testClass;
    private String testMethod;
    private String testScriptDescription;
    private String testPackageName;
    private List<String> dbList;
    private List<RequestParam> request;
    private Map<String, String> response;
    private List<String> dbCheckList;
    private String packageName;
    private String requestPackageName;
    private String responsePackageName;
    private String author;
    private Date date;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String $author) {
        this.author = $author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String $date) {
        this.date = date;
    }

    public String getRequestPackageName() {
        return requestPackageName;
    }

    public void setRequestPackageName(String requestPackageName) {
        this.requestPackageName = requestPackageName;
    }

    public String getResponsePackageName() {
        return responsePackageName;
    }

    public void setResponsePackageName(String responsePackageName) {
        this.responsePackageName = responsePackageName;
    }

    public String getTestPackageName() {
        return testPackageName;
    }

    public void setTestPackageName(String testPackageName) {
        this.testPackageName = testPackageName;
    }

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

    public List<RequestParam> getRequest() {
        return request;
    }

    public void setRequest(List<RequestParam> request) {
        this.request = request;
    }

    public Map<String, String> getResponse() {
        return response;
    }

    public void setResponse(Map<String, String> response) {
        this.response = response;
    }

    public List<String> getDbCheckList() {
        return dbCheckList;
    }

    public void setDbCheckList(List<String> dbCheckList) {
        this.dbCheckList = dbCheckList;
    }

}
