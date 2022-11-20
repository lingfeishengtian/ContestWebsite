package com.lingfeishengtian.contestwebsite.accountpages.team;

import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.data_management.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String school = cleanString(req.getParameter("school"));
        String name1 = cleanString(req.getParameter("name1"));
        String name2 = cleanString(req.getParameter("name2"));
        String name3 = cleanString(req.getParameter("name3"));
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId());
        PrintWriter out = resp.getWriter();

        if(team > 0 && school != null && school.length() >= 3){
            try {
                if(DatabaseUtils.hasTeamRegistered(team)){
                    out.println(Authenticator.generateHTMLMessage("Your team has already registered and we cannot register you again."));
                }else{
                    DatabaseUtils.teamRegistration(team, school, name1, name2, name3);
                    out.println(Authenticator.generateHTMLMessage("You have registered!"));
                }
                resp.sendRedirect("redirect");
            } catch (SQLException e) {
                out.println(Authenticator.generateHTMLMessage("An internal error occurred, please contact staff!"));
                e.printStackTrace();
            }
        }else if(team == 0){
            out.println(Authenticator.generateHTMLMessage("Administrator can't register! Hehe!"));
        }else if(team < 0){
            out.println(Authenticator.generateHTMLMessage("Please login!"));
        }else{
            out.println(Authenticator.generateHTMLMessage("Invalid School"));
        }
        out.flush();
    }

    private static String cleanString(String s){
        s = s.replaceAll("&", "&amp;");
        s = s.replaceAll("<", "&lt;");
        s = s.replaceAll(">", "&gt;");
        s = s.replaceAll("\"", "&quot;");
        s = s.replaceAll("'", "&apos;");
        return s;
    }
}
