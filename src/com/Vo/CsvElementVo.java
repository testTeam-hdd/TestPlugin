package com.Vo;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;

import java.util.List;

/**
 * dongdong Created by 上午10:23  2018/9/14
 */
public class CsvElementVo {

    private List<PsiClass> request;
    private PsiClass response;
    private List<PsiClass> dbInsert;
    private List<String> dbCheck;
    private List<PsiClass> objectCheck;
    private boolean isNormal;
    private String path;
    private String testMethodName;

    public List<PsiClass> getObjectCheck() {
        return objectCheck;
    }

    public void setObjectCheck(List<PsiClass> objectCheck) {
        this.objectCheck = objectCheck;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public List<PsiClass> getRequest() {
        return request;
    }

    public void setRequest(List<PsiClass> request) {
        this.request = request;
    }

    public PsiClass getResponse() {
        return response;
    }

    public void setResponse(PsiClass response) {
        this.response = response;
    }

    public List<PsiClass> getDbInsert() {
        return dbInsert;
    }

    public void setDbInsert(List<PsiClass> dbInsert) {
        this.dbInsert = dbInsert;
    }

    public List<String> getDbCheck() {
        return dbCheck;
    }

    public void setDbCheck(List<String> dbCheck) {
        this.dbCheck = dbCheck;
    }
}
