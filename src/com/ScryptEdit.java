package com;

import com.Utils.AddCsvJPanel;
import com.Utils.CSVParseUtil;
import com.Vo.CsvVO;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ScryptEdit extends JDialog {
    private JPanel contentPane;
    private JPanel csvContent;
    private JPanel csvName;
    private JPanel mainFile;
    private JPanel preparationFile;
    private JPanel checkFile;
    private JTabbedPane tabbedPane1;
    private CardLayout cardLayout;
//    private JButton buttonOK;
//    private JButton buttonCancel;

    public ScryptEdit(List<CsvVO> list) {
        setContentPane(contentPane);
        setModal(true);
        setSize(1500, 1000);
        setTitle("编辑测试数据");
        mainFile.setLayout(new FlowLayout());
        checkFile.setLayout(new FlowLayout());
        preparationFile.setLayout(new FlowLayout());
        cardLayout = new CardLayout();
//        csvName.setLayout(new BoxLayout(csvName, BoxLayout.Y_AXIS));
        csvContent.setLayout(cardLayout);
        for (CsvVO csvVO : list) {
            if (csvVO.getCsvName().contains("Test.")) {
                addJText(csvVO, mainFile);
            } else if (csvVO.getCsvName().contains("check")) {
                addJText(csvVO, checkFile);
            } else {
                addJText(csvVO, preparationFile);
            }
        }
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

    /**
     * 根据csv文件数量添加Jtext,并添加单击监控事件，单击则展示内容
     */
    private void addJText(CsvVO csvVO, Container container) {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        AddCsvJPanel addCsvJPanel = new AddCsvJPanel(container, 150, 30, csvVO.getCsvName());
        container.add(addCsvJPanel);
        addTable(csvVO);
        Component[] components = addCsvJPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField) {
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cardLayout.show(csvContent, csvVO.getCsvName());
                        contentPane.revalidate();

                    }
                });
            }

        }
        container.revalidate();
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


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        ScryptEdit dialog = new ScryptEdit();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
