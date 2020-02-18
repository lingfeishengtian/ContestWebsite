package timed;// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\HelloServlet.java"
import login.Authenticator;
import utils.AuthorizedDownload;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
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
       if(Authenticator.getTeamFromSessionID(request.getSession().getId(), getServletContext().getRealPath("") + "WEB-INF/session-tracker") > -1) {
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

           OutputStream outStream = response.getOutputStream();
           if (fileDate != null && current.compareTo(fileDate) >= 0) {
               String filePath = relativePath + "WEB-INF/secure-downloads/SampleData.zip";
               AuthorizedDownload.downloadFile(response, outStream, context, filePath);
           } else {
               response.setContentType("text/html");
               outStream.write("<html><body><script>alert('The contest hasn\\'t started yet!'); window.history.back();</script></body></html>".getBytes());
           }
           outStream.close();
       }else{
           PrintWriter printWriter = response.getWriter();
           printWriter.println(Authenticator.generateHTMLMessage("You are not authorized to download this file! Please login!"));
           printWriter.close();
       }
    }
}