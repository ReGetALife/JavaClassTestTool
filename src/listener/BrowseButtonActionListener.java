package listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BrowseButtonActionListener implements ActionListener {
    public JLabel pathLabel;
    public JComboBox<String> cmb;

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JFileChooser fc = new JFileChooser(new File("."));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int val = fc.showOpenDialog(null);    //文件打开对话框
        if (val == JFileChooser.APPROVE_OPTION) {
            //正常选择文件
            String workPath = fc.getSelectedFile().toString();
            pathLabel.setText(workPath);
            cmb.removeAllItems();
            File dir = new File(workPath);
            if (dir.exists() && dir.isDirectory()) {
                String[] javaFiles = dir.list();
                if (javaFiles != null) {
                    for (String f : javaFiles) {
                        if(f.endsWith(".java")){
                            cmb.addItem(f);
                        }
                    }
                }
            }

        } else {
            //未正常选择文件，如选择取消按钮
            pathLabel.setText("请点击浏览并选择目录");
        }
    }
}
