package com.lingfeishengtian.contestwebsite.utils;

import com.lingfeishengtian.contestwebsite.login.Authenticator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId());

        if(team < 0){
            resp.getOutputStream().println(Authenticator.generateHTMLMessage("You are not logged in."));
        }
        else{
            req.getSession().invalidate();
            resp.sendRedirect("./");
        }

        resp.getOutputStream().flush();
    }
}
