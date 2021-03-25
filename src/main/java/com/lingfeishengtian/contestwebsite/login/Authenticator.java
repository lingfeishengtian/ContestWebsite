package com.lingfeishengtian.contestwebsite.login;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Scanner;

public class Authenticator {
    private static String adminPass;

    public static void setAdminPasscode(String pass){
        if(adminPass == null) adminPass = pass;
        else System.out.println("Unauthorised admin passcode change detected.");
    }

    public static boolean doesUserHaveElevatedPermissions(HttpServletRequest req, ServletContext context) throws FileNotFoundException {
        if(getTeamFromSessionID(req.getSession().getId(), context.getRealPath("") + "WEB-INF/session-tracker") == 0){
            return true;
        }
        return false;
    }

    public static boolean authenticateUser(int team, String pass, String passwordsPath) throws IOException {
        if(team == 0 && pass.equals(adminPass)) return true;
        FileReader readfile = new FileReader(passwordsPath);
        BufferedReader readbuffer = new BufferedReader(readfile);

        for (int i = 0; i < team - 1; i++) {
            if(readbuffer.readLine() == null) return false;
        }

        return team != 0 && pass.equals(readbuffer.readLine());
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
