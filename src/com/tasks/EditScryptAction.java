package com.tasks;

import com.ScryptEdit;
import com.Utils.CSVParseUtil;
import com.Utils.DataSupply;
import com.Utils.FileUtil;
import com.Vo.CsvVO;
import com.exception.PluginErrorMsg;
import com.exception.PluginRunTimeException;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * dongdong Created by 5:37 PM  2019/1/31
 */
public class EditScryptAction extends AnAction {

    private Project project;

    private String csvPath = "/src/test/resources/testers/";


    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(PlatformDataKeys.PROJECT);
        edit(e);
        refreshProject(e);
    }

    /**
     * 初始化Dialog
     */
    private void edit(AnActionEvent e) {
        String csvPath = null;
        try {
            String className = getClassName(e);
            csvPath = getCsvPath(className);
            List<CsvVO> csvContentList = FileUtil.getFileContent(csvPath);
            ScryptEdit scryptEdit = new ScryptEdit(csvContentList, csvPath, project);
            scryptEdit.setVisible(true);
        } catch (PluginRunTimeException e1) {
            Messages.showInfoMessage(e1.getErrorMsg(), "提示");
        }
    }

    /**
     * 根据上下文获取当前坐标所在位置的类名
     */
    private String getClassName(AnActionEvent event) {
        String className = null;
        DataContext dataContext = event.getDataContext();
        if ("java".equals(getFileExtension(dataContext))) {//根据扩展名判定是否进行下面的处理
            //获取选中的文件
            VirtualFile file = DataKeys.VIRTUAL_FILE.getData(event.getDataContext());
            if (file != null) {
                className = file.getName();
            }
        } else {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_CLASS_ERROR);
        }

        return className;
    }

    /**
     * 根据类名获取csv路径
     */
    private String getCsvPath(String className) {
        boolean isNormal = true;
        String csvDirName = null;
        String path = null;

        if (className.contains("Normal")) {
            csvDirName = className.substring(0, className.lastIndexOf("Normal"));
        } else if (className.contains("FuncException")) {
            isNormal = false;
            csvDirName = className.substring(0, className.lastIndexOf("FuncException"));
        } else {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_CLASS_ERROR);
        }
        if (isNormal) {
            path = project.getBasePath() + csvPath + "normal/" + csvDirName;
        } else {
            path = project.getBasePath() + csvPath + "funcExp/" + csvDirName;
        }

        return path;
    }



    @Override
    public void update(AnActionEvent event) {
        //在Action显示之前,根据选中文件扩展名判定是否显示此Action
        String extension = getFileExtension(event.getDataContext());
        this.getTemplatePresentation().setEnabled(extension != null && "java".equals(extension));
    }

    public static String getFileExtension(DataContext dataContext) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);
        return file == null ? null : file.getExtension();

    }

    public static void main(String[] args) {
        if (1 + 1 == 2) {
            System.out.println("");
        }
    }

    /**
     * 刷新项目
     */
    private void refreshProject(AnActionEvent e) {
        e.getProject().getBaseDir().refresh(false, true);
    }
}
