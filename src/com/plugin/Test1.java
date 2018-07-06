package com.plugin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * dongdong Created by 上午10:44  2018/7/5
 */
public class Test1 {


    public static void main(String[] args) {


        final JFrame jf = new JFrame("测试窗口");
        jf.setSize(400, 400);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
         * 1. 消息对话框（信息消息）
         */
        JButton btn01 = new JButton("showMessageDialog（信息消息）");
        btn01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 消息对话框无返回, 仅做通知作用
                JOptionPane.showMessageDialog(
                        jf,
                        "Hello Information Message",
                        "消息标题",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        /*
         * 2. 消息对话框（警告消息）
         */
        JButton btn02 = new JButton("showMessageDialog（警告消息）");
        btn02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 消息对话框无返回, 仅做通知作用
                JOptionPane.showMessageDialog(
                        jf,
                        "Hello Warning Message",
                        "消息标题",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        /*
         * 3. 确认对话框
         */
        JButton btn03 = new JButton("showConfirmDialog");
        btn03.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * 返回用户点击的选项, 值为下面三者之一:
                 *     是:   JOptionPane.YES_OPTION
                 *     否:   JOptionPane.NO_OPTION
                 *     取消: JOptionPane.CANCEL_OPTION
                 *     关闭: JOptionPane.CLOSED_OPTION
                 */
                int result = JOptionPane.showConfirmDialog(
                        jf,
                        "确认删除？",
                        "提示",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );
                System.out.println("选择结果: " + result);
            }
        });

        /*
         * 4. 输入对话框（文本框输入）
         */
        JButton btn04 = new JButton("showInputDialog（文本框输入）");
        btn04.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示输入对话框, 返回输入的内容
                String inputContent = JOptionPane.showInputDialog(
                        jf,
                        "输入你的名字:",
                        "默认内容"
                );
                System.out.println("输入的内容: " + inputContent);
            }
        });

        /*
         * 5. 输入对话框（下拉框选择）
         */
        JButton btn05 = new JButton("showInputDialog（下拉框选择）");
        btn05.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] selectionValues = new Object[]{"香蕉", "雪梨", "苹果"};

                // 显示输入对话框, 返回选择的内容, 点击取消或关闭, 则返回null
                Object inputContent = JOptionPane.showInputDialog(
                        jf,
                        "选择一项: ",
                        "标题",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        selectionValues,
                        selectionValues[0]
                );
                System.out.println("输入的内容: " + inputContent);
            }
        });

        /*
         * 6. 选项对话框
         */
        JButton btn06 = new JButton("showOptionDialog");
        btn06.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 选项按钮
                Object[] options = new Object[]{"香蕉", "雪梨", "苹果"};

                // 显示选项对话框, 返回选择的选项索引, 点击关闭按钮返回-1
                int optionSelected = JOptionPane.showOptionDialog(
                        jf,
                        "请点击一个按钮选择一项",
                        "对话框标题",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,    // 如果传null, 则按钮为 optionType 类型所表示的按钮（也就是确认对话框）
                        options[0]
                );

                if (optionSelected >= 0) {
                    System.out.println("点击的按钮: " + options[optionSelected]);
                }
            }
        });

        // 垂直排列按钮
        Box vBox = Box.createVerticalBox();
        vBox.add(btn01);
        vBox.add(btn02);
        vBox.add(btn03);
        vBox.add(btn04);
        vBox.add(btn05);
        vBox.add(btn06);

        JPanel panel = new JPanel();
        panel.add(vBox);

        jf.setContentPane(panel);
        jf.setVisible(true);
    }

}


