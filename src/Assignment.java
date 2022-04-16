import java.util.ArrayList;

public class Assignment {
    private String assignmentName;
    private String assignmentDetails;
    private ArrayList<Document> documents = new ArrayList<Document>();
    private AssignmentPageFrame assignmentPageFrame;

    public Assignment(String assignmentName, String assignmentDetails) {
        this.assignmentName = assignmentName;
        this.assignmentDetails = assignmentDetails;
    }

    public void addDocuments(ArrayList<Document> documents) {
        this.documents.addAll(documents);
    }

    public void saveAssignment() {
        AssignmentsFiles.addAssignmentToJSON(assignmentName, assignmentDetails);
    }

    public void openPage(MainMenuFrame mainMenuFrame) {
        assignmentPageFrame = new AssignmentPageFrame(mainMenuFrame, this);
    }

    public void closePage() {
        assignmentPageFrame = null;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public void setAssignmentDetails(String assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public AssignmentPageFrame getAssignmentPageFrame() {
        return assignmentPageFrame;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentName='" + assignmentName + '\'' +
                ", assignmentDetails='" + assignmentDetails + '\'' +
                ", documents=" + documents +
                '}';
    }
}
