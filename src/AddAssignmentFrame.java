import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AddAssignmentFrame extends JFrame implements ActionListener {
    private final String assignmentName;
    private final String description;


    private final JPanel container = new JPanel();

    private final JPanel panel = new JPanel();
    private final JButton acceptButton = new JButton("Accept");

    private final JPanel addDocumentPanel = new JPanel(new GridBagLayout(), false);
    private final GridBagConstraints contstraints = new GridBagConstraints();
    private final JTextField documentField = new JTextField(20);
    private final JButton addDocumentButton = new JButton("Add");
    private final HashMap<JButton, JTextField> documents = new HashMap<JButton, JTextField>();
    private final ArrayList<Document> documentArrayList = new ArrayList<Document>();
    private final HashMap<JButton, Document> removeButtons = new HashMap<JButton, Document>();


    private final AssignmentsFrame loginFame;

    public AddAssignmentFrame(AssignmentsFrame loginFrame, String name, String description) {
        this.assignmentName = name;
        this.description = description;

        loginFame = loginFrame;
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(addDocumentPanel, BorderLayout.NORTH);
        container.add(panel, BorderLayout.SOUTH);

        addDocumentPanel.setLayout(new GridBagLayout());
        contstraints.anchor = GridBagConstraints.WEST;
        contstraints.insets = new Insets(10, 10, 10, 10);

        contstraints.gridx = 0;
        contstraints.gridy = 0;
        addDocumentPanel.add(documentField, contstraints);

        contstraints.gridx = 1;
        addDocumentPanel.add(addDocumentButton, contstraints);

        setLocationAndSize();
        addComponenetsToPanel();
        initButton(addDocumentButton, documentField);
        acceptButton.addActionListener(this);
        pack();
        //setLocationRelativeTo(null);
    }

    private void addComponenetsToPanel() {
        panel.add(acceptButton);
        //addDocumentPanel.add(documentField, BorderLayout.NORTH);
        //addDocumentPanel.add(addDocumentButton, BorderLayout.SOUTH);
    }

    /*
    private void addComponentesToContainer() {
        container.add(nameLabel);
        container.add(nameField);
        container.add(acceptButton);
    }*/

    private void setLocationAndSize() {
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().add(container);
    }

    private void initButton(JButton button, JTextField textField) {
        documents.put(button, textField);
        button.addActionListener(this);
    }

    private void addDocument(File file) {
        String name = "";
        int i = file.getName().lastIndexOf('\\');
        if (i > 0) name = file.getName().substring(i+1);
        if (file.isDirectory()) {
            documentArrayList.add(new Document(name, "Folder", file.getAbsolutePath(), ""));
        } else {
            String extension = "";
            i = file.getName().lastIndexOf('.');
            if (i > 0 ) extension = file.getName().substring(i+1);
            documentArrayList.add(new Document(name, extension, file.getAbsolutePath(), ""));
        }
    }

    private void changeButton(JButton addButton) {
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(this);
        removeButton.setBounds(addButton.getBounds());
        container.remove(addButton);
        container.add(removeButton);
        container.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == acceptButton) {
            if (assignmentName.equals("")) JOptionPane.showMessageDialog(this, "Enter a name for the assignment");
            else {
                try {
                    Assignment assignment = new Assignment(assignmentName, description);
                    documentArrayList.forEach(document -> {
                        document.setAssignmentName(assignmentName);
                        document.saveDocument();
                    });
                    assignment.addDocuments(documentArrayList);
                    assignment.saveAssignment();
                    loginFame.addAssignmentButton(assignment);
                    setVisible(false);
                    dispose();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(this, "Something went wrong, Please check you have filled in all forms correctly and try again");
                }
            }
        }

        else {
            JButton button = (JButton) e.getSource();

            if (button.getText().equals("Add")) {
                JTextField pathTextFeild = documents.get(button);
                String path = pathTextFeild.getText();
                if (path.equals("")) {
                    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    fileChooser.setMultiSelectionEnabled(true);
                    int response = fileChooser.showOpenDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        File[] files = fileChooser.getSelectedFiles();
                        boolean addedAlready = false;
                        for (File file : files) {
                            addDocument(file);
                            if (!addedAlready) {
                                pathTextFeild.setText(file.getAbsolutePath());
                                addedAlready = true;
                            }
                        }
                        changeButton(button);
                    }
                } else {
                    File file = new File(path);
                    if (file.exists() || file.isDirectory()) {
                        addDocument(file);
                        changeButton(button);
                    } else {
                        pathTextFeild.setText("Invalid Document");
                    }
                }
            } else {
                documentArrayList.remove(removeButtons.get(button));
                JOptionPane.showMessageDialog(this, "Removed Document");
            }
        }
    }
}
