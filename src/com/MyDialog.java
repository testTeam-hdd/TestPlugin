package com;

import com.Utils.MyJPanel;
import com.Vo.RequestParam;
import com.Vo.TestScript;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane jsp;
    private JTabbedPane tabbedPane;
    private JRadioButton normal;
    private JRadioButton exception;
    private JTextField testScriptDescription;
    private JPanel setTestCase;
    private JPanel setRequest;
    private JPanel setDb;
    private JPanel setResponse;
    private JPanel setDbCheck;
    private JTextField testClass;
    private JTextField testMethod;
    private JPanel midJPanel;
    private JPanel chooseJPanel;
    private JPanel childJPanel;
    private JButton addTestCase;
    private JPanel addCaseJPanel;
    private JButton save;
    private int caseIdIndex = 1;

    private DialogCallBack mCallBack;
    private TestScript testScript = new TestScript();
    private Map<String, String> testCaseMap = new HashMap<>();

    public MyDialog(DialogCallBack callBack) {
        this.mCallBack = callBack;
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));//盒子布局.从上到下
        setTitle("生成测试用例脚本");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(1000, 1000);
        setLocationRelativeTo(null);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        addTestCase.addActionListener(e -> addTestCase());

        save.addActionListener(e -> saveTestCase());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (null != mCallBack) {
            this.testScript.setTestScriptDescription(testScriptDescription.getText().trim());
            this.testScript.setTestClass(testClass.getText().trim());
            this.testScript.setTestMethod(testMethod.getText().trim());
            Map<String, String> map = new HashMap<>();
            Map<String, String> mapResponse = new HashMap<>();
            List<RequestParam> list1 = new ArrayList<>();
            RequestParam requestParam1 = new RequestParam();
            requestParam1.setType("Object");
            requestParam1.setValue("BorrowerBo");
            list1.add(requestParam1);
            mapResponse.put("Object", "BorrowerBo");
            List<String> list = new ArrayList<String>() {};
            List<String> list2 = new ArrayList<String>() {};
            list.add("borrowDb");
            list.add("lendDb");
            list2.add("borrowDbCheck");
            list2.add("lendDbCheck");
            testScript.setDbCheckList(list2);
            testScript.setAuthor("dongdong");
            testScript.setDbList(list);
            testScript.setRequest(list1);
            testScript.setResponse(mapResponse);
            mCallBack.ok(this.testScript);
        }
        dispose();
    }

    //添加测试用例任务触发按钮
    private void addTestCase() {
        addCaseJPanel.setLayout(new BoxLayout(addCaseJPanel, BoxLayout.Y_AXIS));//盒子布局
        JPanel myJPanel = new MyJPanel(this.setCaseId(), addCaseJPanel);
        myJPanel.setName(this.setCaseId());
        addCaseJPanel.add(myJPanel);//添加1个自己定义的面板组件
        addCaseJPanel.revalidate();
        if (addCaseJPanel.getComponentCount() > 15) {
            myUpdateUI();//拖动下拉框到底部
        }
    }
    //保存测试用例
    private void saveTestCase() {
        Component[] components = addCaseJPanel.getComponents();
        List<JPanel> JPanelList = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanelList.add((JPanel) component);
            }
        }
        for (JPanel jPanel : JPanelList) {
            String caseid = null;
            String description = null;

            for (Component component : jPanel.getComponents()) {
                if (component instanceof JLabel) {
                    caseid = ((JLabel) component).getText();
                }
                if (component instanceof JTextField) {
                    description = ((JTextField) component).getText();
                }
            }
            testCaseMap.put(caseid, description);
        }
    }

    private void onCancel() {
        dispose();
    }

    public interface DialogCallBack {
        void ok(TestScript testScript);
    }

    private void myUpdateUI() {
        JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
        jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
    }

    //生成caseId
    private String setCaseId() {
        String caseId = null;
        if (addCaseJPanel.getComponentCount() == 0) {
            caseId = spliceCaseId();
        } else {
            caseIdIndex = addCaseJPanel.getComponentCount() + 1;
            caseId = spliceCaseId();
            while (!checkCaseId(caseId)) {
                caseIdIndex++;
                caseId = spliceCaseId();
            }
        }
        return caseId;
    }

    //拼接caseId
    private String spliceCaseId() {
        String caseId = null;
        if (caseIdIndex < 10) {
            caseId = "NO00" + caseIdIndex;
        } else if (caseIdIndex < 100 && caseIdIndex >= 10) {
            caseId = "NO0" + caseIdIndex;
        } else {
            caseId = "NO" + caseIdIndex;
        }
        return caseId;
    }

    private boolean checkCaseId(String caseId) {
        Component[] components = addCaseJPanel.getComponents();
        boolean i = true;
        for (Component component : components) {
            if (component.getName().equals(caseId)) {
                i = false;
            }
        }
        return i;
    }

}
