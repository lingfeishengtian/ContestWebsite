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
import java.sql.SQLException;

@WebServlet("/generateIndividual")
public class IndividualScoreReport extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        boolean elevated = false;

        if(Authenticator.doesUserHaveElevatedPermissions(req, getServletContext())){
            elevated = true;
        }

        try {
            String id = req.getParameter("id");
            if(id.equals("1") && elevated) {
                out.println(GenerateFinalData.generateIndividualReport(getServletContext().getRealPath("") + "WEB-INF/pc2-9.6.0",true, false));
            }else if(id.equals("2")) {
                out.println(GenerateFinalData.generateIndividualReport(getServletContext().getRealPath("") + "WEB-INF/pc2-9.6.0",false, !elevated));
            }else{
                out.println(Authenticator.generateHTMLMessage("That is not a valid ID or you do not have permission."));
            }
        } catch (SQLException e) {
            out.println(Authenticator.generateHTMLMessage("An internal error occurred. Immediately contact staff please!"));
            e.printStackTrace();
        }

        out.flush();
    }
}
