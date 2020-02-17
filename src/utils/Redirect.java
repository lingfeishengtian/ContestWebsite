package utils;

import login.Authenticator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/redirect")
public class Redirect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(), getServletContext().getRealPath("") + "WEB-INF/session-tracker");

        if(team == 0){
            resp.sendRedirect("admin");
        }else if(team > 0) {
            resp.sendRedirect("team");
        }else{
            resp.getOutputStream().println(Authenticator.generateHTMLMessage("You are not logged in."));
        }

        resp.getOutputStream().flush();
    }
}
