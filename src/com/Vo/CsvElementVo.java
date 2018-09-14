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
    private List<PsiClass> dbCheck;
    private boolean isNormal;
    private String path;
    private String testClassName;


    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
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

    public List<PsiClass> getDbCheck() {
        return dbCheck;
    }

    public void setDbCheck(List<PsiClass> dbCheck) {
        this.dbCheck = dbCheck;
    }
}
