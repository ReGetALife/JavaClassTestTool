package listener;

import utils.ClassTester;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButtonActionListener implements ActionListener {
    public JLabel xlsPath;
    public JTable table;
    public JLabel result;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("start testing...");
        try {
            //获取方法的参数个数
            int inCount = ClassTester.getMethodParam().length;
            //获取方法的返回值个数
            int outCount = ClassTester.getMethodReturn().length;
            //构建runTest参数
            int[] paramPos = new int[inCount];
            for (int i = 0; i < inCount; i++) {
                paramPos[i] = Integer.parseInt(table.getModel().getValueAt(0, i).toString());
            }
            int[] resultPos = new int[outCount];
            for (int i = 0; i < outCount; i++) {
                resultPos[i] = Integer.parseInt(table.getModel().getValueAt(0, i + inCount).toString());
            }
            int[] returnPos = new int[outCount];
            for (int i = 0; i < outCount; i++) {
                returnPos[i] = Integer.parseInt(table.getModel().getValueAt(0, i + inCount + outCount).toString());
            }
            int isTruePos = Integer.parseInt(table.getModel().getValueAt(0, outCount + inCount + outCount).toString());

            //执行测试
            String re = ClassTester.runTest(xlsPath.getText(), paramPos, resultPos, returnPos, isTruePos);
            System.out.println(re);
            result.setText("测试结果："+re);

        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }
}
