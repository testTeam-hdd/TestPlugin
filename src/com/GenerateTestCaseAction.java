package com;

import com.Utils.PsiUtil;
import com.Vo.RequestParam;
import com.Vo.TestScript;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.tasks.GenerateTestScript;

/**
 * dongdong Created by 下午3:18  2018/6/7
 */
public class GenerateTestCaseAction extends AnAction {

    private Project project;

    //包名
    private String packageName = "src.test.java.com.miz.autotest.servicetest.";

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(PlatformDataKeys.PROJECT);
        generate();

        refreshProject(e);
    }

    /**
     * 刷新项目
     *
     * @param e
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
                testScript.setPackageName(packageName.substring(14) + testScript.getTestClass().toLowerCase());
                testScript.setTestPackageName(getTestClassPackage(testScript));
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
     *
     * @return
     */
    private String getAppPath(TestScript testScript) {
        String packagePath = packageName.replace(".", "/");
        String path = project.getBasePath() + "/" + packagePath + testScript.getTestClass();
        return path;
    }

    private String getRequestPackageName(TestScript testScript) {
        String packageName = null;
        for (RequestParam requestParam : testScript.getRequest()) {
            if (requestParam.getType().equals("Object")) {
                packageName = PsiUtil.getPackageName(project, requestParam.getValue());
                packageName = packageName + "." + requestParam.getValue();
            }
        }
        return packageName;
    }

    private String getResponsePackageName(TestScript testScript) {
        String packageName = null;
        for (String requestParam : testScript.getResponse().keySet()) {
            if (requestParam.equals("Object")) {
                packageName = PsiUtil.getPackageName(project, testScript.getResponse().get(requestParam));
                packageName = packageName + "." + testScript.getResponse().get(requestParam);
            }
        }

        return packageName;
    }

    private String getTestClassPackage(TestScript testScript) {
        String packageName = PsiUtil.getPackageName(project, testScript.getTestClass());
        packageName = packageName + "." + testScript.getTestClass();
        return packageName;
    }
}

