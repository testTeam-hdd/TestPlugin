package com.plugin;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * dongdong Created by 11:36 AM  2019/2/19
 */
public class Test4 {


        public static void main(String[] args) {
            JFrame frame = new JFrame("QQ");

            MutableTreeNode root = new DefaultMutableTreeNode("QQ好友");
            MutableTreeNode aNode = new DefaultMutableTreeNode("我的好友");
            MutableTreeNode bNode = new DefaultMutableTreeNode("陌生人");
            MutableTreeNode cNode = new DefaultMutableTreeNode("黑名单");

            JButton button_1 = new JButton("胡某某");
            button_1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    JTextField jTextField = new JTextField("你好");
                    frame.add(jTextField,BorderLayout.SOUTH);
                }
            });
            root.insert(aNode, 0);
            root.insert(bNode, 1);
            root.insert(cNode, 2);
            aNode.insert(new DefaultMutableTreeNode(button_1), 0);
            aNode.insert(new DefaultMutableTreeNode("friend_b"), 1);
            bNode.insert(new DefaultMutableTreeNode("stranger_a"), 0);
            bNode.insert(new DefaultMutableTreeNode("stranger_b"), 1);
            cNode.insert(new DefaultMutableTreeNode("black_a"), 0);
            cNode.insert(new DefaultMutableTreeNode("black_b"), 1);

            DefaultTreeModel model = new DefaultTreeModel(root);
            JTree tree = new JTree(model);


            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane(tree), BorderLayout.NORTH);
            frame.pack();
            frame.setVisible(true);

        }
    }


