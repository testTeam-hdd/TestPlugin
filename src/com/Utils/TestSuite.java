package com.Utils;

import com.Vo.TestScript;
import com.intellij.openapi.project.Project;
import com.tasks.GenerateTestScript;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * dongdong Created by 2:16 PM  2018/12/24
 */
public class TestSuite {

    private static String testClassPath = "com.miz.autotest.servicetest.";

    private static String testClassType = "NormalTest";

    private static String filePath = null;

    private static String filename = null;

    public static void AddTestCaseToSuite(TestScript testScript, Project project) throws IOException {
        boolean isFirst = false;
        getpath(testScript);
        getFilePath(project);
        File floder = new File(filePath);
        if (!floder.exists()) {
            floder.mkdirs();
            isFirst = true;
        }
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
            isFirst = true;
        }
        Document document = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            if (isFirst) {
                document = db.newDocument();
            }else{
                document = db.parse(file);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element test = document.createElement("test");
        test.setAttribute("name", "test");
        Element classes = document.createElement("classes");
        Element aclass = document.createElement("class");
        aclass.setAttribute("name", testClassPath);
        test.appendChild(classes);
        classes.appendChild(aclass);
        if (isFirst) {
            Element suite = document.createElement("suite");
            suite.appendChild(test);
            document.appendChild(suite);
        }else{
            document.getDocumentElement().appendChild(test);
        }
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            //换行文件内容
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(document), new StreamResult(new File(filename)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void getpath(TestScript testScript) {
        getTestClassType(testScript.getIsNormal());
        testClassPath = String.format("%s%s%s%s%s", testClassPath, testScript.getTestClass(), ".", GenerateTestScript.subStringToUc(testScript.getTestMethod()), testClassType);
    }

    private static void getTestClassType(boolean isNormal) {
        if (!isNormal) {
            testClassType = "FuncExceptionTest";
        }
    }

    private static void getFilePath(Project project) {
        String path = "/src/test/resources/testsuit/";
        String fileName = "autotest-testsuit.xml";
        filePath = String.format("%s%s", project.getBasePath(), path);
        filename = String.format("%s%s%s", project.getBasePath(), path, fileName);
    }

}
