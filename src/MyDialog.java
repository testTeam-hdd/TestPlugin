import com.Utils.MyJPanel;

import javax.swing.*;
import java.awt.event.*;

public class MyDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane jsp;
    private JTabbedPane tabbedPane;
    private JRadioButton normal;
    private JRadioButton exception;
    private JTextField testScryptDescription;
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
    private int inertDbIndex;
    private int checkDbIndex;
    private int checkResponseIndex;

    private DialogCallBack mCallBack;

    public MyDialog(DialogCallBack callBack) {
        this.mCallBack = callBack;
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));//盒子布局.从上到下
        setTitle("生成测试用例脚本");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        addTestCase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTestCase();
            }
        });
//        addResponseCheckData.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                addResponseDate();
//            }
//        });
//        addDbCheckData.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                addDbCheckDate();
//            }
//        });
//        removeDbData.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                removeDbData();
//            }
//        });
//        removeResponseCheckData.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                removeResponseCheckData();
//            }
//        });
//        removeDbCheckData.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                removeDbCheckData();
//            }
//        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (null != mCallBack) {
            mCallBack.ok(testScryptDescription.getText().trim(), testClass.getText().trim(), testMethod.getText().trim());
        }
        dispose();
    }

    private void addTestCase(){
        addCaseJPanel.setLayout(new BoxLayout(addCaseJPanel, BoxLayout.Y_AXIS));//盒子布局.从上到下
        addCaseJPanel.add(new MyJPanel());//添加1个自己定义的面板组件
        addCaseJPanel.add(Box.createVerticalStrut(600));
        myUpdateUI();//刷新界面
    }
//
//    private void addResponseDate(){
//        checkResponse.add(new MyJPanel(checkResponseIndex));//添加1个自己定义的面板组件
//        checkResponseIndex++;//自加1
//        myUpdateUI();//刷新界面
//    }
//    private void addDbCheckDate(){
//        checkDb.add(new MyJPanel(checkDbIndex));//添加1个自己定义的面板组件
//        checkDbIndex++;//自加1
//        myUpdateUI();//刷新界面
//    }
//    private void removeDbData(){
//        if (insertDb.getComponentCount() > 0) { // 得到jpc里的MyJPanel的组件数量
//            insertDb.remove(insertDb.getComponentCount() - 1);//删除末尾的一个组件 ,
//            inertDbIndex -= 1;
//            myUpdateUI();
//        }
//    }
//    private void removeResponseCheckData(){
//        if (checkResponse.getComponentCount() > 0) { // 得到jpc里的MyJPanel的组件数量
//            checkResponse.remove(checkResponse.getComponentCount() - 1);//删除末尾的一个组件 ,
//            checkResponseIndex -= 1;
//            myUpdateUI();
//        }
//    }
//    private void removeDbCheckData(){
//        if (checkDb.getComponentCount() > 0) { // 得到jpc里的MyJPanel的组件数量
//            checkDb.remove(checkDb.getComponentCount() - 1);//删除末尾的一个组件 ,
//            checkDbIndex -= 1;
//            myUpdateUI();
//        }
//    }
    private void onCancel() {
        dispose();
    }

    public interface DialogCallBack {
        void ok(String author, String testClassName, String testMethodName);
    }

    private void myUpdateUI() {
        JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
        jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
    }

}
