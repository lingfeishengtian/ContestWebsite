package com.lingfeishengtian.contestwebsite.accountpages.admin;

import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.data_management.DatabaseUtils;
import com.lingfeishengtian.contestwebsite.utils.types.Appeal;
import com.lingfeishengtian.contestwebsite.utils.types.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/editStatus")
public class ModifyStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        if(Authenticator.doesUserHaveElevatedPermissions(req, getServletContext())){
            try{
                DatabaseUtils.SolveAppeal(new Appeal(Integer.parseInt(req.getParameter("probNum")), Integer.parseInt(req.getParameter("team")), req.getParameter("status")));

                out.println("<script>window.history.back()</script>");
            } catch (SQLException e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("Database error occurred. CONTACT STAFF!"));
            } catch (Exception e) {
                e.printStackTrace();
                out.println(Authenticator.generateHTMLMessage("Database can't be found error occurred. CONTACT STAFF!"));
            }
        }else{
            out.println(Authenticator.generateHTMLMessage("You aren't even logged in or no permission!"));
        }

        out.flush();
    }
}
