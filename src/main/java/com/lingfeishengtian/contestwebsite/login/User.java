package com.lingfeishengtian.contestwebsite.login;

import java.util.Queue;
import java.util.LinkedList;

public class User{
    public static final int MAX_USERS = 5;
    private Queue sessionID = new LinkedList<String>();
    private int team;

    public User(int t){ 
        team = t;
    }

    public void addSession(String session){
        if(!containsSession(session)){
            sessionID.add(session);
        }
        if(sessionID.size() > MAX_USERS){
            sessionID.remove();
        }
    }

    public boolean containsSession(String session){
        return sessionID.contains(session);
    }

    public int getTeam(){
        return team;
    }

    public String toString(){
        return "team " + getTeam() + "\n" + sessionID.toString();
    }
}