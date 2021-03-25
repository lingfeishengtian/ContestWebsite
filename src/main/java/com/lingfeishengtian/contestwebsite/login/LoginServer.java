package com.lingfeishengtian.contestwebsite.login;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/login")
public class LoginServer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String relativePath = context.getRealPath("");
        PrintWriter out = resp.getWriter();

        String sessionID = req.getSession().getId();

        int team = Authenticator.getTeamFromSessionID(sessionID);
        if(team > 0) {
            out.println("Logged in as team" + team + ".");
        }else if(team == 0){
            out.println("Logged in as administrator.");
        }else {
            out.println("Not logged in! Please login to submit appeals, register, etc.");
        }

        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("team");
        String password = req.getParameter("password");
        ServletContext context = getServletContext();
        String relativePath = context.getRealPath("");

        PrintWriter out = resp.getWriter();  
        String sessionID = req.getSession().getId();

        int team = Integer.parseInt(user);
        
        if(team >= -1){
            boolean isAuth = Authenticator.authenticateUser(team, password, relativePath + "WEB-INF/data/passwords.txt", sessionID);

            if(isAuth){
                if(team > 0)
                    resp.sendRedirect("team");
                else
                    resp.sendRedirect("admin");
            }else{
                out.println(Authenticator.generateHTMLMessage("Invalid User or Password!"));
            }
        }
        
        out.println(Authenticator.generateHTMLMessage("No account found."));
        out.flush();
    }
}