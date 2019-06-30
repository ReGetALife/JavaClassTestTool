import UI.MainWindow;

public class Main {

    public static void main(String[] args) {
        // 显示应用 GUI
        javax.swing.SwingUtilities.invokeLater(MainWindow::createAndShowGUI);
    }
}
