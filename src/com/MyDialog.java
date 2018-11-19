package com;

import com.Utils.AddObjectJPanel;
import com.Utils.EmptyUtils;
import com.Vo.RequestParam;
import com.Vo.TestScript;
import com.exception.PluginErrorMsg;
import com.exception.PluginRunTimeException;
import com.intellij.openapi.ui.Messages;

import javax.management.RuntimeErrorException;
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
    private JPanel scriptJPanel;
    private JPanel setDataJPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane jsp;
    private JTextField testScriptDescription;
    private JTextField testClass;
    private JTextField testMethod;
    private JRadioButton normal;
    private JRadioButton exception;
    private JButton nextJButton;
    private JButton previonsJBotton;
    private JButton DBinsertButton;
    private JButton DBcheckButton;
    private JPanel dbInsertJButton;
    private JPanel dbCheckJButton;
    private JTextField author;
    private JButton ObjectCheckButton;
    private JPanel ObjectCheckJButton;
    private JTextField module;
    private TestScript testScript = new TestScript();
    private DialogCallBack mCallBack;
    private CardLayout cardLayout = new CardLayout();

    public MyDialog(DialogCallBack callBack) {
        this.mCallBack = callBack;
        setTitle("生成测试用例脚本");
        setContentPane(contentPane);
        contentPane.setLayout(cardLayout);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setJradio();

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        nextJButton.addActionListener(e -> next());

        previonsJBotton.addActionListener(e -> previons());

        DBinsertButton.addActionListener(e -> addButton(dbInsertJButton));

        DBcheckButton.addActionListener(e -> addButton(dbCheckJButton));

        ObjectCheckButton.addActionListener(e -> addButton(ObjectCheckJButton));

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
            try {
                checkParam();
                testScript.setTestScriptDescription(testScriptDescription.getText().trim());
                testScript.setTestClass(testClass.getText().trim());
                testScript.setTestMethod(testMethod.getText().trim());
                testScript.setAuthor(author.getText().trim());
                testScript.setModule(module.getText().trim());
                testScript.setIsNormal(normal.isSelected() ? true : false);
                testScript.setDbCheckList(getButtonList(dbCheckJButton));
                testScript.setDbList(getButtonList(dbInsertJButton));
                testScript.setObjectList(getButtonList(ObjectCheckJButton));
                mCallBack.ok(testScript, this);
            }catch (PluginRunTimeException e){
                Messages.showInfoMessage(e.getErrorMsg(), "提示");
            }
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
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
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
        String index ;
        if (component.getComponentCount() == 0) {
            index = "01";
        } else {
            int newindex = component.getComponentCount() + 1;
            index = newindex<10?"0"+Integer.valueOf(newindex).toString():Integer.valueOf(newindex).toString();
        }
        return index;
    }
    private void checkParam(){
        if (EmptyUtils.isEmpty(testClass.getText().trim())) {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_CLASS_IS_EMPTY);
        }
        if (EmptyUtils.isEmpty(testMethod.getText().trim())) {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_METHOD_IS_EMPTY);
        }
        if (EmptyUtils.isEmpty(testScriptDescription.getText().trim())) {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_DESCRIPTION_IS_EMPTY);
        }
        if (EmptyUtils.isEmpty(author.getText().trim())) {
            throw new PluginRunTimeException(PluginErrorMsg.TEST_AUTHOR_IS_EMPTY);
        }
    }
}
