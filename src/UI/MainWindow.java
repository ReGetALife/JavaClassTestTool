package UI;

import listener.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class MainWindow {
    /**
     * {
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    public static void createAndShowGUI() {
        // 创建窗口和各种组件
        JFrame frame = new JFrame("java类自动测试工具");
        Box verBox = Box.createVerticalBox();
        JLabel label = new JLabel("1. 类文件目录：");
        JLabel pathLabel = new JLabel("请点击浏览并选择目录");
        JButton browseButton = new JButton("浏览");
        JLabel label1 = new JLabel("2. 测试用例文件：");
        JLabel xlsPath = new JLabel("请点击浏览并选择文件");
        JButton browseButton2 = new JButton("浏览");
        Box horBox = Box.createHorizontalBox();
        Box horBox1 = Box.createHorizontalBox();
        Box horBox2 = Box.createHorizontalBox();
        Box horBox3 = Box.createHorizontalBox();
        Box horBox4 = Box.createHorizontalBox();
        Box horBox5 = Box.createHorizontalBox();
        Box horBox6 = Box.createHorizontalBox();
        horBox2.setMaximumSize(new Dimension(800, 100));
        JLabel label2 = new JLabel("3. 请选择将要测试的类和方法：");
        JComboBox<String> javaFiles = new JComboBox<>();
        JComboBox<String> methods = new JComboBox<>();
        JButton startButton = new JButton("开始测试");
        DefaultTableModel tableModel = new DefaultTableModel(1, 0);
        JTable table = new JTable(tableModel);
        JLabel label3 = new JLabel("测试结果：");
        JLabel label4 = new JLabel("4. 请为下表中的变量指定其在excel表格中的列位置，输入后请回车");

        //建立嵌套关系
        horBox.add(Box.createHorizontalStrut(20));
        horBox.add(label);
        horBox.add(pathLabel);
        horBox.add(Box.createHorizontalStrut(20));
        horBox.add(browseButton);
        horBox.add(Box.createHorizontalGlue());
        horBox1.add(Box.createHorizontalStrut(20));
        horBox1.add(label1);
        horBox1.add(xlsPath);
        horBox1.add(Box.createHorizontalStrut(20));
        horBox1.add(browseButton2);
        horBox1.add(Box.createHorizontalGlue());
        horBox2.add(Box.createHorizontalStrut(20));
        horBox2.add(label2);
        horBox2.add(Box.createHorizontalStrut(20));
        horBox2.add(javaFiles);
        horBox2.add(Box.createHorizontalStrut(10));
        horBox2.add(methods);
        horBox2.add(Box.createHorizontalGlue());
        horBox3.add(Box.createHorizontalStrut(20));
        horBox3.add(label4);
        horBox3.add(Box.createHorizontalGlue());
        horBox4.add(Box.createHorizontalStrut(20));
        horBox4.add(new JScrollPane(table));
        horBox4.add(Box.createHorizontalStrut(20));
        horBox5.add(Box.createHorizontalStrut(20));
        horBox5.add(startButton);
        horBox5.add(Box.createHorizontalGlue());
        horBox6.add(Box.createHorizontalStrut(20));
        horBox6.add(label3);
        horBox6.add(Box.createHorizontalGlue());
        verBox.add(Box.createVerticalStrut(20));
        verBox.add(horBox);
        verBox.add(Box.createVerticalStrut(20));
        verBox.add(horBox1);
        verBox.add(Box.createVerticalStrut(20));
        verBox.add(horBox2);
        verBox.add(Box.createVerticalStrut(20));
        verBox.add(horBox3);
        verBox.add(Box.createVerticalStrut(20));
        verBox.add(horBox4);
        verBox.add(Box.createVerticalStrut(20));
        verBox.add(horBox5);
        verBox.add(Box.createVerticalStrut(20));
        verBox.add(horBox6);
        verBox.add(Box.createVerticalStrut(20));
        frame.add(verBox);

        //添加事件监听
        BrowseButtonActionListener browseButtonActionListener = new BrowseButtonActionListener();
        browseButtonActionListener.pathLabel = pathLabel;
        browseButtonActionListener.cmb = javaFiles;
        browseButton.addActionListener(browseButtonActionListener);

        JavaFileItemListener javaFileItemListener = new JavaFileItemListener();
        javaFileItemListener.pathLabel = pathLabel;
        javaFileItemListener.cmb = methods;
        javaFiles.addItemListener(javaFileItemListener);

        StartButtonActionListener startButtonActionListener = new StartButtonActionListener();
        startButtonActionListener.table = table;
        startButtonActionListener.xlsPath = xlsPath;
        startButtonActionListener.result = label3;
        startButton.addActionListener(startButtonActionListener);

        MethodItemListener methodItemListener = new MethodItemListener();
        methodItemListener.tableModel = tableModel;
        methods.addItemListener(methodItemListener);

        BrowseButton2ActionListener browseButton2ActionListener = new BrowseButton2ActionListener();
        browseButton2ActionListener.xlsPath = xlsPath;
        browseButton2.addActionListener(browseButton2ActionListener);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 300, 800, 400);
        frame.setVisible(true);
    }
}
