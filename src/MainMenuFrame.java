import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.util.HashMap;

public class MainMenuFrame extends JFrame implements ActionListener {
    private Frame openWindow = null;

    private final int[] screenSize = new int[2];

    private final MainMenuFrame instance = this;
    private final Container container = getContentPane();

    private final JMenuBar mb = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem logOutMenuItem = new JMenuItem(new AbstractAction("Logout") {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login.main(null);
            setVisible(false);
            dispose();
        }
    });

    private final JMenu editMenu = new JMenu("Edit");
    private  final JMenuItem avalibleOffline = new JMenuItem(new AbstractAction("Make available Offline") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(instance, "Sorry this feature isn't available yet");
        }
    });

    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuItem versionMenuItem = new JMenuItem(new AbstractAction("Version") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(instance, "Version - I mean it's not even pre-alpha yet");
        }
    });

    private final ImageIcon buttonImg = new ImageIcon("buttonIcon.jpg");
    private final JButton callenderButton = new JButton("Calender", buttonImg);
    private final JButton assigmentsButton = new JButton("Assignments", buttonImg);
    private final JLabel logoLabel = new JLabel();
    private  ImageIcon logo = new ImageIcon("Logo.jpg");

    //private JButton[] buttons = {callenderButton, assigmentsButton};
    private final HashMap<String, JButton> buttons = new HashMap<String, JButton>();

    private String currentlyPressedButton = null;

    MainMenuFrame(int width, int height) {
        Border emptyBorder = BorderFactory.createEmptyBorder();

        buttons.put(callenderButton.getText(), callenderButton);
        buttons.put(assigmentsButton.getText(), assigmentsButton);

        for (JButton button : buttons.values()) {
            button.setVerticalTextPosition(SwingConstants.CENTER);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setForeground(new Color(31, 40, 55));
            button.setBorder(emptyBorder);
            //button.setFont(new Font());
        }

        Image image = logo.getImage();
        Image resizedLogo = image.getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        logo = new ImageIcon(resizedLogo);
        screenSize[0] = width;
        screenSize[1] = height;
        logoLabel.setIcon(logo);
        setLayoutManager();
        setLocationAndSize();
        setMenuBar();
        addCompoenetsToContatiner();
        addActionEvent();
    }

    public Container getContainer() {
        return container;
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void setMenuBar() {
        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(helpMenu);
        fileMenu.add(logOutMenuItem);
        editMenu.add(avalibleOffline);
        helpMenu.add(versionMenuItem);
    }

    private void setLocationAndSize() {
        mb.setBounds(0,0, screenSize[0], 30);
        logoLabel.setBounds(0, 30, 150, 50);
        int startPixel = 80;
        for (JButton button : buttons.values()) {
            button.setBounds(0, startPixel, 150, 30);
            startPixel += 30;
        }
    }

    private void addCompoenetsToContatiner() {
        container.add(mb);
        container.add(logoLabel);
        for (JButton button : buttons.values()) {
            container.add(button);
        }
    }


    public boolean addCompoenet(JComponent component) {
        container.add(component);
        return  true;
    }

    private void addActionEvent() {
        callenderButton.addActionListener(this);
        assigmentsButton.addActionListener(this);
    }

    private void resetButton() {
        if (currentlyPressedButton != null) {
            buttons.get(currentlyPressedButton).setBorder(BorderFactory.createEmptyBorder());
        }
    }
    @Override
    public  void actionPerformed(ActionEvent e) {
        resetButton();
        if (e.getSource() == callenderButton) {
            currentlyPressedButton = callenderButton.getText();
            if (openWindow != null) {
             openWindow.closeWindow();
            }
            openWindow = new CalenderFrame(this);
        }
        if (e.getSource() == assigmentsButton) {
            currentlyPressedButton = assigmentsButton.getText();
            if (openWindow != null) {
                openWindow.closeWindow();
            }
            openWindow = new AssignmentsFrame(this);
        }
        if (currentlyPressedButton != null) {
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            buttons.get(currentlyPressedButton).setBorder(border);
        }
    }

    public int[] getScreenSize(){
        return screenSize;
    }
}
