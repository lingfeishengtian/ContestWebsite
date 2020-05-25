package utils.data_management;

import login.Authenticator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.types.Team;
import utils.types.Teammate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Generates scoreboard data. This is easier if I use the old method of manually generating HTML.
 *
 * There are just too many variables involved in the generation and it'd be really annoying to generate it from a jsp file.
 */
public class GenerateFinalData {
    public static String generateIndividualReport(String pc2root, boolean writtenOnly, boolean isPublic) throws SQLException, IOException {
        if(!updateProgrammingScoreDataInDatabase(pc2root)){
            return Authenticator.generateHTMLMessage("Contest programming file doesn't exist.");
        }
        Team[] teams = DatabaseUtils.getRegisteredTeams();

        Arrays.sort(teams, (o1, o2) -> o2.programmingScore - o1.programmingScore);

        return generateScoreboard(teams, isPublic, writtenOnly, !writtenOnly, false);
    }

    public static String generateFinalReport(String pc2root, boolean isPub, boolean includeNavBar) throws IOException, SQLException {
        if(!updateProgrammingScoreDataInDatabase(pc2root)){
            return Authenticator.generateHTMLMessage("Contest programming file doesn't exist.");
        }

        Team[] teams = DatabaseUtils.getRegisteredTeams();

        Arrays.sort(teams, (o1, o2) -> o2.getFinalScore() - o1.getFinalScore());

        return generateScoreboard(teams, isPub, false, false, includeNavBar);
    }

    private static String generateScoreboard(Team[] teams, boolean isPublic, boolean writtenOnly, boolean programmingOnly, boolean shouldIncludeNavBar){
        String htmlF = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Generated Scoreboard</title>\n" +
                "    <link href=\"css/bootstrap-4.3.1.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"css/style.css\" rel=\"stylesheet\">\n" +
                "  </head>\n" +
                "  <body>\n" +
                "\n";

        if(shouldIncludeNavBar){
            htmlF += "    <nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
                            "\t<img class=\"navbar-brand image\" src=\"images/travis_logo.png\" alt=\"Travis Logo\">\n" +
                            "      <a class=\"navbar-brand navbar-center\" href=\"#\">UIL Competition</a>\n" +
                            "      <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\"> <span class=\"navbar-toggler-icon\"></span> </button>\n" +
                            "      <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\"></div>\n" +
                            "        <div class=\"form-inline my-2 my-lg-0\">\n" +
                            "        <ul class=\"navbar-nav mr-auto\">\n" +
                            "          <li class=\"nav-item navbar-right\"> <a class=\"nav-link\" href=\"index.html\">Home <span class=\"sr-only\"></span></a> </li>\n" +
                            "        </ul>\n" +
                            "\t\t <ul class=\"navbar-nav mr-auto\">\n" +
                            "          <li class=\"nav-item active navbar-right\"> <a class=\"nav-link\" href=\"scoreboard.html\">Scoreboard <span class=\"sr-only\">(current)</span></a> </li>\n" +
                            "        </ul>\n" +
                            "          <ul class=\"navbar-nav mr-auto\">\n" +
                            "            <li class=\"nav-item navbar-right\"> <a class=\"nav-link\" href=\"login.html\">Login <span class=\"sr-only\"></span></a> </li>\n" +
                            "          </ul>\n" +
                            "          <ul class=\"navbar-nav mr-auto\">\n" +
                            "            <li class=\"nav-item navbar-right\"> <a class=\"nav-link\" href=\"redirect\">My Team <span class=\"sr-only\">(current)</span></a> </li>\n" +
                            "          </ul>\n" +
                            "        </div>\n" +
                            "    </nav>";
        }

        htmlF += "\t  <div class=\"blank-space-baby\"></div>\n" +
                "  <div class=\"container\">\n" +
                "    <div class=\"row\">\n" +
                "      <div class=\"col-12\">\n" +
                "        <h1 class=\"text-center\">" + (isPublic ? "Public " : "Final ") + (writtenOnly ? "Written Test " : "") + (programmingOnly ? "Hands-On " : "") + (!writtenOnly && !programmingOnly ? "Total " : "") + "Scoreboard" + "</h1>\n" +
                "        <p class=\"text-center\">Hey there! Here's the final scoreboard with detailed information." + (isPublic ? "" : " This is a more detailed version of the public scoreboard for coaches. If you are not a coach or staff, then please contact staff immediately.") + "</p>\n" +
                "          <div class=\"row text-center\">\n" +
                "            <div class=\"text-center col-12 navbar-center\">\n" +
                "\t\t\t\t<table width=\"200\" border=\"1\" class=\"navbar-center\">\n" +
                "  <tbody>\n";

        if (writtenOnly) {
            ArrayList<Teammate> teammates = new ArrayList<>();
            for (Team team : teams) {
                Collections.addAll(teammates, team.getAllTeammates());
            }

            teammates.sort((o1, o2) -> {
                if(o2.written == o1.written){
                    return o2.associatedTeam.programmingScore - o1.associatedTeam.programmingScore;
                }else{
                    return o2.written - o1.written;
                }
            });

            htmlF += "    <tr>\n" +
                            "      <th class=\"shorter-cell\" scope=\"col\">Place</th>\n" +
                            "      <th class=\"shorter-cell\" scope=\"col\">Team</th>\n" +
                            "      <th scope=\"col\">School</th>\n" +
                            (!isPublic ? "      <th scope=\"col\">Team Member</th>\n" : "") +
                            "      <th class=\"shorter-cell\" scope=\"col\">Written Score</th>\n" +
                            "    </tr>\n";

            for (int i = 0; i < teammates.size(); i++) {
                Teammate teammate = teammates.get(i);
                htmlF += "    <tr>\n" +
                        "      <td>" + (i + 1) + "</td>\n" +
                        "      <td>" + teammate.associatedTeam.team + "</td>\n" +
                        "      <td>" + teammate.associatedTeam.school + "</td>\n" +
                        (!isPublic ? "      <td>" + teammate.name + "</td>\n" : "") +
                        "      <td>" + teammate.written + "</td>\n" +
                        "    </tr>\n";
            }

        }else{
            htmlF += "    <tr>\n" +
                            "      <th class=\"shorter-cell\" scope=\"col\">Place</th>\n" +
                            "      <th class=\"shorter-cell\" scope=\"col\">Team</th>\n" +
                            "      <th scope=\"col\">School</th>\n" +
                            (!isPublic ? "      <th scope=\"col\">Team Members</th>\n" : "");
            if(!programmingOnly){
                htmlF += "      <th class=\"shorter-cell\" scope=\"col\">Written Score</th>\n" +
                                "      <th class=\"shorter-cell\" scope=\"col\">Hands On Score</th>\n" +
                                "      <th class=\"shorter-cell\" scope=\"col\">Total Score</th>\n";
            }else{
                htmlF += "      <th class=\"shorter-cell\" scope=\"col\">Hands On Score</th>\n";
            }
            htmlF += "    </tr>\n";

            for (int i = 0; i < teams.length; i++) {
                Team team = teams[i];
                htmlF += "    <tr>\n" +
                        "      <td>" + (i + 1) + "</td>\n" +
                        "      <td>" + team.team + "</td>\n" +
                        "      <td>" + team.school + "</td>\n" +
                        (!isPublic ? "      <td>" + team.teammate1 + "<br>" + team.teammate2 + "<br>" + team.teammate3 + "</td>\n" : "") +
                        (!programmingOnly ? ("      <td>" + (isPublic ? (team.teammate1score + team.teammate2score + team.teammate3score) : team.teammate1score + "<br>" + team.teammate2score + "<br>" + team.teammate3score) + "</td>\n") : "") +
                        "      <td>" + team.programmingScore + "</td>\n" +
                        (!programmingOnly ? ("      <td>" + team.getFinalScore() + "</td>\n") : "") +
                        "    </tr>\n";
            }

        }
        String htmlB = "  </tbody>\n" +
                "</table>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "  </div>\n" +
                "</div>\n" +
                "\t <div class=\"blank-space-baby\"></div>\n" +
                "    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> \n" +
                "    <script src=\"js/jquery-3.3.1.min.js\"></script> \n" +
                "    <!-- Include all compiled plugins (below), or include individual files as needed --> \n" +
                "    <script src=\"js/popper.min.js\"></script> \n" +
                "    <script src=\"js/bootstrap-4.3.1.js\"></script>\n" +
                "  </body>\n" +
                "</html>";
        return htmlF + htmlB;
    }

    private static boolean updateProgrammingScoreDataInDatabase(String pc2Root) throws IOException {
        File file = new File(pc2Root + File.separator + "bin" + File.separator + "html" + File.separator + "index.html");

        if(!file.exists()){
            return false;
        }else{
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements tr = doc.select("tr");

            for (int i = 2; i < tr.size() - 1; i++) {
                Elements td = tr.get(i).getAllElements();
                int team = Integer.parseInt(td.get(2).text().trim().substring(4));
                int programmingScore = Integer.parseInt(td.get(4).text().trim());

                try {
                    DatabaseUtils.updateTeamProgrammingScore(team, programmingScore);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }
}
