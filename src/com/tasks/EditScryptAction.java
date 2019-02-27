package com.tasks;

import com.ScryptEdit;
import com.Utils.CSVParseUtil;
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
        String className = getClassName(e);
        String csvPath = getCsvPath(className);
        List<CsvVO> csvContentList = getFileContent(csvPath);
        ScryptEdit scryptEdit = new ScryptEdit(csvContentList);
        String a = "";
        scryptEdit.setVisible(true);
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
        } else if (className.contains("FuncException")){
            isNormal = false;
            csvDirName = className.substring(0, className.lastIndexOf("FuncException"));
        }else{
            throw new PluginRunTimeException(PluginErrorMsg.TEST_CLASS_ERROR);
        }
        if (isNormal) {
            path = project.getBasePath() + csvPath + "normal/" + csvDirName;
        } else {
            path = project.getBasePath() + csvPath + "funcExp/" + csvDirName;
        }

        return path;
    }

    /**
     * 读取一个路径下所有文件的文件内容
     */
    private List<CsvVO> getFileContent(String path) {
        List<CsvVO> csvList = new ArrayList<>();
        CSVParseUtil csvParseUtil = new CSVParseUtil();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File content : files) {
            List<String> list = new ArrayList<>();
            CsvVO csvVO = new CsvVO();
            BufferedReader br;
            int rowNum;
            int colNum;

            try {
                InputStream is = new FileInputStream(content);
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);
                String stemp;
                while ((stemp = br.readLine()) != null) {
                    list.add(stemp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            colNum = csvParseUtil.getColNum(list);
            rowNum = csvParseUtil.getRowNum(list);
            csvVO.setRowNum(rowNum);
            csvVO.setColNum(colNum);
            csvVO.setCsvName(content.getName());
            csvVO.setContent(list);
            csvList.add(csvVO);
        }
        return csvList;
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

    /**
     * 刷新项目
     */
    private void refreshProject(AnActionEvent e) {
        e.getProject().getBaseDir().refresh(false, true);
    }
}
