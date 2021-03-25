package com.lingfeishengtian.contestwebsite.login;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Authenticator {
    private static String adminPass;
    private static ArrayList<User> users = new ArrayList<User>();

    public static void setAdminPasscode(String pass){
        if(adminPass == null) adminPass = pass;
        else System.out.println("Unauthorised admin passcode change detected.");
    }

    public static boolean doesUserHaveElevatedPermissions(HttpServletRequest req, ServletContext context) throws FileNotFoundException {
        if(getTeamFromSessionID(req.getSession().getId()) == 0){
            return true;
        }
        return false;
    }

    public static boolean authenticateUser(int team, String pass, String passwordsPath, String session) throws IOException {
        if(team == 0 && pass.equals(adminPass)) return true;
        FileReader readfile = new FileReader(passwordsPath);
        BufferedReader readbuffer = new BufferedReader(readfile);

        for (int i = 0; i < team - 1; i++) {
            if(readbuffer.readLine() == null) return false;
        }

        if (team != 0 && pass.equals(readbuffer.readLine())){
            boolean found = false;
            for (User user : users) {
                if(user.getTeam() == team){
                    user.addSession(session);
                    found = true;
                }
            }

            if(!found){
                User newU = new User(team);
                newU.addSession(session);
                users.add(newU);
            }

            return true;
        }

        return false;
    }

    public static int getTeamFromSessionID(String session) {
        for (User user : users) {
            System.out.println(user.toString());
        }
         for (User u : users) {
             if(u.containsSession(session)){
                 return u.getTeam();
             }
         }
         return -1;
    }

    public static String generateHTMLMessage(String message){
        return "<html><body><script>alert(\"" + message + "\"); window.history.back();</script></body></html>";
    }
}
