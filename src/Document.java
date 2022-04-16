import javax.imageio.IIOException;
import java.io.IOException;

public class Document {
    private String fileName;
    private String location;
    private String fileType;
    private String assignmentName;

    public Document(String fileName, String fileType, String location, String assignmentName) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.location = location;
        this.assignmentName = assignmentName;
    }

    public void saveDocument() {
        AssignmentsFiles.addDocumentToJSON(assignmentName, fileName, fileType, location);
    }


    public boolean open() {
        try {
            Run.openFile(location);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getLocation() {
        return location;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    @Override
    public String toString() {
        return "Document{" +
                "fileName='" + fileName + '\'' +
                ", location='" + location + '\'' +
                ", fileType='" + fileType + '\'' +
                ", assignmentName='" + assignmentName + '\'' +
                '}';
    }
}
