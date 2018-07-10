package com;

import com.Utils.GenCsvFileUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * dongdong Created by 下午3:18  2018/6/7
 */
public class GenerateTestCaseAction extends AnAction {


    private Project project;
    //包名
    private String packageName = "src.test.java.com.miz.autotest.servicetest.";
    private String mAuthor;//作者
    private String testClassName;//测试类名称
    private String testMethodName;//测试方法名称

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(PlatformDataKeys.PROJECT);
        init();
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
    private void init() {
        MyDialog myDialog = new MyDialog(new MyDialog.DialogCallBack() {
            @Override
            public void ok(String author, String TestClassName, String TestMethodName) {
                mAuthor = author;
                testClassName = TestClassName;
                testMethodName = TestMethodName;
                createClassFiles();
                GenCsvFileUtil.createCsvForObject(testMethodName,true,"TransfeeBo",project);
                Messages.showInfoMessage(project, "脚本生成成功!", "提示");
            }
        });
        myDialog.setVisible(true);

    }

    /**
     * 生成类文件
     */
    private void createClassFiles() {
        createClassFile();
    }

    /**
     * 生成测试代码
     */
    private void createClassFile() {
        String fileName = "";
        String content = "";
        String appPath = getAppPath();
        fileName = "testTemplate.txt";
        content = ReadTemplateFile(fileName);
        content = dealTemplateContent(content);
        writeToFile(content, appPath, testMethodName + "NormalTest.java");
    }

    /**
     * 获取包路径
     *
     * @return
     */
    private String getAppPath() {
        String packagePath = packageName.replace(".", "/");
        String path = project.getBasePath() + "/" + packagePath + testClassName;
        return path;
    }

    /**
     * 替换模板中字符
     *
     * @param content
     * @return
     */
    private String dealTemplateContent(String content) {
        content = content.replace("${classEntity.scriptPackage}", packageName);
        content = content.replace("$author", mAuthor);
        content = content.replace("$date", getDate());
        content = content.replace("${classEntity.scriptClassName}", testMethodName + "NormolTest");
        content = content.replace("${classEntity.scriptClassName}", testMethodName + "NormolTest");
        content = content.replace("${classEntity.scriptClassName}", testMethodName + "NormolTest");
        content = content.replace("${classEntity.scriptClassName}", testMethodName + "NormolTest");
        content = content.replace("${classEntity.scriptClassName}", testMethodName + "NormolTest");
        return content;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 读取模板文件中的字符内容
     *
     * @param fileName 模板文件名
     * @return
     */
    private String ReadTemplateFile(String fileName) {
        InputStream in = null;
        in = this.getClass().getResourceAsStream("/template/" + fileName);
        String content = "";
        try {
            content = new String(readStream(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


    private byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
            inputStream.close();
        }

        return outputStream.toByteArray();
    }


    /**
     * 生成
     *
     * @param content   类中的内容
     * @param classPath 类文件路径
     * @param className 类文件名称
     */
    private void writeToFile(String content, String classPath, String className) {
        try {
            File floder = new File(classPath);
            if (!floder.exists()) {
                floder.mkdirs();
            }

            File file = new File(classPath + "/" + className);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

