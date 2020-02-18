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

@WebServlet("/admin")
public class AdminPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(),getServletContext().getRealPath("") + "WEB-INF/session-tracker");
        PrintWriter out = resp.getWriter();

        if(team == 0){
            try{
                Connection connection = DatabaseUtils.getConnectionAndAutoCheck(getServletContext().getRealPath(""));
                out.println(generateHTML(DatabaseUtils.getRegisteredTeams(connection)));
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

    private String generateHTML(Team[] teams){
        String front = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Admin Panel</title>\n" +
                "    <!-- Bootstrap -->\n" +
                "    <link href=\"css/bootstrap-4.3.1.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"css/style.css\" rel=\"stylesheet\">\n" +
                "  </head>\n" +
                "  <body>\n" +
                "\t<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
                "  <img class=\"navbar-brand image\" src=\"images/travis_logo.png\" alt=\"Travis Logo\">\n" +
                "  <a class=\"navbar-brand navbar-center\" href=\"#\">UIL Competition</a>\n" +
                "  <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\"></div>\n" +
                "  <div class=\"form-inline my-2 my-lg-0\">\n" +
                "    <ul class=\"navbar-nav mr-auto\">\n" +
                "      <li class=\"nav-item navbar-right\"> <a class=\"nav-link\" href=\"index.html\">Home <span class=\"sr-only\"></span></a> </li>\n" +
                "    </ul>\n" +
                "    <ul class=\"navbar-nav mr-auto\">\n" +
                "      <li class=\"nav-item navbar-right\"> <a class=\"nav-link\" href=\"scoreboard.shtml\">Scoreboard <span class=\"sr-only\"></span></a> </li>\n" +
                "    </ul>\n" +
                "    <ul class=\"navbar-nav mr-auto\">\n" +
                "      <li class=\"nav-item navbar-right\"> <a class=\"nav-link\" href=\"login.html\">Login <span class=\"sr-only\"></span></a> </li>\n" +
                "    </ul>\n" +
                "    <ul class=\"navbar-nav mr-auto\">\n" +
                "      <li class=\"nav-item active navbar-right\"> <a class=\"nav-link\" href=\"redirect\">My Team <span class=\"sr-only\">(current)</span></a> </li>\n" +
                "    </ul>\n" +
                "  </div>\n" +
                "</nav>\n" +
                "    <header>\n" +
                "      <div class=\"jumbotron\">\n" +
                "        <div class=\"container\">\n" +
                "          <div class=\"row\">\n" +
                "            <div class=\"col-12\">\n" +
                "              <h1 class=\"text-center\">Welcome Administrator&nbsp;</h1>\n" +
                "              <p class=\"text-center\">This is where you input written scores!</p>\n" +
                "              <p class=\"text-center\">Once you enter individual written scores, all scores including programming ones will be automatically added together and placed. A custom scoreboard would be generated. For you, a file with all data will be generated!</p>\n" +
                "\t\t\t\t<p class=\"text-center\"><a class=\"btn btn-primary btn-lg btn-rounded\" href=\"generateReport\" role=\"button\">Download Score Report</a> <a class=\"btn btn-primary btn-lg btn-rounded\" href=\"authorized-download?id=1\" role=\"button\">Download PC^2 Judge</a></p>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </header>\n" +
                "  <div class=\"container\">\n" +
                "    <div class=\"row\">\n" +
                "      <div class=\"col-12\">\n" +
                "        <h1 class=\"text-center\">Input Scores in Database&nbsp;</h1>\n" +
                "        <p class=\"text-center\">Hey admins! Here, registered teams will popup with details on team number, school, and students. What you can do is input written scores and generate a data report!&nbsp;</p>\n" +
                "          <div class=\"row text-center\">\n" +
                "            <div class=\"text-center col-12\">\n" +
                "              <form id=\"editForm\" class=\"text-center\" action = \"editscore\" method = \"POST\">\n" +
                "                 <label for=\"message\">How do I use this? Enter missing values or modify existing values to edit the database. You will then be able to generate a scoreboard.</label>\n";

        String back = "                <button type=\"submit\" id=\"appealSubmit\" class=\"btn btn-primary btn-lg\"> Save Changes</button>\n" +
                "              </form>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "  </div>\n" +
                "</div>\n" +
                "<div></div>\n" +
                "    <footer class=\"text-center\">\n" +
                "      <div class=\"container\">\n" +
                "        <div class=\"row\">\n" +
                "          <div class=\"col-12\">\n" +
                "            <p>Made by Hunter Han</p>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </footer>\n" +
                "    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> \n" +
                "    <script src=\"js/jquery-3.3.1.min.js\"></script> \n" +
                "    <!-- Include all compiled plugins (below), or include individual files as needed --> \n" +
                "    <script src=\"js/popper.min.js\"></script> \n" +
                "    <script src=\"js/bootstrap-4.3.1.js\"></script>\n" +
                "  </body>\n" +
                "</html>";
        String formStuff = generateEditTemplate(teams);

        return front + formStuff + back;
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
