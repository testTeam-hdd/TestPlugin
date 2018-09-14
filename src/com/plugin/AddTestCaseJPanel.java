package com.plugin;//package com.Utils;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
///**
// * dongdong Created by 下午2:29  2018/7/5
// */
//public class AddTestCaseJPanel extends JPanel {
//    private JTextField jtf;
//    private JButton button;
//    private JLabel jLabel;
//
//    public AddTestCaseJPanel(String caseId, Container container) {
//        jtf = new JTextField(70);
//        jLabel = new JLabel();
//        jLabel.setText(caseId);
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
//                        container.revalidate();
//                    }
//                }
//            }
//        });
//        this.add(jLabel);
//        this.add(jtf);
//        this.add(button);
//        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));//盒子布局.从左到右
//        this.setMaximumSize(new Dimension(980, 35));
//    }
//}
//
