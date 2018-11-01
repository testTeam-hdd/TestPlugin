package com.Vo;

import com.intellij.psi.PsiType;

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
    private List<PsiType> allRequestParem;
    private List<String> request;//请求类型为对象的请求对象集合
    private String response;
    private List<String> dbCheckList;
    private List<String> objectList;
    private String packageName;
    private List<String> requestPackageName;
    private List<String> dbMapperPackageName;//dbmapper包名集合
    private List<String> dbPackageName;//db实体类包名集合
    private String responsePackageName;
    private String author;
    private List<String> tableName;//表名
    private boolean isNormal;
    private Date date;

    public List<String> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<String> objectList) {
        this.objectList = objectList;
    }

    public List<String> getTableName() {
        return tableName;
    }

    public void setTableName(List<String> tableName) {
        this.tableName = tableName;
    }

    public List<String> getDbMapperPackageName() {
        return dbMapperPackageName;
    }

    public List<String> getDbPackageName() {
        return dbPackageName;
    }

    public void setDbPackageName(List<String> dbPackageName) {
        this.dbPackageName = dbPackageName;
    }

    public void setDbMapperPackageName(List<String> dbMapperPackageName) {
        this.dbMapperPackageName = dbMapperPackageName;
    }


    public List<String> getRequest() {
        return request;
    }

    public void setRequest(List<String> request) {
        this.request = request;
    }

    public boolean getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(boolean isNormal) {
        this.isNormal = isNormal;
    }

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

    public List<String> getRequestPackageName() {
        return requestPackageName;
    }

    public void setRequestPackageName(List<String> requestPackageName) {
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

    public List<PsiType> getAllRequestParem() {
        return allRequestParem;
    }

    public void setAllRequestParem(List<PsiType> allRequestParem) {
        this.allRequestParem = allRequestParem;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getDbCheckList() {
        return dbCheckList;
    }

    public void setDbCheckList(List<String> dbCheckList) {
        this.dbCheckList = dbCheckList;
    }

}
