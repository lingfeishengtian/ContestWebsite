package com.lingfeishengtian.contestwebsite.accountpages.team;

import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.data_management.DatabaseUtils;
import com.lingfeishengtian.contestwebsite.utils.data_management.GenerateAppealRow;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/team")
public class TeamPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String path = context.getRealPath("");

        PrintWriter out = resp.getWriter();

        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(), path + "WEB-INF/session-tracker");

        if(team > 0){
            req.setAttribute("teamNum", team);
            try {
                req.setAttribute("appealRowElements", GenerateAppealRow.getAppealRowForTeam(DatabaseUtils.GetAppealsForTeam(team, path)));
                req.getRequestDispatcher("WEB-INF/protected-pages/TeamProtected.jsp").forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("An SQL error occurred, contact the admin and let him know to check logs!"));
            } catch (Exception e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("An unexpected error occurred, contact the admin immediately!"));
            }
        } else if(team == 0){
            out.println(Authenticator.generateHTMLMessage("You are an administrator! Don't go to the team panel!"));
        } else {
            out.println(Authenticator.generateHTMLMessage("You have not logged in!"));
        }

        out.flush();
    }
}
