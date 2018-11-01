package com.Utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.miz.testframework.config.Configration;
import com.miz.testframework.config.DBConfig;
import com.miz.testframework.config.impl.ConfigrationImpl;
import com.miz.testframework.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * dongdong Created by 3:58 PM  2018/11/1
 */
public class DBConnect {
    public static String fileName = "testdb.conf";

    private static Configration configImpl;


    /**
     * 配置中的表分割符
     */
    public static String DB_TABLENAME_SPIT_REGEX = ",";

    /**
     * 配置项计数，计算出共有多少组配置
     */
    public static int confNumber = -1;

    public static void getDbConnect(Project project) {
        PsiFile psifile = PsiUtil.getPsiFile(project, fileName);
        InputStream is = null;
        Properties properties = null;
        configImpl = new ConfigrationImpl();
        try {
            is = psifile.getVirtualFile().getInputStream();//psifile中读取数据流
             properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            Object keyObject = entry.getKey();
            Object valueObject = entry.getValue();
            configImpl.setProperty(keyObject.toString(), valueObject.toString());

        }
    }
    /**
     * 读取指定数据库配置项
     */
    public static DBConfig getDBConfig(String tableName) {
        DBConfig dbconfig = new DBConfig();
        String url = "";
        String username = "";
        String password = "";
        String schema = "";

		/* 这里修改为TABLE大小写不敏感 */
        if (StringUtil.isNotBlank(tableName)) {
            tableName = tableName.toUpperCase();
        }
        confNumber = 1;
        // 配置项计数，计算出共有多少组配置
        while (configImpl.getPropertyValue("ext" + confNumber + "_db_tablename") != null) {
            confNumber++;
        }
        for (int i = 1; i < confNumber; i++) {
            if (matchTable(tableName, configImpl.getPropertyValue("ext" + i + "_db_tablename"))) {
                url = configImpl.getPropertyValue("ext" + i + "_db_url");
                username = configImpl.getPropertyValue("ext" + i + "_db_username");
                password = configImpl.getPropertyValue("ext" + i + "_db_password");
                schema = configImpl.getPropertyValue("ext" + i + "_db_schema");
                break;
            }


        }

        dbconfig.setConnectionUrl(url);
        dbconfig.setUsername(username);
        dbconfig.setPassword(password);
        dbconfig.setSchema(schema);

        return dbconfig;
    }


    /**
     * 如果tableName包含在tableNameConfig中，则返回 true
     *
     * @param tableName
     * @param tableNameConfig
     * @return
     */
    private static boolean matchTable(String tableName, String tableNameConfig) {
        if (StringUtil.isBlank(tableNameConfig) || StringUtil.isBlank(tableName)) {
            return false;
        }
        String[] tables = tableNameConfig.toUpperCase().split(DB_TABLENAME_SPIT_REGEX);
        for (String table : tables) {
            if (table.equals(tableName)) {
                return true;
            }
        }
        return false;
    }


}
