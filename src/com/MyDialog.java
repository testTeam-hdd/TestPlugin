package com;

import com.Utils.AddObjectJPanel;
import com.Vo.RequestParam;
import com.Vo.TestScript;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane jsp;
    private JTextField testScriptDescription;
    private JTextField testClass;
    private JTextField testMethod;
    private JRadioButton normal;
    private JRadioButton exception;
    private JPanel setDataJPanel;
    private JPanel scriptJPanel;
    private JButton nextJButton;
    private JButton previonsJBotton;
    private JButton DBinsertButton;
    private JButton DBcheckButton;
    private JPanel dbInsertJButton;
    private JPanel dbCheckJButton;
    private JTextField author;
    private int caseIdIndex = 1;
    private TestScript testScript = new TestScript();
    private DialogCallBack mCallBack;
    private CardLayout cardLayout = new CardLayout();

    private Map<String, String> testCaseMap = new HashMap<>();

    public MyDialog(DialogCallBack callBack) {
        this.mCallBack = callBack;
        setTitle("生成测试用例脚本");
        setContentPane(contentPane);
        contentPane.setLayout(cardLayout);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setJradio();

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        nextJButton.addActionListener(e -> next());

        previonsJBotton.addActionListener(e -> previons());

        DBinsertButton.addActionListener(e -> addButton(dbInsertJButton));

        DBcheckButton.addActionListener(e -> addButton(dbCheckJButton));

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
            testScript.setTestScriptDescription(testScriptDescription.getText().trim());
            testScript.setTestClass(testClass.getText().trim());
            testScript.setTestMethod(testMethod.getText().trim());
            testScript.setAuthor(author.getText().trim());
            testScript.setIsNormal(normal.isSelected() ? true : false);
            testScript.setDbCheckList(getButtonList(dbCheckJButton));
            testScript.setDbList(getButtonList(dbInsertJButton));
            mCallBack.ok(testScript,this);
        }
    }

    private void setJradio() {
        //将按钮添加到组，否则按钮会支持多选
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(normal);
        buttonGroup.add(exception);
        normal.setSelected(true);
    }

    private void addButton(Container container) {
        dbInsertJButton.setLayout(new BoxLayout(dbInsertJButton, BoxLayout.Y_AXIS));
        dbCheckJButton.setLayout(new BoxLayout(dbCheckJButton, BoxLayout.Y_AXIS));
        String index = setIndex(container);
        JPanel myJPanl = new AddObjectJPanel(index, container, 540, 30);
        myJPanl.setName(index);
        container.add(myJPanl);
        container.revalidate();
        if (container.getComponentCount() > 15) {
            myUpdateUI();//拖动下拉框到底部
        }
    }

    private List<String> getButtonList(Container container){
        Component[] components = container.getComponents();
        List<JPanel> JPanelList = new ArrayList<>();
        List<String> ObjectList = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanelList.add((JPanel) component);
            }
        }
        for (JPanel jPanel : JPanelList) {
            String description = null;

            for (Component component : jPanel.getComponents()) {
                if (component instanceof JTextField) {
                    description = ((JTextField) component).getText();
                }
            }
            ObjectList.add(description);
        }
        return ObjectList;
    }


    private void onCancel() {
        dispose();
    }

    private void next() {
        cardLayout.next(contentPane);
    }

    private void previons() {
        cardLayout.previous(contentPane);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public interface DialogCallBack {
        void ok(TestScript testScript,MyDialog myDialog);
    }

    private void myUpdateUI() {
        JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
        jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
    }

    private String setIndex(Container component) {
        String index = null;
        if (component.getComponentCount() == 0) {
            index = "01";
        } else {
            int newindex = component.getComponentCount() + 1;
            index = newindex<10?"0"+Integer.valueOf(newindex).toString():Integer.valueOf(newindex).toString();
        }
        return index;
    }
}
