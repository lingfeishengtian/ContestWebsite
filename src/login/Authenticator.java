package login;

import java.io.*;
import java.util.Scanner;

public class Authenticator {
    public static boolean authenticateUser(int team, String pass, String passwordsPath) throws IOException {
        if(team == 0 && pass.equals("CompsciIsAwesomeCoach")) return true;
        FileReader readfile = new FileReader(passwordsPath);
        BufferedReader readbuffer = new BufferedReader(readfile);

        for (int i = 0; i < team - 1; i++) {
            if(readbuffer.readLine() == null) return false;
        }

        return pass.equals(readbuffer.readLine());
    }

    public static int getTeamFromSessionID(String session, String sessionPath) throws FileNotFoundException {
        File file = new File(sessionPath);

        if(file.exists() && file.isDirectory()){
            Scanner scan;
            for (File text :
                    file.listFiles()) {
                if(!text.getName().startsWith(".")) {
                    scan = new Scanner(text);
                    int team = Integer.parseInt(text.getName().replace("team", ""));
                    String sessionID = scan.nextLine().split(":")[1];
                    if (sessionID.equals(session)) {
                        return team;
                    }
                }
            }
        }

        return -1;
    }

    public static String generateHTMLMessage(String message){
        return "<html><body><script>alert(\"" + message + "\"); window.history.back();</script></body></html>";
    }
}
