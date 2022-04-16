import javax.print.Doc;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AssignmentPageFrame extends Frame implements ActionListener {
    private final Assignment assignment;
    private final HashMap<JButton, Document> documents = new HashMap<JButton, Document>();

    private int lastPlacedAssingment = 80;

    public AssignmentPageFrame(MainMenuFrame mainMenuFrame, Assignment assignment) {
        super(mainMenuFrame, "Assignments");
        this.assignment = assignment;
        displayFiles();
    }

    private void displayFiles() {
        JButton button;
        for (Document document : assignment.getDocuments()) {
            button = new JButton(document.getAssignmentName());
            documents.put(button, document);
            button.addActionListener(this);
            button.setBounds(300, lastPlacedAssingment, 150, 30);
            addComponent(button);
            lastPlacedAssingment += 30;
        }
    }

    public void removeAssignment() {
        System.out.println("Removing Assignment");
        AssignmentsFiles.removeAssignment(assignment);
    }

    public Assignment getAssignment() {
        return assignment;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Document document = documents.get(e.getSource());
        document.open();
    }

}
