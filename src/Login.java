import javax.swing.*;
public class Login {
    public static void main(String[] args) {
        LoginFame frame = new LoginFame();
        frame.setTitle("Login");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}