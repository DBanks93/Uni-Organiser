import javax.swing.*;
import java.awt.*;

public class CalenderFrame extends Frame {

    public CalenderFrame(MainMenuFrame mainMenuFrame) {
        super(mainMenuFrame, "Calender");

        JLabel text = new JLabel("Test");
        text.setBounds(100, 200, 100, 100);
        mainMenuFrame.setTitle("Calender");

        ImageIcon logo = new ImageIcon("Logo.jpg");
        JLabel hello = new JLabel();
        hello.setIcon(logo);
        text.setBounds(500, 500, 300, 300);
        super.addComponent(hello);
        super.addComponent(text);
    }
}
