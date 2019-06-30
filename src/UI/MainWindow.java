package UI;

import listener.BrowseButtonActionListener;
import listener.JavaFileItemListener;
import listener.StartButtonActionListener;

import javax.swing.*;
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
        JLabel label = new JLabel("工作目录：");
        JLabel pathLabel = new JLabel("请点击浏览并选择目录");
        JButton browseButton = new JButton("浏览");
        Box horBox = Box.createHorizontalBox();
        Box horBox2 = Box.createHorizontalBox();
        horBox2.setMaximumSize(new Dimension(800,100));
        JLabel label2 = new JLabel("请选择将要测试的类和方法：");
        JComboBox<String> javaFiles = new JComboBox<>();
        JComboBox<String> methods = new JComboBox<>();
        JButton startButton = new JButton("开始测试");

        //建立嵌套关系
        horBox.add(Box.createHorizontalGlue());
        horBox.add(label);
        horBox.add(pathLabel);
        horBox.add(Box.createHorizontalStrut(20));
        horBox.add(browseButton);
        horBox.add(Box.createHorizontalGlue());
        horBox2.add(Box.createHorizontalGlue());
        horBox2.add(label2);
        horBox2.add(Box.createHorizontalStrut(20));
        horBox2.add(javaFiles);
        horBox2.add(Box.createHorizontalStrut(10));
        horBox2.add(methods);
        horBox2.add(Box.createHorizontalGlue());
        verBox.add(Box.createVerticalGlue());
        verBox.add(horBox);
        verBox.add(Box.createVerticalGlue());
        verBox.add(horBox2);
        verBox.add(Box.createVerticalGlue());
        verBox.add(startButton);
        verBox.add(Box.createVerticalGlue());
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
        startButtonActionListener.pathLabel = pathLabel;
        startButtonActionListener.file = javaFiles;
        startButtonActionListener.method = methods;
        startButton.addActionListener(startButtonActionListener);

        /*
        ClassTester classTester = new ClassTester();
        try {
            //选择方法数组中的第0个方法
            classTester.setCurrentMethod(0);
            //获取选中方法的参数
            String[] mp = classTester.getMethodParam();
            for(String a : mp){
                System.out.println(a);
            }
            //获取选中方法的返回值
            String[] mr = classTester.getMethodReturn();
            for(String a : mr){
                System.out.println(a);
            }
            //执行测试
            //System.out.println(classTester.runTest("C:\\Users\\13643\\Desktop\\sales.xls", new int[]{1},new int[]{5},new int[]{7}, 9));
            //System.out.println(classTester.runTest("C:\\Users\\13643\\Desktop\\sales.xls", new int[]{1,2,3},new int[]{5,4},new int[]{7,6}, 9));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 300, 800, 400);
        frame.setVisible(true);
    }
}
