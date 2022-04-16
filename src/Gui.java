import javax.swing.*;
import java.awt.*;

public class Gui {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static double width = screenSize.getWidth();
    static  double height = screenSize.getHeight();

    public static void main(String args[]) {
        JFrame frame = new JFrame("My first GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) width,(int) height);

        JButton button = new JButton("Press");
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}
