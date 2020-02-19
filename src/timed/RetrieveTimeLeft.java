package timed;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@WebServlet("/timeTillContestStart")
public class RetrieveTimeLeft extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String relativePath = context.getRealPath("");
        PrintWriter out = resp.getWriter();

        Scanner scan = new Scanner(new File(relativePath + "WEB-INF/data/contestStartDate.txt"));
        scan.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat(scan.nextLine());
        Date current = new Date();
        Date fileDate = null;
        scan.nextLine();
        try {
            fileDate = formatter.parse(scan.nextLine());
        } catch (Exception e) {
            System.out.println("An internal error occurred.");
        }
        if (fileDate != null){
            out.println((fileDate.getTime() - current.getTime())/1000);
        }
        out.flush();
    }
}
