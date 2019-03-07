package com;

import com.Utils.*;
import com.Vo.CsvVO;
import com.exception.PluginErrorMsg;
import com.exception.PluginRunTimeException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ScryptEdit extends JDialog {
    private JPanel contentPane;
    private JPanel csvContent;
    private JPanel csvName;
    private JTabbedPane tabbedPane1;
    private JTree jTree;
    private JComboBox comboBox1;
    private CardLayout cardLayout;

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("测试数据");
    DefaultMutableTreeNode mainCsv = new DefaultMutableTreeNode("主测试文件");
    DefaultMutableTreeNode dataPreparation = new DefaultMutableTreeNode("测试数据");
    TreeModel treeModel = new DefaultTreeModel(root);
//    private JButton buttonOK;
//    private JButton buttonCancel;

    public ScryptEdit(List<CsvVO> list, String csvPath, Project project) {
        setContentPane(contentPane);
        setModal(true);
        setSize(1500, 1100);
        setTitle("编辑测试数据");
        cardLayout = new CardLayout();
        csvContent.setLayout(cardLayout);
        setJcomboBox(csvPath, project);
        initTree(list);
        for (CsvVO csvVO : list) {
            addTable(csvVO);
        }
        jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();//返回最后选定的节点
                cardLayout.show(csvContent, selectedNode.toString());
                contentPane.validate();
                contentPane.repaint();
                contentPane.revalidate();
            }
        });

//        getRootPane().setDefaultButton(buttonOK);

//        buttonOK.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onOK();
//            }
//        });
//
//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
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


    private void addTable(CsvVO csvVO) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(csvVO.getRowNum(), csvVO.getColNum());
        JTable table = new JBTable(defaultTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(1300, 1000));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt, table, createPopupMenu());
            }
        });
        for (int i = 0; i < csvVO.getColNum(); i++) {
            for (int j = 0; j < csvVO.getRowNum(); j++) {
                CSVParseUtil csvParseUtil = new CSVParseUtil();
                table.setValueAt(csvParseUtil.getString(csvVO.getContent(), j, i), j, i);
            }
        }

        JScrollPane scrollPane = new JBScrollPane(table);
        csvContent.add(scrollPane, csvVO.getCsvName());
        csvContent.revalidate();
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt, JTable jTable1, JPopupMenu m_popupMenu) {

        mouseRightButtonClick(evt, jTable1, m_popupMenu);
    }

    private void mouseRightButtonClick(java.awt.event.MouseEvent evt, JTable jTable1, JPopupMenu m_popupMenu) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            //通过点击位置找到点击为表格中的行
            int focusedRowIndex = jTable1.rowAtPoint(evt.getPoint());
            if (focusedRowIndex == -1) {
                return;
            }
            //将表格所选项设为当前右键点击的行
            jTable1.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            //弹出菜单
            m_popupMenu.show(jTable1, evt.getX(), evt.getY());
        }

    }

    private void setJcomboBox(String csvPath, Project project) {
        comboBox1.addItem("DB数据插入");
        comboBox1.addItem("DB数据校验");
        comboBox1.addItem("出入参对象");
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String text = (String) comboBox1.getSelectedItem();
                    switch (text) {
                        case "DB数据插入":
                            addDbinsertCsv(csvPath, project);
                            break;
                        case "DB数据校验":
                            addDbcheckCsv(csvPath, project);
                            break;
                        case "出入参对象":
                            addObjectCsv(csvPath, project);
                            break;
                    }

                }
            }
        });
    }

    private void addDbcheckCsv(String csvpath, Project project) {
        String tableName = JOptionPane.showInputDialog(this, "输入需要校验的数据库表名", "新增数据校验文件", JOptionPane.PLAIN_MESSAGE);
        if (EmptyUtils.isNotEmpty(tableName)) {
            String filename = "dbCheck" + "_" + tableName + ".csv";
            csvpath = csvpath + "/"+filename;
            GenerateCsv.createDBCheckTemplateCsv(tableName, csvpath, project);
            refresh(csvpath,filename);
        } else {
            throw new PluginRunTimeException(PluginErrorMsg.INPUT_IS_EMPTY);
        }
    }

    private void addDbinsertCsv(String csvpath, Project project) {
        String classname = JOptionPane.showInputDialog(this, "输入需要插入的表对应的实体类", "新增数据准备文件", JOptionPane.PLAIN_MESSAGE);
        if (EmptyUtils.isNotEmpty(classname)) {
            String filename = "dbInsert" + "_" + classname + ".csv";
            csvpath = csvpath + "/"+filename;
            GenerateCsv.createTransverseCsvForCheck(PsiUtil.getPsiClass(project, classname), csvpath);
            refresh(csvpath,filename);
        } else {
            throw new PluginRunTimeException(PluginErrorMsg.INPUT_IS_EMPTY);
        }
    }

    private void addObjectCsv(String csvpath, Project project) {
        String classname = JOptionPane.showInputDialog(this, "输入需要需要生成对象的类名", "新增数据校验文件", JOptionPane.PLAIN_MESSAGE);
        if (EmptyUtils.isNotEmpty(classname)) {
            String filename = "objectCheck" + "_" + classname + ".csv";
            csvpath = csvpath + "/"+filename;
            GenerateCsv.createVerticalCsvForCheck(PsiUtil.getPsiClass(project, classname), csvpath);
            refresh(csvpath,filename);
        } else {
            throw new PluginRunTimeException(PluginErrorMsg.INPUT_IS_EMPTY);
        }
    }

    private void refresh(String path,String filename){
        Messages.showInfoMessage("添加成功", "提示");
        CsvVO csvVO = FileUtil.getOneFileContent(path,filename);
        DefaultMutableTreeNode csvName = new DefaultMutableTreeNode(csvVO.getCsvName());
        dataPreparation.add(csvName);
        addTable(csvVO);
        jTree.validate();
        jTree.repaint();
        jTree.revalidate();
        this.validate();
        contentPane.validate();
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu m_popupMenu = new JPopupMenu();

        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("  删除  ");
        delMenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //该操作需要做的事

            }
        });
        m_popupMenu.add(delMenItem);
        return m_popupMenu;
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void initTree(List<CsvVO> list) {
        for (CsvVO csvVO : list) {
            DefaultMutableTreeNode csvName = new DefaultMutableTreeNode(csvVO.getCsvName());

            if (csvVO.getCsvName().contains("Test.")) {
                mainCsv.add(csvName);
            } else {
                dataPreparation.add(csvName);
            }
        }
        root.add(mainCsv);
        root.add(dataPreparation);

        jTree.setModel(treeModel);
    }

//    private boolean isCheck(String csvName) {
//        Boolean ischeckFile = true;
//        if (!csvName.contains("che")||!csvName.contains("res")) {
//            ischeckFile = false;
//        }
//        return ischeckFile;
//    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

//    public static void main(String[] args) {
//        ScryptEdit dialog = new ScryptEdit();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
