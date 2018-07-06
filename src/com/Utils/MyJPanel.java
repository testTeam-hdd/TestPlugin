package com.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * dongdong Created by 下午2:29  2018/7/5
 */
public class MyJPanel extends JPanel {
    public JTextField jtf;
    private JButton button;

    public MyJPanel() {
        jtf = new JTextField(8);
        button = new JButton();
        button.setText("删除");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        add(jtf);
        add(button);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));//盒子布局.从上到下
        this.revalidate();
    }

    //获取文本框的值
    public String getJTFValue() {
        return jtf.getText();
    }

    //设置文本框的值
    public void setJTFValue(String value) {
        jtf.setText(value);
    }

    private void delete(){
        this.remove(jtf);
        this.remove(button);
        this.revalidate();
    }
}

