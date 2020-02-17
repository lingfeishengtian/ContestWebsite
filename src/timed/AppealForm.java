package timed;
import login.Authenticator;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.*;
import java.text.SimpleDateFormat;

//TODO: IMPLEMENT APPEALS IN DATABASE AND ALLOW ADMIN CONNECTED TO SEE

@WebServlet("/AppealForm")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class AppealForm extends HttpServlet {
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  

        ServletContext context = getServletContext();
        String relativePath = context.getRealPath("");

        String sessionID = request.getSession().getId();
        int teamNum = Authenticator.getTeamFromSessionID(sessionID, relativePath + "WEB-INF/session-tracker");

        if(teamNum > -1) {
            Scanner scan = new Scanner(new File(relativePath + "WEB-INF/data/contestStartDate.txt"));
            scan.nextLine();

            SimpleDateFormat formatter = new SimpleDateFormat(scan.nextLine());
            scan.nextLine();
            scan.nextLine();
            scan.nextLine();
            Date current = new Date();
            Date contestEnd = null;
            Date contestAppealAcceptEnd = null;
            try {
                contestEnd = formatter.parse(scan.nextLine());
                scan.nextLine();
                contestAppealAcceptEnd = formatter.parse(scan.nextLine());
            } catch (Exception e) {
                System.out.println("internal error");
            }

            String problemNum = request.getParameter("problem");
            String message = request.getParameter("message");

            int problem = -1;
            try {
                problem = Integer.parseInt(problemNum);
            } catch (Exception e) {
                out.println(Authenticator.generateHTMLMessage("Problem number is not a number!"));
            } finally {
                if (current.compareTo(contestEnd) >= 0 && current.compareTo(contestAppealAcceptEnd) <= 0) {
                    File appealsDir = new File(relativePath + "WEB-INF/appeals");

                    if (appealsDir.exists()) {
                        FileWriter myWriter = new FileWriter(relativePath + "WEB-INF/appeals/team" + teamNum + "problem" + problem + ".txt");

                        myWriter.write(message);
                        myWriter.close();
                        out.println(Authenticator.generateHTMLMessage("Your appeal has been generated!"));
                    } else {
                        out.println(Authenticator.generateHTMLMessage("Something went wrong and your appeal couldn't be generated. Please contact administrators immediately!"));
                    }

                } else {
                    out.println(Authenticator.generateHTMLMessage("Appeal time ended."));
                }
            }
        }else{
            out.println(Authenticator.generateHTMLMessage("You have not logged in yet!"));
        }
        out.flush();
    }
}