package com.Utils;

import com.intellij.openapi.project.Project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * dongdong Created by 2:32 PM  2018/12/25
 */
public class GenerateTestGreContextConfiguration {

    private static String centent;

    private static String packageName = "com.miz.autotest.base";

    private static String classpath = "/src/test/java/com/miz/autotest/base/";

    private static String className = "TestGreContextConfiguration.java";

    private static void generateTestGreContextConfigurationCentent() {
        StringBuffer sb = new StringBuffer("package ");
        sb.append(packageName);
        sb.append(";");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;");
        sb.append("\r\n");
        sb.append("import org.springframework.cache.annotation.EnableCaching;");
        sb.append("\r\n");
        sb.append("import org.springframework.boot.autoconfigure.EnableAutoConfiguration;");
        sb.append("\r\n");
        sb.append("import org.springframework.context.annotation.ComponentScan;");
        sb.append("\r\n");
        sb.append("import org.springframework.context.annotation.Configuration;");
        sb.append("\r\n");
        sb.append("import org.springframework.context.annotation.ImportResource;");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("/**");
        sb.append("\r\n");
        sb.append(" * @author Created by 技术平台组");
        sb.append("\r\n");
        sb.append(" * @Date ");
        sb.append(new Date());
        sb.append("\r\n");
        sb.append(" */");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("@ComponentScan(basePackages = {\"com.miz\"})");
        sb.append("\r\n");
        sb.append("@ImportResource(locations = {\"classpath:/config/cfg-test-properties.xml\"})");
        sb.append("\r\n");
        sb.append("@Configuration");
        sb.append("\r\n");
        sb.append("@EnableApolloConfig");
        sb.append("\r\n");
        sb.append("@EnableCaching");
        sb.append("\r\n");
        sb.append("@EnableAutoConfiguration");
        sb.append("\r\n");
        sb.append("public class TestGreContextConfiguration {");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("}");
        sb.append("\r\n");
        centent = sb.toString();
    }

    /**
     * 生成基类
     *
     */
    public static void writeToFile(Project project) {
        try {
            generateTestGreContextConfigurationCentent();
            File floder = new File(project.getBasePath()+classpath);
            if (!floder.exists()) {
                floder.mkdirs();
            }

            File file = new File(project.getBasePath()+classpath + "/" + className);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(centent);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}