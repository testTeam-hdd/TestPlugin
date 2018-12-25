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
public class GenerateBaseTest {

    private static String centent;

    private static String packageName = "com.miz.autotest.base";

    private static String classpath = "/src/test/java/com/miz/autotest/base/";

    private static String className = "AutoBaseTest.java";

    private static void generateBaseClassCentent() {
        StringBuffer sb = new StringBuffer("package ");
        sb.append(packageName);
        sb.append(";");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("import com.alibaba.fastjson.JSONObject;");
        sb.append("\r\n");
        sb.append("import com.miz.mekansm.dist.api.pojo.instance.BaseEntity;");
        sb.append("\r\n");
        sb.append("import com.miz.mekansm.dist.core.persistence.BaseMapper;");
        sb.append("\r\n");
        sb.append("import com.miz.testframework.database.DBUtils;");
        sb.append("\r\n");
        sb.append("import com.miz.testframework.dataprovider.DataDriver;");
        sb.append("\r\n");
        sb.append("import com.miz.testframework.util.CSVParseUtil;");
        sb.append("\r\n");
        sb.append("import com.miz.testframework.util.CSVUtil;");
        sb.append("\r\n");
        sb.append("import com.miz.testframework.vo.XlsRowVO;");
        sb.append("\r\n");
        sb.append("import org.springframework.test.context.ContextConfiguration;");
        sb.append("\r\n");
        sb.append("import java.util.List;");
        sb.append("\r\n");
        sb.append("import java.util.Map;");
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
        sb.append("@ContextConfiguration(classes = com.miz.autotest.base.TestGreContextConfiguration.class)");
        sb.append("\r\n");
        sb.append("public class AutoBaseTest extends DataDriver {");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("\t/**");
        sb.append("\r\n");
        sb.append("\t * 删除DB记录");
        sb.append("\r\n");
        sb.append("\t *");
        sb.append("\r\n");
        sb.append("\t * @param tableName 表名");
        sb.append("\r\n");
        sb.append("\t * @param condition 查询条件");
        sb.append("\r\n");
        sb.append("\t */");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("\tpublic static void deleteDB(String tableName, String condition) {");
        sb.append("\r\n");
        sb.append("\t\tString deleteSql = \"DELETE FROM \" + tableName + \" WHERE \" + condition;");
        sb.append("\r\n");
        sb.append("\t\tDBUtils.updateDB(tableName, deleteSql);");
        sb.append("\r\n");
        sb.append("\t}");
        sb.append("\r\n");
        sb.append("\t/**");
        sb.append("\r\n");
        sb.append("\t * 更新DB记录");
        sb.append("\r\n");
        sb.append("\t *");
        sb.append("\r\n");
        sb.append("\t * @param tableName 表名");
        sb.append("\r\n");
        sb.append("\t * @param condition 查询条件");
        sb.append("\r\n");
        sb.append("\t * @param filed     更新字段");
        sb.append("\r\n");
        sb.append("\t * @param value     更新值");
        sb.append("\r\n");
        sb.append("\t */");
        sb.append("\r\n");
        sb.append("\tpublic static void updateDB(String tableName, String condition, String filed, String value) {");
        sb.append("\r\n");
        sb.append("\t\tString updateSql = \"UPDATE \" + tableName + \" SET \" + filed + \"='\" + value + \"' WHERE \" + condition;");
        sb.append("\r\n");
        sb.append("\t\tDBUtils.updateDB(tableName, updateSql);");
        sb.append("\r\n");
        sb.append("\t}");
        sb.append("\r\n");
        sb.append("\t/**");
        sb.append("\r\n");
        sb.append("\t * 查询DB记录条数");
        sb.append("\r\n");
        sb.append("\t *");
        sb.append("\r\n");
        sb.append("\t * @param tableName 表名");
        sb.append("\r\n");
        sb.append("\t * @param condition 查询条件");
        sb.append("\r\n");
        sb.append("\t */");
        sb.append("\r\n");
        sb.append("\tpublic static int countDB(String tableName, String condition) {");
        sb.append("\r\n");
        sb.append("\t\tString selectSql = \"SELECT  count(*) FROM \" + tableName + \" WHERE \" + condition;");
        sb.append("\r\n");
        sb.append("\t\treturn DBUtils.getCountValue(tableName, selectSql);");
        sb.append("\r\n");
        sb.append("\t}");
        sb.append("\r\n");
        sb.append("\t/**");
        sb.append("\r\n");
        sb.append("\t * 查询DB");
        sb.append("\r\n");
        sb.append("\t *");
        sb.append("\r\n");
        sb.append("\t * @param tableName 表名");
        sb.append("\r\n");
        sb.append("\t * @param condition 查询条件");
        sb.append("\r\n");
        sb.append("\t */");
        sb.append("\r\n");
        sb.append("\tpublic static String queryDB(String tableName, String key, String condition) {");
        sb.append("\r\n");
        sb.append("\t\tString selectSql = \" SELECT \" + key + \" FROM \" + tableName + \" WHERE \" + condition;");
        sb.append("\r\n");
        sb.append("\t\treturn DBUtils.getStringValue(tableName, selectSql);");
        sb.append("\r\n");
        sb.append("\t}");
        sb.append("\r\n");
        sb.append("\t/**");
        sb.append("\r\n");
        sb.append("\t * 解析csv并插入DB");
        sb.append("\r\n");
        sb.append("\t *");
        sb.append("\r\n");
        sb.append("\t * @param csvfile    csv文件路径");
        sb.append("\r\n");
        sb.append("\t * @param basemapper 插入表对应mapper");
        sb.append("\r\n");
        sb.append("\t * @param clazz");
        sb.append("\r\n");
        sb.append("\t */");
        sb.append("\r\n");
        sb.append("\tpublic static <T extends BaseEntity> void insertDB(String csvfile, BaseMapper basemapper, Class<T> clazz) {");
        sb.append("\r\n");
        sb.append("\t\tif (!csvfile.isEmpty()) {");
        sb.append("\r\n");
        sb.append("\t\t\tCSVParseUtil util = null;");
        sb.append("\r\n");
        sb.append("\t\t\ttry {");
        sb.append("\r\n");
        sb.append("\t\t\t\tutil = new CSVParseUtil(csvfile);");
        sb.append("\r\n");
        sb.append("\t\t\t} catch (Exception e) {");
        sb.append("\r\n");
        sb.append("\t\t\t\te.printStackTrace();");
        sb.append("\r\n");
        sb.append("\t\t\t}");
        sb.append("\r\n");
        sb.append("\t\t\tList<XlsRowVO> list = util.parseCSV();");
        sb.append("\r\n");
        sb.append("\t\t\tfor (int i = 0; i < list.size(); i++) {");
        sb.append("\r\n");
        sb.append("\t\t\t\tMap<String, Object> data = list.get(i).getMapData();");
        sb.append("\r\n");
        sb.append("\t\t\t\tString param = JSONObject.toJSONString(data);");
        sb.append("\r\n");
        sb.append("\t\t\t\tT record = JSONObject.parseObject(param, clazz);");
        sb.append("\r\n");
        sb.append("\t\t\t\tbasemapper.insert(record);");
        sb.append("\r\n");
        sb.append("\t\t\t}");
        sb.append("\t\t}");
        sb.append("\t}");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("\tpublic static void main(String[] args) {");
        sb.append("\r\n");
        sb.append("\t\t//CSVUtil.createTransverseCsvForCheck(Bankcard.class, \"/Users/dongdong/Documents/code/asset-test-2/src/test/resources/testers/normal/submitBorrowCard/bankcard-db1.csv.csv\");");
        sb.append("\r\n");
        sb.append("\t}");
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
            generateBaseClassCentent();
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