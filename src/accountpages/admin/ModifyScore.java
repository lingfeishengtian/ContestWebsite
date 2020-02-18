package accountpages.admin;

import login.Authenticator;
import utils.data_management.DatabaseUtils;
import utils.types.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/editscore")
public class ModifyScore extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(),getServletContext().getRealPath("") + "WEB-INF/session-tracker");
        PrintWriter out = resp.getWriter();

        if(team == 0){
            try{
                Connection connection = DatabaseUtils.getConnectionAndAutoCheck(getServletContext().getRealPath(""));
                Team[] teams = DatabaseUtils.getRegisteredTeams(connection);

                for (Team teamObj :
                        teams) {
                    String teamNum = req.getParameter("team" + teamObj.team);
                    if(teamNum != null) {
                        DatabaseUtils.updateTeamValues(connection,
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
        }else if(team > 0){
            out.println(Authenticator.generateHTMLMessage("Hey, teams can't access the modification post!"));
        }else{
            out.println(Authenticator.generateHTMLMessage("You aren't even logged in!"));
        }

        out.flush();
    }
}
