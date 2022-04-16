import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AssignmentsFiles {
    private static final String JSON_FILE_NAME = "AssignmentData.json";

    private static final String ASSINGMENT_INFO_JSON_STRING = "Assignments_Info";
    private static final String ASSIGNMENT_NAME_JSON_STRING = "assignmentName";
    private static final String ASSIGNMENT_DETAILS_JSON_STRING = "assignmentDetails";
    private static final String FILE_NAME_JSON_STRING = "filename";
    private static final String FILE_TYPE_JSON_STRING = "fileType";
    private static final String FILE_LOCATION_JSON_STRING = "location";

    //main function to test
    public static void main(String[] args)  throws FileNotFoundException {
        //addDocumentToJSON("AssignmentFiles", "Test Document", ".doc", "C:\\Users\\danie\\Desktop\\Checkstyle");
        //loadAssignmentFiles("AssignmentFiles");
        addAssignmentToJSON("AssignmentFiles", "TestAssigment");
        loadAssignments();
    }

    private static JSONObject getJsonObject() {
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(new FileReader(JSON_FILE_NAME));
        }
        catch (FileNotFoundException e ) {
            System.out.println("---- ERROR: JSON Not Found");
            System.exit(-1);
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return  jsonObject;
    }

    private static void commitChanges(JSONObject jsonObject) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(JSON_FILE_NAME);
        }
        catch (FileNotFoundException e) {
            System.out.println("---- ERROR: JSON Not Found");
            System.exit(-1);
        }
        printWriter.write(jsonObject.toJSONString());
        printWriter.flush();
        printWriter.close();
    }

    private static JSONArray getCurrentAssignmentFiles(String assignmentName) {
        JSONArray assignmentFiles = (JSONArray) getJsonObject().get(assignmentName);
        return (assignmentFiles == null) ? new JSONArray() : assignmentFiles;
    }

    public static void addDocumentToJSON(String assignmentName, String filename, String fileType, String location) {
        JSONArray assignmentFiles = getCurrentAssignmentFiles(assignmentName);
        JSONObject jsonObject = getJsonObject();

        Map<String, String> assignmentFilesMap = new LinkedHashMap<String, String>(3);
        assignmentFilesMap.put(FILE_NAME_JSON_STRING, filename);
        assignmentFilesMap.put(FILE_TYPE_JSON_STRING, fileType);
        assignmentFilesMap.put(FILE_LOCATION_JSON_STRING, location);
        assignmentFiles.add(assignmentFilesMap);

        jsonObject.put(assignmentName, assignmentFiles);

        commitChanges(jsonObject);
    }

    public static Object[] getAssignmentFiles(String assignmentName) {
        return getCurrentAssignmentFiles(assignmentName).toArray();
    }

    public static ArrayList<Document> loadAssignmentFiles(String assignmentName) {
        ArrayList<Document> documents = new ArrayList<Document>();
        for (Object fileObj : getCurrentAssignmentFiles(assignmentName).toArray()) {
            JSONObject file = (JSONObject) fileObj;
            documents.add(new Document((String) file.get(FILE_NAME_JSON_STRING), (String) file.get(FILE_TYPE_JSON_STRING),
                    (String) file.get(FILE_LOCATION_JSON_STRING), assignmentName));
        }
        return documents;
    }

    public static void addAssignmentToJSON(String assignmentName, String assignmentDetails) {
        JSONArray assignments = getCurrentAssignmentFiles(ASSINGMENT_INFO_JSON_STRING);
        JSONObject jsonObject = getJsonObject();

        Map<String, String> assignmentMap = new LinkedHashMap<String, String>(2);
        assignmentMap.put(ASSIGNMENT_NAME_JSON_STRING, assignmentName);
        assignmentMap.put(ASSIGNMENT_DETAILS_JSON_STRING, assignmentDetails);
        assignments.add(assignmentMap);

        jsonObject.put(ASSINGMENT_INFO_JSON_STRING, assignments);

        commitChanges(jsonObject);
    }

    public static Object[] getAssignments() {
        return getCurrentAssignmentFiles(ASSINGMENT_INFO_JSON_STRING).toArray();
    }

    public static ArrayList<Assignment> loadAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        for (Object fileObj : getCurrentAssignmentFiles(ASSINGMENT_INFO_JSON_STRING).toArray()) {
            JSONObject file = (JSONObject) fileObj;
            String assignmentName = (String) file.get(ASSIGNMENT_NAME_JSON_STRING);
            Assignment assignment = new Assignment(assignmentName, (String) file.get(ASSIGNMENT_DETAILS_JSON_STRING));
            assignment.addDocuments(loadAssignmentFiles(assignmentName));
            assignments.add(assignment);
            System.out.println(assignment.toString());
        }
        return assignments;
    }

    public static void removeAssignment(Assignment assignment) {
        JSONObject jsonObject = getJsonObject();
        jsonObject.remove(assignment.getAssignmentName());
        Map<String, String> assignmentMap = new LinkedHashMap<String, String>(2);
        assignmentMap.put(ASSIGNMENT_NAME_JSON_STRING, assignment.getAssignmentName());
        assignmentMap.put(ASSIGNMENT_DETAILS_JSON_STRING, assignment.getAssignmentDetails());
        new ArrayList<>(Arrays.asList(getCurrentAssignmentFiles(ASSINGMENT_INFO_JSON_STRING).toArray())).remove(assignmentMap);

        JSONArray assignments  =  getCurrentAssignmentFiles(ASSINGMENT_INFO_JSON_STRING);
        assignments.remove(assignmentMap);

        jsonObject.put(ASSINGMENT_INFO_JSON_STRING, assignments);

        commitChanges(jsonObject);
    }
}
