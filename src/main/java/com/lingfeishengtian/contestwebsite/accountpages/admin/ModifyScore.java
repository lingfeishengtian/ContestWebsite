package com.lingfeishengtian.contestwebsite.accountpages.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.data_management.DatabaseUtils;
import com.lingfeishengtian.contestwebsite.utils.types.Team;

@WebServlet("/editscore")
public class ModifyScore extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        if(Authenticator.doesUserHaveElevatedPermissions(req, getServletContext())){
            try{
                Team[] teams = DatabaseUtils.getRegisteredTeams();

                for (Team teamObj :
                        teams) {
                    String teamNum = req.getParameter("team" + teamObj.team);
                    if(teamNum != null) {
                        DatabaseUtils.updateTeamValues(
                                Integer.parseInt(teamNum),
                                req.getParameter("school" + teamNum),
                                req.getParameter(teamNum + "teammate1"),
                                Integer.parseInt(req.getParameter(teamNum + "score1")),
                                req.getParameter(teamNum + "teammate2"),
                                Integer.parseInt(req.getParameter(teamNum + "score2")),
                                req.getParameter(teamNum + "teammate3"),
                                Integer.parseInt(req.getParameter(teamNum + "score3")));
                    }
                }

                out.println(Authenticator.generateHTMLMessage("Success in modifying values!"));
            } catch (SQLException e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("Database error occurred. CONTACT STAFF!"));
            } catch (Exception e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("Database can't be found error occurred. CONTACT STAFF!"));
            }
        }else{
            out.println(Authenticator.generateHTMLMessage("You aren't even logged in or no permission!"));
        }

        out.flush();
    }
}
