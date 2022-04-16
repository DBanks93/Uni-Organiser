import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Run {
    private final static String CHECK_CREDENTIALS_FUN = "checkCredentials('%s', '%s')";
    private final static String ADD_USER_FUN = "addUser('%s', '%s')";
    private final static String VIEW_USER_DETAILS_TABLE = "viewTable()";

    private static boolean isWindows = false;
    private static boolean isLinux = false;
    private static boolean isMac = false;

    static
    {
        String os = System.getProperty("os.name").toLowerCase();
        isWindows = os.contains("win");
        isLinux = os.contains("nux") || os.contains("nix");
        isMac = os.contains("mac");
    }

    public static boolean isWindows() { return isWindows; }
    public static boolean isLinux() { return isLinux; }
    public static boolean isMac() { return isMac; };


    public static void viewTable() {
        System.out.println(run(VIEW_USER_DETAILS_TABLE));
    }

    public static boolean checkCredentials(String username, String password) {
        return run(String.format(CHECK_CREDENTIALS_FUN, username, password)).equals("True");
    }

    public static boolean addUser(String username, String password) {
        return run(String.format(ADD_USER_FUN, username, password)).equals("True");
    }

    private static String run(String param) {
        String result = null;
        String errorsString = null;
        try {
            Process p = Runtime.getRuntime().exec("python -c \"from UserDetailsScript import *; " + param + "\"");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader errors = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while((errorsString = errors.readLine()) != null) {
                System.out.println(errorsString);
            }
            result = in.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return result;
    }

    public static void openFile(String path) throws IOException {
        if (isWindows()) {
            Runtime.getRuntime().exec(new String[]
                    {"rundll32", "url.dll,FileProtocolHandler",
                            path});
        } else if (isLinux() || isMac()) {
            Runtime.getRuntime().exec(new String[] {"/usr/bin/open", path});
        }
    }
}
