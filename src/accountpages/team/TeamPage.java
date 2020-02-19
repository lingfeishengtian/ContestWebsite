package accountpages.team;

import login.Authenticator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/team")
public class TeamPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String path = context.getRealPath("");

        PrintWriter out = resp.getWriter();

        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(), path + "WEB-INF/session-tracker");

        if(team > 0){
            out.println(getTeamHTML(team));
        } else if(team == 0){
            out.println(Authenticator.generateHTMLMessage("You are an administrator! Don't go to the team panel!"));
        } else {
            out.println(Authenticator.generateHTMLMessage("You have not logged in!"));
        }

        out.flush();
    }

    private String getTeamHTML(int team){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Team Panel</title>\n" +
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
                "      <li class=\"nav-item navbar-right\"> <a class=\"nav-link\" href=\"scoreboard.html\">Scoreboard <span class=\"sr-only\"></span></a> </li>\n" +
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
                "              <h1 class=\"text-center\">Welcome Team " + team + "&nbsp;</h1>\n" +
                "              <p class=\"text-center\">Have you registered yet? If you haven't, please do so below in the registration section. If you do not register, there is a chance your score won't be counted.</p>\n" +
                "              <p class=\"text-center\">This is your team's management panel. You will register, submit appeals and download sample data.&nbsp;</p>\n" +
                "\t\t\t\t<p class=\"text-center\"><a class=\"btn btn-primary btn-lg btn-rounded\" href=\"downloads/pc2-9.6.0_distribution.zip\" role=\"button\">Download PC^2</a> <a class=\"btn btn-primary btn-lg btn-rounded\" href=\"downloadContestSampleData\" role=\"button\">Download Sample Data</a> </p>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </header>\n" +
                "    <div class=\"container\">\n" +
                "      <div class=\"row\">\n" +
                "        <div class=\"col-12 col-md-8 mx-auto\">\n" +
                "          <div class=\"jumbotron\">\n" +
                "            <div class=\"row text-center\">\n" +
                "              <div class=\"text-center col-12\">\n" +
                "                <h2>Register Your Team</h2>\n" +
                "              </div>\n" +
                "              <div class=\"text-center col-12\">\n" +
                "                <!-- CONTACT FORM https://github.com/jonmbake/bootstrap3-contact-form -->\n" +
                "                <form id=\"registrationForm\" clrs=\"text-center\" action=\"register\" method=\"POST\">\n" +
                "                  <div class=\"form-group\">\n" +
                "                    <label for=\"school\">School Name</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"school\" name=\"school\" placeholder=\"Example HS\" aria-describedby=\"schoolRegister\">\n" +
                "                    <span id=\"schoolRegister\" class=\"form-text text-muted\" style=\"display: none;\">Please enter your school name.</span>\n" +
                "                  </div>\n" +
                "                  <div class=\"form-group\">\n" +
                "                    <label for=\"name1\">Teammate 1</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"name1\" name=\"name1\" placeholder=\"Teammate 1\" aria-describedby=\"name1Register\">\n" +
                "                    <span id=\"name1Help\" class=\"form-text text-muted\" style=\"display: none;\">Please enter your name.</span>\n" +
                "                  </div>\n" +
                "                  <div class=\"form-group\">\n" +
                "                    <label for=\"name2\">Teammate 2</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"name2\" name=\"name2\" placeholder=\"Teammate 2\" aria-describedby=\"name2Register\">\n" +
                "                    <span id=\"name2Help\" class=\"form-text text-muted\" style=\"display: none;\">Please enter your name.</span>\n" +
                "                  </div>\n" +
                "                  <div class=\"form-group\">\n" +
                "                    <label for=\"name3\">Teammate 3</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"name3\" name=\"name3\" placeholder=\"Teammate 3\" aria-describedby=\"name3Register\">\n" +
                "                    <span id=\"name3Help\" class=\"form-text text-muted\" style=\"display: none;\">Please enter your name.</span>\n" +
                "                  </div>\n" +
                "                  <button type=\"submit\" id=\"feedbackSubmit\" class=\"btn btn-primary btn-lg\"> Send</button>\n" +
                "                </form>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "\t  \n" +
                "<div class=\"jumbotron\">\n" +
                "  <div class=\"container\">\n" +
                "    <div class=\"row\">\n" +
                "      <div class=\"col-12\">\n" +
                "        <h1 class=\"text-center\">Appeals</h1>\n" +
                "        <p class=\"text-center\">After the competition is over, you can submit appeals here. But you only have 10 minutes after the competition before appeals are closed! You may only submit one appeal for each problem. Submitting an appeal for the same problem will override the old one.</p>\n" +
                "        <div class=\"col-12 col-md-8 mx-auto\">\n" +
                "          <div class=\"row text-center\">\n" +
                "            <div class=\"text-center col-12\">\n" +
                "              <h2>Please fill out the Appeal Form</h2>\n" +
                "            </div>\n" +
                "            <div class=\"text-center col-12\">\n" +
                "              <form id=\"feedbackForm\" class=\"text-center\" action = \"AppealForm\" method = \"POST\">\n" +
                "                <div class=\"form-group\">\n" +
                "                  <label for=\"problem\">Problem Number</label>\n" +
                "                  <input type=\"text\" class=\"form-control\" id=\"problem\" name=\"problem\" placeholder=\"Problem Number (Ex. 2)\" aria-describedby=\"emailHelp\">\n" +
                "                  <span id=\"emailHelp\" class=\"form-text text-muted\" style=\"display: none;\">Please enter a valid problem number.</span>\n" +
                "                </div>\n" +
                "                <div class=\"form-group\">\n" +
                "                  <label for=\"message\">Message</label>\n" +
                "                  <textarea rows=\"10\" cols=\"100\" class=\"form-control\" id=\"message\" name=\"message\" placeholder=\"Message\" aria-describedby=\"messageHelp\"></textarea>\n" +
                "                  <span id=\"messageHelp\" class=\"form-text text-muted\" style=\"display: none;\">Please enter a message.</span>\n" +
                "                </div>\n" +
                "                <button type=\"submit\" id=\"appealSubmit\" class=\"btn btn-primary btn-lg\"> Send</button>\n" +
                "              </form>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>\n" +
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
    }
}
