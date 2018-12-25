package com.Utils;

import com.intellij.openapi.project.Project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * dongdong Created by 10:30 AM  2018/12/25
 */
public class TestDbConfig {
    private static String dbFilePath = null;
    private static String dbFile = null;
    private static String autoConfigFilePath = null;
    private static String autoConfigFile = null;

    public static void AddTestDbConfig(Project project) throws IOException {
        getDbFilePath(project);
        File floder = new File(dbFilePath);
        if (!floder.exists()) {
            floder.mkdirs();
        }

        File file = new File(dbFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("#默认生成一套数据库配置，需要根据测试需求更新成所需的db配置\n");
            bw.write("ext1_db_url = jdbc:mysql://192.168.3.19/asset?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true\n");
            bw.write("ext1_db_username = mz\n");
            bw.write("ext1_db_password = MZisagreatcompany+_)(*&^%$#@!\n");
            bw.write("#所有测试过程中会用到的表都需要添加到此配置文件中\n");
            bw.write("ext1_db_tablename = lend ……\n");
            bw.write("#测试系统\n");
            bw.write("ext1_db_schema = asset");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void AddDbAutoConfig(Project project) throws IOException {
        getDbAutoConfigPath(project);
        File floder = new File(autoConfigFilePath);
        if (!floder.exists()) {
            floder.mkdirs();
        }

        File file = new File(autoConfigFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("dbconf_file = testdb.conf");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取testdb.conf路径
     */
    private static void getDbFilePath(Project project) {
        String path = "/src/test/resources/config/dbConf/";
        String fileName = "testdb.conf";
        dbFilePath = String.format("%s%s", project.getBasePath(), path);
        dbFile = String.format("%s%s%s", project.getBasePath(), path, fileName);
    }

    /**
     * 获取auto-config.properties路径
     */
    private static void getDbAutoConfigPath(Project project) {
        String path = "/src/test/resources/config/";
        String fileName = "auto-config.properties";
        autoConfigFilePath = String.format("%s%s", project.getBasePath(), path);
        autoConfigFile = String.format("%s%s%s", project.getBasePath(), path, fileName);
    }
}