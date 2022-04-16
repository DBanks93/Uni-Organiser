import javax.swing.*;
import java.awt.*;

public class MainMenu {
    public static void main(String[] args) {
        start();
    }

    public static MainMenuFrame start() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        MainMenuFrame frame = new MainMenuFrame((int) width, (int) height);
        frame.setTitle("Main");
        frame.setVisible(true);
        frame.setBounds(10, 10, (int) width, (int) height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        return  frame;
    }
}

