package com;

import com.Utils.GenerateCsv;
import com.Utils.PsiUtil;
import com.Vo.CsvElementVo;
import com.Vo.TestScript;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;
import com.miz.testframework.util.CSVUtil;
import com.tasks.GenerateTestScript;

import java.util.*;

/**
 * dongdong Created by 下午3:18  2018/6/7
 */
public class GenerateTestCaseAction extends AnAction {

    private Project project;

    //包名
    private String packageName = "src.test.java.com.miz.autotest.servicetest.";
    private String csvPath = "/src/test/resources/testers/";

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(PlatformDataKeys.PROJECT);
        generate();
        refreshProject(e);
    }

    /**
     * 刷新项目
     */
    private void refreshProject(AnActionEvent e) {
        e.getProject().getBaseDir().refresh(false, true);
    }

    /**
     * 初始化Dialog
     */
    private void generate() {
        MyDialog myDialog = new MyDialog(new MyDialog.DialogCallBack() {
            @Override
            public void ok(TestScript testScript) {
                testScript.setResponsePackageName(getResponsePackageName(testScript));
                testScript.setRequestPackageName(getRequestPackageName(testScript));
                testScript.setPackageName(packageName.substring(14) + testScript.getTestClass());
                testScript.setTestPackageName(getTestClassPackage(testScript));
                String response = PsiUtil.getMethodRetureType(project, testScript.getTestClass(), testScript.getTestMethod()).getPresentableText();
                testScript.setResponse(response.equals("void")?null:response);
                testScript.setAllRequestParem(changeType(PsiUtil.getMethodPrame(project, testScript.getTestClass(), testScript.getTestMethod())));
                testScript.setRequest(getRequestObject(testScript));
                String path = getAppPath(testScript);
                GenerateTestScript generateTestScript = new GenerateTestScript(testScript);
                String testClassName = testScript.getTestMethod() + "NormalTest.java";
                generateTestScript.writeToFile(path, testClassName);
                Messages.showInfoMessage(project, "脚本生成成功!", "提示");
            }
        });
        myDialog.setVisible(true);

    }

    /**
     * 获取包路径
     */
    private String getAppPath(TestScript testScript) {
        String packagePath = packageName.replace(".", "/");
        String path = project.getBasePath() + "/" + packagePath + testScript.getTestClass();
        return path;
    }
    /**
     * 获取csv路径
     */
    private String getCsvPath(TestScript testScript) {
        String path = project.getBasePath()  + csvPath;
        return path;
    }
    /**
     * 获取测试方法中，请求对象对应的包名
     */
    private List<String> getRequestPackageName(TestScript testScript) {
        List<String> packageNames = new ArrayList<>();
        String packageName;
        Map<String, String> maps = changeType(PsiUtil.getMethodPrame(project, testScript.getTestClass(), testScript.getTestMethod()));
        for (String key : maps.keySet()) {
            if (!Arrays.asList(GenerateTestScript.TYPE).contains(key)) {
                packageName = PsiUtil.getPackageName(project, key);
                packageName = packageName + "." + key;
                packageNames.add(packageName);
            } else {
                return null;
            }

        }
        return packageNames;
    }

    /**
     * 获取测试方法中，响应对象对应的包名
     */
    private String getResponsePackageName(TestScript testScript) {
        String packageName = null;
        String returnType = PsiUtil.getMethodRetureType(project, testScript.getTestClass(), testScript.getTestMethod()).getPresentableText();
        if (!Arrays.asList(GenerateTestScript.TYPE).contains(returnType)) {
            packageName = PsiUtil.getPackageName(project, returnType);
            packageName = packageName + "." + returnType;
        }
        return packageName;
    }

    /**
     * 获取测试类对应的包名
     */
    private String getTestClassPackage(TestScript testScript) {
        String packageName = PsiUtil.getPackageName(project, testScript.getTestClass());
        packageName = packageName + "." + testScript.getTestClass();
        return packageName;
    }

    /**
     * 获取非基础类型、枚举类型、集合类型的入参集合
     */
    private List<String> getRequestObject(TestScript testScript) {
        List<String> requests = new ArrayList<>();
        Map<PsiType, String> maps = PsiUtil.getMethodPrame(project, testScript.getTestClass(), testScript.getTestMethod());
        for (PsiType key : maps.keySet()) {
            if (!Arrays.asList(GenerateTestScript.TYPE).contains(key.getPresentableText().toString())&&!PsiUtil.isEnum(key)&&!PsiUtil.isCollection(key)) {
                requests.add(key.getPresentableText().toString());
            } else {
                return null;
            }
        }
        return requests;
    }
    /**
     * 组装参数调用工具类生成csv
     */
    private List<String> generateCsv(TestScript testScript) {
        List<PsiClass> requests = new ArrayList<>();
        List<PsiClass> dbInserts = new ArrayList<>();
        List<PsiClass> dbChecks = new ArrayList<>();

        CsvElementVo csvElementVo = new CsvElementVo();
        csvElementVo.setNormal(testScript.getIsNormal());
        for (String request:testScript.getRequest()){
            PsiClass psiClass = PsiUtil.getPsiClass(project,request);
            requests.add(psiClass);
        }
        csvElementVo.setRequest(requests);
        csvElementVo.setResponse(PsiUtil.getPsiClass(project,testScript.getResponse()));
        for (String dbInsert:testScript.getDbList()){
            PsiClass psiClass = PsiUtil.getPsiClass(project,dbInsert);
            dbInserts.add(psiClass);
        }
        csvElementVo.setRequest(requests);
        return requests;
    }
    //变更参数类型
    private Map<String,String> changeType(Map<PsiType,String> map){
        Map<String,String> newMap = new HashMap<>();
        for (PsiType parem:map.keySet()){
            String newParem = parem.getPresentableText().toString();
            newMap.put(newParem,map.get(parem));
        }
        return newMap;
    }

}

