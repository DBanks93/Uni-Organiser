import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;


public class LoginFame extends JFrame implements ActionListener {

    private final Container container = getContentPane();
   // private final JLabel userLabel = new JLabel("Username");
   // private final JLabel passwordLabel = new JLabel("Password");
    private final JTextField userTextField = new JTextField("Username");
    private final JPasswordField passwordField = new JPasswordField("Password");
    private final JButton loginButton = new JButton("Login");
    private final JButton resetButton = new JButton("Reset");
    private final JCheckBox showPassword = new JCheckBox("Show Password");

    private final JLabel logoLabel = new JLabel();
    private final ImageIcon logo = new ImageIcon("Logo.jpg");
    private final JLabel backgroundLabel = new JLabel();
    private final ImageIcon background = new ImageIcon("LoginBackground.jpg");

    LoginFame() {
        setLayoutManager();
        setLocationAndSize();
        logoLabel.setIcon(logo);
        backgroundLabel.setIcon(background);
       // userLabel.setForeground(Color.BLACK);
       // passwordLabel.setForeground(Color.BLACK);
        setColours();
        addCompoenetsToContatiner();
        addActionEvent();
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void setLocationAndSize() {
        //userLabel.setBounds(50,150,100,30);
        //passwordLabel.setBounds(50,220,100,30);
        userTextField.setBounds(100,150,150,30);
        passwordField.setBounds(100,220,150,30);
        showPassword.setBounds(100,275,150,30);
        loginButton.setBounds(50,300,100,30);
        resetButton.setBounds(200,300,100,30);
        logoLabel.setBounds(10, 10, 335, 120);
        backgroundLabel.setBounds(0, 0, 370, 400);
    }

    private void setColours() {
        userTextField.setOpaque(false);
        passwordField.setOpaque(false);
        showPassword.setOpaque(false);
        loginButton.setBackground(new Color(159, 31, 53));
        loginButton.setForeground(Color.WHITE);
        resetButton.setBackground(new Color(159, 31, 53));
        resetButton.setForeground(Color.WHITE);
    }

    private void addCompoenetsToContatiner() {
        //container.add(userLabel);
       // container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(logoLabel);
        container.add(backgroundLabel);
    }

    private void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    private boolean isValidCredentials(String username, String password) {
        if (username.equals("") && password.equals("")) return false;
        return checkPassword(username, password);
    }

    private boolean checkPassword(String username, String password) {
        return Run.checkCredentials(username, password);
    }

    @Override
    public  void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String passwordText = passwordField.getText();

            if (isValidCredentials(userText, passwordText)) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                setVisible(false);
                MainMenu.start();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }
}
