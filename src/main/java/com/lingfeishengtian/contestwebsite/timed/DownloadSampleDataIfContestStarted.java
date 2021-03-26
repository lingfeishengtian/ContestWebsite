package com.lingfeishengtian.contestwebsite.timed;// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\HelloServlet.java"
import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.AuthorizedDownload;
import com.lingfeishengtian.contestwebsite.utils.data_management.DatabaseUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.*;
import java.util.zip.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/downloadContestSampleData")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class DownloadSampleDataIfContestStarted extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
       int team = Authenticator.getTeamFromSessionID(request.getSession().getId());
       try {
           if(team > -1 && DatabaseUtils.hasTeamRegistered(team)) {
               ServletContext context = getServletContext();
               String relativePath = context.getRealPath("");

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

               Calendar c = Calendar.getInstance();
               c.setTime(fileDate);
               c.add(Calendar.MINUTE, -1);

               OutputStream outStream = response.getOutputStream();
               if (fileDate != null && current.after(c.getTime())) {
                   String filePath = relativePath + "WEB-INF/secure-downloads/SampleData.zip";
                   AuthorizedDownload.downloadFile(response, outStream, context, filePath);

                   System.out.println("Team " + team + " has downloaded sample data at " + current.toString());
               } else {
                   response.setContentType("text/html");
                   outStream.write("<html><body><script>alert('Please wait until 1 minute before the competition starts to download the sample data.'); window.history.back();</script></body></html>".getBytes());
               }

               outStream.close();
           }else{
               PrintWriter printWriter = response.getWriter();
               printWriter.println(Authenticator.generateHTMLMessage("You are not authorized to download this file! You are not logged in or you are not registered. PLEASE REGISTER!"));
               printWriter.close();
           }
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Contact system admins, a SQL error has landed.");
       }
   }
}