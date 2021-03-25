package com.lingfeishengtian.contestwebsite.timed;
import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.data_management.DatabaseUtils;
import com.lingfeishengtian.contestwebsite.utils.types.Appeal;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;

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

       try {
           boolean hasReg = DatabaseUtils.hasTeamRegistered(teamNum);
           if(teamNum > -1 && hasReg) {
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
                   if(message.length() <= 10000 && problem <= 1000) {
                       if (current.compareTo(contestEnd) >= 0 && current.compareTo(contestAppealAcceptEnd) <= 0) {
                           try {
                               DatabaseUtils.CreateAppeal(new Appeal(problem, teamNum, message, "Unresolved"), relativePath);
                               out.println(Authenticator.generateHTMLMessage("Success!"));
                           } catch (SQLException e) {
                               System.out.println(e);
                               out.println(Authenticator.generateHTMLMessage("An SQL error occurred. Contact the administrator."));
                           }
                       } else {
                           out.println(Authenticator.generateHTMLMessage("Appeal time ended."));
                       }
                   }else{
                       out.println(Authenticator.generateHTMLMessage("Message cannot be greater than 10,000 characters and problem number cannot be greater than 1000."));
                   }
               }
           }else{
               out.println(Authenticator.generateHTMLMessage("You have not registered yet!"));
           }
       } catch (SQLException e) {
           e.printStackTrace();
           out.println(Authenticator.generateHTMLMessage("An error occurred when trying to retrieve whether your team has registered or not. Please contact admin!"));
       }
       out.flush();
    }
}