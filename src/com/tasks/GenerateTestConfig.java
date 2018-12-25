package com.tasks;

import com.Utils.GenerateBaseTest;
import com.Utils.GenerateTestGreContextConfiguration;
import com.Utils.TestDbConfig;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

import java.io.IOException;

/**
 * dongdong Created by 11:29 AM  2018/12/25
 */
public class GenerateTestConfig extends AnAction {
    private Project project;

    @Override
    public void actionPerformed(AnActionEvent event) {
        project = event.getData(PlatformDataKeys.PROJECT);
        generate();
        refreshProject(event);

    }

    private void generate() {
        try {
            //db配置
            TestDbConfig.AddDbAutoConfig(project);
            TestDbConfig.AddTestDbConfig(project);
            //基类
            GenerateBaseTest.writeToFile(project);
            //springboot配置
            GenerateTestGreContextConfiguration.writeToFile(project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新项目
     */
    private void refreshProject(AnActionEvent e) {
        e.getProject().getBaseDir().refresh(false, true);
    }
}