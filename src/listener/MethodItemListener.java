package listener;

import utils.ClassTester;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.file.Paths;

public class MethodItemListener implements ItemListener {
    public DefaultTableModel tableModel;
    @Override
    public void itemStateChanged(ItemEvent e) {
        //切换项时会触发两次，分别是 selected 和 deselected，我们只关心选中
        if(e.getStateChange()==ItemEvent.SELECTED){
            String method = e.getItem().toString();
            //由于其他地方已经执行过编译，这里可以直接获取方法
            String[] methods = ClassTester.getClassMethods();
            for(int i =0;i<methods.length;i++){
                if(methods[i].equals(method)){
                    try{
                        //设置当前测试的方法
                        ClassTester.setCurrentMethod(i);
                        //System.out.println(i);
                    }
                    catch (Exception exc){
                        System.out.println(exc.getMessage());
                    }
                }
            }
            //列出当前测试方法的参数供用户选择
            try{
                //清空table
                tableModel.setColumnCount(0);
                //获取选中方法的参数
                String[] mp = ClassTester.getMethodParam();
                for (String s : mp) {
                    tableModel.addColumn("I: " + s);
                }
                //获取选中方法的返回值
                String[] mr = ClassTester.getMethodReturn();
                for (String s : mr) {
                    tableModel.addColumn("O: " + s);
                }
                //测试用例的正确的返回值
                for (String s : mr) {
                    tableModel.addColumn("target: " + s);
                }
                tableModel.addColumn("result");
            }catch (Exception exc){
                System.out.println(exc.getMessage());
            }
        }
    }
}
