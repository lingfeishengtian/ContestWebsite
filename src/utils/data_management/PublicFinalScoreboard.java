package utils.data_management;

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

@WebServlet("/publicfinalscoreboard")
public class PublicFinalScoreboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        try {
            String shouldShowNavBar = req.getParameter("shouldShowNavBar");
            if(shouldShowNavBar == null) shouldShowNavBar = "y";
            boolean elevated = Authenticator.doesUserHaveElevatedPermissions(req, getServletContext());
            out.println(GenerateFinalData.generateFinalReport(getServletContext().getRealPath("") + "pc2-9.6.0", !elevated, !shouldShowNavBar.substring(0, 1).toLowerCase().startsWith("n")));
        }catch (Exception e){
            e.printStackTrace();
        }

        out.flush();
    }
}
