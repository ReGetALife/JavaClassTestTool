package listener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BrowseButton2ActionListener implements ActionListener {
    public JLabel xlsPath;

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JFileChooser fc = new JFileChooser(new File("."));
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("*.xls", "xls");
        fc.setFileFilter(fileNameExtensionFilter);
        int val = fc.showOpenDialog(null);    //文件打开对话框
        if (val == JFileChooser.APPROVE_OPTION) {
            //正常选择文件
            String workPath = fc.getSelectedFile().toString();
            xlsPath.setText(workPath);

        } else {
            //未正常选择文件，如选择取消按钮
            xlsPath.setText("请点击浏览并选择文件");
        }
    }
}
