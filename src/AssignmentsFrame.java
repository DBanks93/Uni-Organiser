import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AssignmentsFrame extends Frame implements ActionListener {

    private HashMap<JButton, Assignment> assignmentsHash = new HashMap<JButton, Assignment>();

    private JButton addAssignmentButton = new JButton("Add");
    private JButton removeAssignmentButton = new JButton("Remove");

    private AssignmentPageFrame openAssignmentPage = null;

    private int lastButtonPos = 80;

    public AssignmentsFrame(MainMenuFrame mainMenuFrame) {
        super(mainMenuFrame, "Assignments");
        addAssignmentButton.setOpaque(false);
        addAssignmentButton.setContentAreaFilled(false);
        removeAssignmentButton.setOpaque(false);
        removeAssignmentButton.setContentAreaFilled(false);
        loadAssignments();
    }

    private void loadAssignments() {
        addAssignmentsToFrame(new ArrayList<Assignment>(AssignmentsFiles.loadAssignments()));
    }

    private void addAssignmentsToFrame(ArrayList<Assignment> assignments) {
        JButton assignmentButton;
        int currentPosY = 80;

        for (Assignment assignment : assignments) {
            assignmentButton = new JButton(assignment.getAssignmentName());
            assignmentButton.setOpaque(false);
            assignmentButton.setContentAreaFilled(false);
            assignmentButton.setBounds(200, currentPosY, 150, 30);
            currentPosY += 30;
            assignmentButton.addActionListener(this);
            addComponent(assignmentButton);
            assignmentsHash.put(assignmentButton, assignment);
            lastButtonPos = currentPosY;
        }

        addAssignmentButton.setBounds(200, currentPosY + 70, 150, 30);
        addAssignmentButton.addActionListener(this);
        removeAssignmentButton.setBounds(200, currentPosY+ 130, 150, 30);
        removeAssignmentButton.addActionListener(this);
        addComponents(new JButton[]{addAssignmentButton, removeAssignmentButton});
    }

    public void addAssignmentButton(Assignment assignment) {
        JButton assignmentButton = new JButton(assignment.getAssignmentName());
        assignmentButton.setOpaque(false);
        assignmentButton.setContentAreaFilled(false);
        assignmentButton.setBounds(200, lastButtonPos, 150, 30);
        lastButtonPos += 30;
        assignmentButton.addActionListener(this);
        addComponent(assignmentButton);
        assignmentsHash.put(assignmentButton, assignment);

        addAssignmentButton.setBounds(200, lastButtonPos + 70, 150, 30);
        removeAssignmentButton.setBounds(200, lastButtonPos+ 130, 150, 30);
        refreshPage();
    }

    private void addAssignment() {
        String name = JOptionPane.showInputDialog("Enter Assignment Name");
        String description = JOptionPane.showInputDialog("Enter Assignment Description");
        AddAssignmentFrame addAssignmentFrame = new AddAssignmentFrame(this, name, description);
        addAssignmentFrame.setTitle("Create Assignment: " + name);
        addAssignmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addAssignmentFrame.setSize(400, 400);
        addAssignmentFrame.setVisible(true);
    }

    private boolean removeAssignment(Assignment assignment) {
        JButton buttonRemove = null;
        //Set<JButton> jButtons = new HashSet<JButton>();
        for (Map.Entry<JButton, Assignment> entry : assignmentsHash.entrySet()) {
            if(Objects.equals(assignment, entry.getValue())) {
                buttonRemove = entry.getKey();
            }
        }
        if (buttonRemove != null) {
            openAssignmentPage.removeAssignment();
            assignmentsHash.remove(buttonRemove);
            removeComponent(buttonRemove);
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addAssignmentButton) {
            addAssignment();
            //loadAssignments();
            refreshPage();
        } else if ( e.getSource() == removeAssignmentButton) {
            if (openAssignmentPage != null) {
                Assignment assignment = openAssignmentPage.getAssignment();
                if (JOptionPane.showConfirmDialog(null,
                        String.format("Are you sure you wish to remove the assignment %s?",
                                assignment.getAssignmentName()), "WARNING",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (!removeAssignment(assignment))
                        JOptionPane.showMessageDialog(getMainMenuFrame(), "Please Select an Assignment to remove");
                }
            }
            else {
                JOptionPane.showMessageDialog(getMainMenuFrame(), "Please Select an Assignment to remove");
            }
        } else {
            if (openAssignmentPage != null) {openAssignmentPage.closeWindow();}
            Assignment openAssignment = assignmentsHash.get(e.getSource());
            openAssignment.openPage(super.getMainMenuFrame());
            openAssignmentPage = openAssignment.getAssignmentPageFrame();
        }
        refreshPage();
    }
}
