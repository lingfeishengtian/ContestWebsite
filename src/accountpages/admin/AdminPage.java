package accountpages.admin;

import login.Authenticator;
import utils.data_management.DatabaseUtils;
import utils.data_management.GenerateAppealRow;
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

@WebServlet("/admin")
public class AdminPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(),getServletContext().getRealPath("") + "WEB-INF/session-tracker");
        PrintWriter out = resp.getWriter();

        if(team == 0){
            try{
                req.setAttribute("formData", generateEditTemplate(DatabaseUtils.getRegisteredTeams()));
                req.setAttribute("appealRowElements", GenerateAppealRow.getAppealRowForAdmin(DatabaseUtils.GetAppeals(getServletContext().getRealPath(""))));
                req.getRequestDispatcher("/WEB-INF/protected-pages/AdminProtected.jsp").forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("Database error occurred. CONTACT STAFF!"));
            } catch (Exception e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("Database can't be found error occurred. CONTACT STAFF!"));
            }
        }else if(team > 0){
            out.println(Authenticator.generateHTMLMessage("Hey, teams can't access the admin panel!"));
        }else{
            out.println(Authenticator.generateHTMLMessage("You aren't even logged in!"));
        }

        out.flush();
    }

    private String generateEditTemplate(Team[] teams){
        String html = "";
        for(Team teamObj : teams) {
            int team = teamObj.team;
            html +=  "                <div class=\"form-group\">\n" +
                    "                  <input readonly type=\"number\" class=\"team-form form-control-multi\" id=\"team" + team + "\" name=\"team" + team + "\" placeholder=\"Team X\" aria-describedby=\"team\" value=\"" + team + "\">\n" +
                    "                  <input type=\"text\" class=\"name-form form-control-multi\" id=\"school" + team + "\" name=\"school" + team + "\" placeholder=\"School\" aria-describedby=\"school\" value=\"" + teamObj.school + "\">\n" +
                    "                  <input type=\"text\" class=\"name-form form-control-multi\" id=\"" + team + "teammate1\" name=\"" + team + "teammate1\" placeholder=\"Teammate 1\" aria-describedby=\"teammate\" value=\"" + teamObj.teammate1 + "\">\n" +
                    "                  <input type=\"number\" class=\"team-form form-control-multi\" id=\"" + team + "score1\" name=\"" + team + "score1\" placeholder=\"Score 1\" aria-describedby=\"team\" value=\"" + teamObj.teammate1score + "\">\n" +
                    "                  <input type=\"text\" class=\"name-form form-control-multi\" id=\"" + team + "teammate2\" name=\"" + team + "teammate2\" placeholder=\"Teammate 2\" aria-describedby=\"teammate\" value=\"" + teamObj.teammate2 + "\">\n" +
                    "                  <input type=\"number\" class=\"team-form form-control-multi\" id=\"" + team + "score2\" name=\"" + team + "score2\" placeholder=\"Score 2\" aria-describedby=\"team\" value=\"" + teamObj.teammate2score + "\">\n" +
                    "                  <input type=\"text\" class=\"name-form form-control-multi\" id=\"" + team + "teammate3\" name=\"" + team + "teammate3\" placeholder=\"Teammate 3\" aria-describedby=\"teammate\" value=\"" + teamObj.teammate3 + "\">\n" +
                    "                  <input type=\"number\" class=\"team-form form-control-multi\" id=\"" + team + "score3\" name=\"" + team + "score3\" placeholder=\"Score 3\" aria-describedby=\"team\" value=\"" + teamObj.teammate3score + "\">\n" +
                    "                </div>\n";
        }
        return html;
    }
}
