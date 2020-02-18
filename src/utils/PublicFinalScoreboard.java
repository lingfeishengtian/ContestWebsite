package utils;

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
            Connection connection = DatabaseUtils.getConnectionAndAutoCheck(getServletContext().getRealPath(""));
            out.println(GenerateFinalData.generateLessFinalReport(connection, getServletContext().getRealPath("") + "pc2-9.6.0"));
        }catch (Exception e){
            e.printStackTrace();
        }

        out.flush();
    }
}
