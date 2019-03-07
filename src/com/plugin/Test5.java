package com.plugin;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;

/**
 * dongdong Created by 2:32 PM  2019/3/4
 */
public class Test5 extends JFrame {
    JTree jTree = new JTree();
    JTextField jTextField = new JTextField();
    GridLayout lay = new GridLayout(1, 1);

    public Test5() {
        treeinit();
        this.setSize(400, 400);
        this.setLayout(lay);
        this.add(jTree);
        this.add(jTextField);
        this.setVisible(true);
        jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeValueChanged(evt);
            }
        });
    }

    private void jTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();//返回最后选定的节点
        if (selectedNode.toString().equals("child")) {
            jTextField.setText("I LOVE YOU");
        }

    }

    public void treeinit() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode father = new DefaultMutableTreeNode("father");
        DefaultMutableTreeNode child = new DefaultMutableTreeNode("child");
        father.add(child);
        root.add(father);
        TreeModel treeModel = new DefaultTreeModel(root);
        jTree.setModel(treeModel);
    }

    public static void main(String args[]) {
        new Test5();
    }
}

