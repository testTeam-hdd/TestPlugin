package com.plugin;

import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * dongdong Created by 上午10:53  2018/7/5
 * 动态增删文本框
 */
public class Test2 extends JFrame implements ActionListener {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(Test2.class);

    JPanel jpc;//存放组件的面板
    JScrollPane jsp;//滚动面板
    JButton jbAdd, jbRemove, jbReset;// 增加,删除按钮
    int index = 1;//开始的字符

    //构造函数
    public Test2() {
        jpc = new JPanel();
        jpc.setLayout(new BoxLayout(jpc, BoxLayout.Y_AXIS));//盒子布局.从上到下
        jsp = new JScrollPane(jpc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(jsp);

        jbAdd = new JButton("增加");
        jbAdd.addActionListener(this);
        jbRemove = new JButton("删除");
        jbRemove.addActionListener(this);
        jbReset = new JButton("重置");
        jbReset.addActionListener(this);
        JPanel jps = new JPanel();
        jps.add(jbAdd);
        jps.add(jbRemove);
        jps.add(jbReset);
        add(jps, BorderLayout.SOUTH);
        setTitle("增删组件");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 220);//大小
        setLocationRelativeTo(null);//居中
    }

    //main函数
    public static void main(String[] args) {
        logger.info("123");
        new Test2().setVisible(true);//初始化并可见
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jb = (JButton) e.getSource();
        if (jb == jbAdd) {//当点击添加按钮时
            jpc.add(new MyJPanel(index));//添加1个自己定义的面板组件
            index++;//自加1
            myUpdateUI();//刷新界面
            logger.info("当前面板组件数量为：{}",index);
        } else if (jb == jbRemove) {//当点击删除按钮时
            if (jpc.getComponentCount() > 0) { // 得到jpc里的MyJPanel的组件数量
                jpc.remove(jpc.getComponentCount() - 1);//删除末尾的一个组件 ,
                index -= 1;
                myUpdateUI();
            }
        } else if (jb == jbReset) {
            for (int i = 0; i < jpc.getComponentCount(); i++) {
                MyJPanel mjp = (MyJPanel) jpc.getComponent(i);
                //也就是说取值,可以根据文本框所在的位置来取
                System.out.println("第" + (i + 1) + "个文本框的值是" + mjp.getJTFValue());
                mjp.setJTFValue("");//清空,重置
                System.out.println("第" + (i + 1) + "个文本框的值已清空重置");
            }
        }

    }

    //刷新界面函数
    private void myUpdateUI() {
        SwingUtilities.updateComponentTreeUI(this);//添加或删除组件后,更新窗口
        JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
        jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
    }

}

//自定义一个JPanle类
class MyJPanel extends JPanel {
    public JTextField jtf;

    public MyJPanel(int index) {
        JLabel jl = new JLabel("字符" + index);
        jtf = new JTextField(15);
        add(jl);
        add(jtf);
    }

    //获取文本框的值
    public String getJTFValue() {
        return jtf.getText();
    }

    //设置文本框的值
    public void setJTFValue(String value) {
        jtf.setText(value);
    }
}

