package utils.data_management;

import utils.types.Appeal;

import java.util.ArrayList;
import java.util.Comparator;

public class GenerateAppealRow {
    public static String getAppealRowForTeam(ArrayList<Appeal> appeals){
        String html = "";
        appeals.sort(Comparator.comparingInt(Appeal::getProblemNumber));

        for(Appeal a : appeals){
            html += "\n" +
                    "\t\t\t\t\t<div class=\"gray-back-div\">\n" +
                    "\t\t\t\t\t\t<h1>Problem " + a.getProblemNumber() + "</h1>\n" +
                    "\t\t\t\t\t\t<b>Status: </b>" + a.getStatus() + "\n" +
                    "\t\t\t\t\t\t<br><b>Message: </b>" + a.getMessage() + "\n" +
                    "\t\t\t\t\t</div>";
        }

        if(appeals.size() == 0){
            html += "<p class=\"text-center\">No appeals present.</p>";
        }

        return html;
    }

    public static String getAppealRowForAdmin(ArrayList<Appeal> appeals){
        String html = "";
        appeals.sort((o1, o2) -> {
            if((o1.getStatus().equals("Unresolved") && o2.getStatus().equals("Unresolved")) || (!o1.getStatus().equals("Unresolved") && !o2.getStatus().equals("Unresolved"))) {
                if (o1.getTeam() - o2.getTeam() == 0) {
                    return o1.getProblemNumber() - o2.getProblemNumber();
                } else return o1.getTeam() - o2.getTeam();
            }else{
                if(o1.getStatus().equals("Unresolved")) return -1;
                else return 1;
            }
        });

        for(Appeal a : appeals){
            html += "                    <div class=\"white-back-div\">\n" +
                            "                        <h1>Team " + a.getTeam() + "</h1>\n" +
                            "                        <h2>Problem " + a.getProblemNumber() + "</h2>\n" +
                            "                        <b>Message: </b>" + a.getMessage() + "\n" +
                            "                        <br><form id=\"feedbackForm\" class=\"text-center\" action = \"editStatus\" method = \"POST\">\n" +
                            "                        <label for=\"problem\"><b>Status</b></label>\n" +
                            "                        <input type=\"hidden\" name=\"team\" value=\"" + a.getTeam() + "\" />" +
                            "                        <input type=\"hidden\" name=\"probNum\" value=\"" + a.getProblemNumber() + "\" />" +
                            "                        <input type=\"text\" class=\"form-control\" id=\"problem\" name=\"status\" placeholder=\"" + a.getStatus() + "\" aria-describedby=\"emailHelp\">\n" +
                            "                        <br><button onclick=\"reload()\" type=\"submit\" id=\"statusSubmit\" class=\"btn btn-primary btn-lg\"> Set</button>\n" +
                            "                        </form>\n" +
                            "                    </div>";
        }

        if(appeals.size() == 0){
            html += "<p class=\"text-center\">No appeals present.</p>";
        }

        return html;
    }
}
