package listener;

import utils.ClassTester;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.file.Paths;

public class JavaFileItemListener implements ItemListener {
    public JLabel pathLabel;
    public JComboBox<String> cmb;
    @Override
    public void itemStateChanged(ItemEvent e) {
        //切换项时会触发两次，分别是 selected 和 deselected，我们只关心选中
        if(e.getStateChange()==ItemEvent.SELECTED){
            cmb.removeAllItems();
            String javaFile = e.getItem().toString();
            try{
                //获取文件中类的所有方法
                String[] methods = ClassTester.getClassMethods(Paths.get(pathLabel.getText(),javaFile).toString());
                for(String m:methods){
                    cmb.addItem(m);
                }
            }
            catch (Exception exc){
                System.out.println(exc.getMessage());
            }
        }
    }
}
