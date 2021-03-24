package accountpages.admin;

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
        PrintWriter out = resp.getWriter();

        if(Authenticator.doesUserHaveElevatedPermissions(req, getServletContext())) {
            try {
                out.println(GenerateFinalData.generateFinalReport(getServletContext().getRealPath("") + "WEB-INF/pc2-9.6.0", false, false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.println(Authenticator.generateHTMLMessage("You do not have permission or not logged in!"));
        }

        out.flush();
    }
}
