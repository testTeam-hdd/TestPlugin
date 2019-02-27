package com.Utils;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * dongdong Created by 下午2:29  2018/7/5
 */
public class AddCsvJPanel extends JPanel {
    private JTextField jtf;
//    private JButton button;

    public AddCsvJPanel( Container container, int width, int height,String csvName) {
        jtf = new JTextField();
        jtf.setText(csvName.trim());
        jtf.setEditable(false);
//        button = new JButton();
//        button.setText("删除");
//        button.addActionListener(new ActionListener() {
//            @Override
//            //删除选中的JPanel
//            public void actionPerformed(ActionEvent e) {
//                Component[] components = container.getComponents();
//                for (Component component : components) {
//                    if (component.getName().equals(jLabel.getText())) {
//                        container.remove(component);
//                        container.validate();
//                        container.repaint();
//                        container.revalidate();
//                    }
//                }
//            }
//        });
        this.add(jtf);
//        this.add(button);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//盒子布局.从左到右
        this.setPreferredSize(new Dimension(width, height));
    }
}

