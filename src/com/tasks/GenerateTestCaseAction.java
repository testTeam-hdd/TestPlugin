package com.tasks;

import com.MyDialog;
import com.Utils.EmptyUtils;
import com.Utils.GenerateCsv;
import com.Utils.PsiUtil;
import com.Utils.TestSuite;
import com.Vo.CsvElementVo;
import com.Vo.TestScript;
import com.exception.PluginErrorMsg;
import com.exception.PluginRunTimeException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            public void ok(TestScript testScript, MyDialog dialog) throws IOException {
                getScriptParem(testScript);
                String path = getAppPath(testScript);
                GenerateTestScript generateTestScript = new GenerateTestScript(testScript);
                String testClassName = null;
                if (testScript.getIsNormal()) {
                    testClassName = testScript.getTestMethod() + "NormalTest.java";
                } else {
                    testClassName = testScript.getTestMethod() + "FuncExceptionTest.java";
                }
                new GenerateCsv(generateCsv(testScript), testScript, project);
                generateTestScript.writeToFile(path, GenerateTestScript.subStringToUc(testClassName), project);
                TestSuite.AddTestCaseToSuite(testScript, project);
                Messages.showInfoMessage("脚本生成成功!", "result");
                dialog.dispose();
            }
        });
        myDialog.setVisible(true);
    }

    /**
     * 获取包路径
     */
    private String getAppPath(TestScript testScript) {
        String path = null;
        String packagePath = packageName.replace(".", "/");
        if (EmptyUtils.isNotEmpty(testScript.getModule())) {
            path = project.getBasePath() + "/" + testScript.getModule() + "/" + packagePath + testScript.getTestClass();
        } else {
            path = project.getBasePath() + "/" + packagePath + testScript.getTestClass();
        }
        return path;
    }

    /**
     * 获取csv路径
     */
    private String getCsvPath(TestScript testScript) {
        String path = null;
        if (EmptyUtils.isNotEmpty(testScript.getModule())) {
            path = project.getBasePath() + "/" + testScript.getModule() + "/" + csvPath;
        } else {
            path = project.getBasePath() + csvPath;
        }
        return path;
    }

    /**
     * 获取测试方法中，请求对象对应的包名
     */
    private List<String> getRequestPackageName(TestScript testScript) {
        List<String> packageNames = new ArrayList<>();
        String packageName;
        String listPackageName;
        List<PsiType> lists = PsiUtil.getMethodPrame(project, testScript.getTestClass(), testScript.getTestMethod());
        for (PsiType key : lists) {
            if (!Arrays.asList(GenerateTestScript.TYPE).contains(key.getPresentableText())) {
                if (PsiUtil.isCollection(key)) {
                    listPackageName = "java.util.*";
                    packageName = PsiUtil.getPackageName(project, GenerateTestScript.subStringGeneric(key.getPresentableText()));
                    packageName = packageName + "." + GenerateTestScript.subStringGeneric(key.getPresentableText());
                    packageNames.add(listPackageName);
                } else {
                    packageName = PsiUtil.getPackageName(project, key.getPresentableText());
                    packageName = packageName + "." + key.getPresentableText();
                }
                packageNames.add(packageName);
            }
        }
        return packageNames;
    }

    /**
     * 获取测试方法中，响应对象对应的包名
     */
    private String getResponsePackageName(TestScript testScript) {
        String packageName = null;
        PsiType returnType = PsiUtil.getMethodRetureType(project, testScript.getTestClass(), testScript.getTestMethod());
        if (!Arrays.asList(GenerateTestScript.TYPE).contains(returnType.getPresentableText()) && !PsiUtil.isEnum(returnType)) {
            if (PsiUtil.isCollection(returnType)) {
                packageName = "java.util.*";
            } else {
                packageName = PsiUtil.getPackageName(project, returnType.getPresentableText());
                packageName = packageName + "." + returnType.getPresentableText();
            }
        }
        return packageName;
    }

    /**
     * 获取测试类对应的包名
     */
    private String getTestClassPackage(TestScript testScript) {
        String packageName = null;
        try {
            packageName = PsiUtil.getPackageName(project, testScript.getTestClass());
            packageName = packageName + "." + testScript.getTestClass();
        } catch (PluginRunTimeException e) {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_CLASS_NOT_FIND);
        }
        return packageName;
    }

    /**
     * 获取dbMapper对应的包名
     */
    private List<String> getDbMapperPackage(TestScript testScript) {
        List<String> dbMapperPackage = new ArrayList<>();
        String packageName = null;
        try {
            for (String db : testScript.getDbList()) {
                db = PsiUtil.subClassName(db);
                packageName = PsiUtil.getPackageName(project, db + "Mapper");
                packageName = packageName + "." + db + "Mapper";
                dbMapperPackage.add(packageName);
            }
        } catch (PluginRunTimeException e) {
            throw new PluginRunTimeException(PluginErrorMsg.DBENTITY_CLASS_NOT_FIND);
        }
        return dbMapperPackage;
    }

    /**
     * 获取db实体对应的包名
     */
    private List<String> getDbPackage(TestScript testScript) {
        List<String> dbPackage = new ArrayList<>();
        String packageName = null;
        try {
            for (String db : testScript.getDbList()) {
                db = PsiUtil.subClassName(db);
                packageName = PsiUtil.getPackageName(project, db);
                packageName = packageName + "." + db;
                dbPackage.add(packageName);
            }
        } catch (PluginRunTimeException e) {
            throw new PluginRunTimeException(PluginErrorMsg.DBENTITY_CLASS_NOT_FIND);
        }
        return dbPackage;
    }


    /**
     * 获取非基础类型、枚举类型、集合类型的入参集合
     */
    private List<String> getRequestObject(TestScript testScript) {
        List<String> requests = new ArrayList<>();
        List<PsiType> list = PsiUtil.getMethodPrame(project, testScript.getTestClass(), testScript.getTestMethod());
        if (list.size() == 0 || list == null) {
            return null;
        }
        for (PsiType key : list) {
            if (!Arrays.asList(GenerateTestScript.TYPE).contains(key.getPresentableText()) && !PsiUtil.isEnum(key)) {
                if (PsiUtil.isCollection(key)) {
                    String generic = GenerateTestScript.subStringGeneric(key.getPresentableText());
                    requests.add(generic);
                    continue;
                }
                requests.add(key.getPresentableText());
            }
        }
        return requests;
    }

    /**
     * 组装生成csv参数
     */
    private CsvElementVo generateCsv(TestScript testScript) {
        List<PsiClass> requests = new ArrayList<>();
        List<PsiClass> dbInserts = new ArrayList<>();
        List<PsiClass> objs = new ArrayList<>();

        CsvElementVo csvElementVo = new CsvElementVo();
        csvElementVo.setNormal(testScript.getIsNormal());
        try {
            if (testScript.getRequest().size() != 0 && testScript.getRequest() != null) {
                for (String request : testScript.getRequest()) {
                    PsiClass psiClass = PsiUtil.getPsiClass(project, request);
                    requests.add(psiClass);
                }
                csvElementVo.setRequest(requests);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!EmptyUtils.isEmpty(testScript.getResponse())) {
                csvElementVo.setResponse(PsiUtil.getPsiClass(project, testScript.getResponse()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (testScript.getDbList().size() != 0 && testScript.getDbList() != null) {
                for (String dbInsert : testScript.getDbList()) {
                    PsiClass psiClass = PsiUtil.getPsiClass(project, dbInsert);
                    dbInserts.add(psiClass);
                }
            }
            csvElementVo.setDbInsert(dbInserts);
        } catch (PluginRunTimeException ex) {
            throw new PluginRunTimeException(PluginErrorMsg.DBENTITY_CLASS_NOT_FIND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (testScript.getObjectList().size() != 0 && testScript.getObjectList() != null) {
                for (String obj : testScript.getObjectList()) {
                    PsiClass psiClass = PsiUtil.getPsiClass(project, obj);
                    objs.add(psiClass);
                }
            }
            csvElementVo.setObjectCheck(objs);
        } catch (PluginRunTimeException ex) {
            throw new PluginRunTimeException(PluginErrorMsg.DBENTITY_CLASS_NOT_FIND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (testScript.getDbCheckList().size() != 0 && testScript.getDbCheckList() != null) {
            csvElementVo.setDbCheck(testScript.getDbCheckList());
        }
        csvElementVo.setTestMethodName(testScript.getTestMethod());
        csvElementVo.setPath(getCsvPath(testScript));
        return csvElementVo;
    }

    private TestScript getScriptParem(TestScript testScript) {
        try {
            testScript.setResponsePackageName(getResponsePackageName(testScript));
            testScript.setRequestPackageName(getRequestPackageName(testScript));
            testScript.setPackageName(packageName.substring(14) + testScript.getTestClass());
            testScript.setTestPackageName(getTestClassPackage(testScript));
            String response = PsiUtil.getMethodRetureType(project, testScript.getTestClass(), testScript.getTestMethod()).getPresentableText();
            testScript.setResponse(response.equals("void") ? null : response);
            testScript.setAllRequestParem(PsiUtil.getMethodPrame(project, testScript.getTestClass(), testScript.getTestMethod()));
            testScript.setRequest(getRequestObject(testScript));
            testScript.setDbMapperPackageName(getDbMapperPackage(testScript));
            testScript.setDbPackageName(getDbPackage(testScript));
            testScript.setTableName(getTableName(testScript));
        } catch (PluginRunTimeException e) {
            Messages.showInfoMessage(e.getErrorMsg(), "提示");
        }
        return testScript;
    }

    private List<String> getTableName(TestScript testScript) {
        List<String> tableNames = new ArrayList<>();
        for (String entity : testScript.getDbList()) {
            String tableName = PsiUtil.getTableName(project, entity);
            tableNames.add(tableName);
        }
        return tableNames;
    }

//    //变更参数类型
//    private Map<String, String> changeType(Map<PsiType, String> map) {
//        Map<String, String> newMap = new HashMap<>();
//        for (PsiType parem : map.keySet()) {
//            String newParem = parem.getPresentableText().toString();
//            newMap.put(newParem, map.get(parem));
//        }
//        return newMap;
//    }

}

