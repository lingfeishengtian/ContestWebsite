package accountpages.team;

import login.Authenticator;
import utils.data_management.DatabaseUtils;
import utils.data_management.GenerateFinalData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/generateReport")
public class AdminReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(),getServletContext().getRealPath("") + "WEB-INF/session-tracker");
        PrintWriter out = resp.getWriter();

        if(team == 0){
            try {
                Connection connection = DatabaseUtils.getConnectionAndAutoCheck(getServletContext().getRealPath(""));
                out.println(GenerateFinalData.generateFinalReport(connection, getServletContext().getRealPath("") + "pc2-9.6.0"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(team > 0){
            out.println(Authenticator.generateHTMLMessage("Hey, teams can't access the modification post!"));
        }else{
            out.println(Authenticator.generateHTMLMessage("You aren't even logged in!"));
        }

        out.flush();
    }
}
