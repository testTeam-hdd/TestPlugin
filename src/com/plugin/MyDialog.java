//package com.plugin;
//
//import com.Utils.AddObjectJPanel;
//import com.Vo.RequestParam;
//import com.Vo.TestScript;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MyDialog extends JDialog {
//    private JPanel contentPane;
//    private JButton buttonOK;
//    private JButton buttonCancel;
//    private JScrollPane jsp;
//    private JTextField testScriptDescription;
//    private JTextField testClass;
//    private JTextField testMethod;
//    private JRadioButton normal;
//    private JRadioButton exception;
//    private JPanel setDataJPanel;
//    private JPanel scriptJPanel;
//    private JButton nextJButton;
//    private JButton previonsJBotton;
//    private JButton requestButton;
//    private JButton responseButton;
//    private JButton DBinsertButton;
//    private JButton DBcheckButton;
//    private int caseIdIndex = 1;
//    private TestScript testScript = new TestScript();
//    private DialogCallBack mCallBack;
//    private CardLayout cardLayout = new CardLayout();
//
//    private Map<String, String> testCaseMap = new HashMap<>();
//
//    public MyDialog(DialogCallBack callBack) {
//        this.mCallBack = callBack;
////        testScriptJPanel.setLayout(new BoxLayout(testScriptJPanel, BoxLayout.Y_AXIS));//盒子布局.从上到下
////        scriptParam.setLayout(new BoxLayout(scriptParam,BoxLayout.Y_AXIS));
//        setTitle("生成测试用例脚本");
//        setContentPane(contentPane);
//        contentPane.setLayout(cardLayout);
//        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);
//        setSize(1100, 750);
//        setLocationRelativeTo(null);
//        setJradio();
////        tabbedPane.setTabLayoutPolicy();
//
//
//        buttonOK.addActionListener(e -> onOK());
//
//        buttonCancel.addActionListener(e -> onCancel());
//
//        nextJButton.addActionListener(e -> next());
//
//        previonsJBotton.addActionListener(e -> previons());
//
//        requestButton.setLayout(new BoxLayout(requestButton, BoxLayout.Y_AXIS));
//        requestButton.addActionListener(e -> addButton(requestButton));
//
//        responseButton.setLayout(new BoxLayout(requestButton, BoxLayout.Y_AXIS));
//        responseButton.addActionListener(e -> addButton(responseButton));
//
//        DBinsertButton.setLayout(new BoxLayout(requestButton, BoxLayout.Y_AXIS));
//        DBinsertButton.addActionListener(e -> addButton(DBinsertButton));
//
//        DBcheckButton.setLayout(new BoxLayout(requestButton, BoxLayout.Y_AXIS));
//        DBcheckButton.addActionListener(e -> addButton(DBcheckButton));
//
//
////        addTestCase.addActionListener(e -> addTestCase());
//
////        save.addActionListener(e -> saveTestCase());
//
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                onCancel();
//            }
//        });
//
//        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
////        setLookAndFeel(this);
//    }
//
//    private void onOK() {
//        if (null != mCallBack) {
//            testScript.setTestScriptDescription(testScriptDescription.getText().trim());
//            testScript.setTestClass(testClass.getText().trim());
//            testScript.setTestMethod(testMethod.getText().trim());
//            Map<String, String> map = new HashMap<>();
//            Map<String, String> mapResponse = new HashMap<>();
//            List<RequestParam> list1 = new ArrayList<>();
//            RequestParam requestParam1 = new RequestParam();
//            requestParam1.setType("Object");
//            requestParam1.setValue("BorrowerBo");
//            list1.add(requestParam1);
//            mapResponse.put("Object", "BorrowerBo");
//            List<String> list = new ArrayList<String>() {
//            };
//            List<String> list2 = new ArrayList<String>() {
//            };
//            list.add("borrowDb");
//            list.add("lendDb");
//            list2.add("borrowDbCheck");
//            list2.add("lendDbCheck");
//            testScript.setDbCheckList(list2);
//            testScript.setAuthor("dongdong");
//            testScript.setDbList(list);
//            testScript.setRequest(list1);
//            testScript.setResponse(mapResponse);
//            testScript.setIsNormal(normal.isSelected() ? true : false);
//            mCallBack.ok(testScript);
//        }
//        dispose();
//    }
//
//    private void setJradio() {
//        //将按钮添加到组，否则按钮会支持多选
//        ButtonGroup buttonGroup = new ButtonGroup();
//        buttonGroup.add(normal);
//        buttonGroup.add(exception);
//        normal.setSelected(true);
//    }
//
//    private void addButton(Container container) {
//        JPanel myJPanl = new AddObjectJPanel(setIndex(container), container, 270, 30);
//        myJPanl.setName("index");
//        container.add(myJPanl);
//        container.revalidate();
//        if (container.getComponentCount() > 15) {
//            myUpdateUI();//拖动下拉框到底部
//        }
//    }
//    //添加测试用例任务触发按钮
////    private void addTestCase() {
////        testScriptJPanel.setLayout(new BoxLayout(testScriptJPanel, BoxLayout.Y_AXIS));//盒子布局
////        JPanel myJPanel = new AddTestCaseJPanel(this.setCaseId(), testScriptJPanel);
////        myJPanel.setName(this.setCaseId());
////        testScriptJPanel.add(myJPanel);//添加1个自己定义的面板组件
////        testScriptJPanel.revalidate();//刷新窗体
////        if (testScriptJPanel.getComponentCount() > 15) {
////            myUpdateUI();//拖动下拉框到底部
////        }
////    }
//    //保存测试用例
////    private void saveTestCase() {
////        Component[] components = testScriptJPanel.getComponents();
////        List<JPanel> JPanelList = new ArrayList<>();
////        for (Component component : components) {
////            if (component instanceof JPanel) {
////                JPanelList.add((JPanel) component);
////            }
////        }
////        for (JPanel jPanel : JPanelList) {
////            String caseid = null;
////            String description = null;
////
////            for (Component component : jPanel.getComponents()) {
////                if (component instanceof JLabel) {
////                    caseid = ((JLabel) component).getText();
////                }
////                if (component instanceof JTextField) {
////                    description = ((JTextField) component).getText();
////                }
////            }
////            testCaseMap.put(caseid, description);
////        }
////    }
//
//    private void onCancel() {
//        dispose();
//    }
//
//    private void next() {
//        cardLayout.next(contentPane);
//    }
//
//    private void previons() {
//        cardLayout.previous(contentPane);
//    }
//
//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
//
//    public interface DialogCallBack {
//        void ok(TestScript testScript);
//    }
//
//    private void myUpdateUI() {
//        JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
//        jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
//    }
//
//    private String setIndex(Container component) {
//        int index = 0;
//        if (component.getComponentCount() == 0) {
//            index = 1;
//        } else {
//            index = component.getComponentCount() + 1;
//        }
//        return Integer.valueOf(index).toString();
//    }
//
//
//    //生成caseId
////    private String setCaseId() {
////        String caseId = null;
////        if (testScriptJPanel.getComponentCount() == 0) {
////            caseId = spliceCaseId();
////        } else {
////            caseIdIndex = testScriptJPanel.getComponentCount() + 1;
////            caseId = spliceCaseId();
////            while (!checkCaseId(caseId)) {
////                caseIdIndex++;
////                caseId = spliceCaseId();
////            }
////        }
////        return caseId;
////    }
//
//    //拼接caseId
////    private String spliceCaseId() {
////        String caseId = null;
////        if (caseIdIndex < 10) {
////            caseId = "NO00" + caseIdIndex;
////        } else if (caseIdIndex < 100 && caseIdIndex >= 10) {
////            caseId = "NO0" + caseIdIndex;
////        } else {
////            caseId = "NO" + caseIdIndex;
////        }
////        return caseId;
////    }
//
////    private boolean checkCaseId(String caseId) {
////        Component[] components = testScriptJPanel.getComponents();
////        boolean i = true;
////        for (Component component : components) {
////            if (component.getName().equals(caseId)) {
////                i = false;
////            }
////        }
////        return i;
////    }
////    private void setLookAndFeel(Component component){
////        try{
////            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////            String lookAndFeel = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
////            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
////            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
////            String lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
////
////            String lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
////            UIManager.setLookAndFeel(lookAndFeel);
////            SwingUtilities.updateComponentTreeUI(component);
////
////        }catch (Exception e){
////
////            e.getStackTrace();
////        }
////    }
//
//}
