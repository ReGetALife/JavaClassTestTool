package listener;

import utils.ClassTester;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButtonActionListener implements ActionListener {
    public JLabel pathLabel;
    public JComboBox<String> file;
    public JComboBox<String> method;
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("start testing...");
        try{
            //选择方法数组中的第0个方法
            ClassTester.setCurrentMethod(0);
            //获取选中方法的参数
            String[] mp = ClassTester.getMethodParam();
            for(String a : mp){
                System.out.println(a);
            }
            //获取选中方法的返回值
            String[] mr = ClassTester.getMethodReturn();
            for(String a : mr){
                System.out.println(a);
            }
        }catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }
}
