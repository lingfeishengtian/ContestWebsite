package utils;

import login.Authenticator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/authorized-download")
public class AuthorizedDownload extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int team = Authenticator.getTeamFromSessionID(req.getSession().getId(), getServletContext().getRealPath("") + "WEB-INF/session-tracker");
        OutputStream out = resp.getOutputStream();

        if(team == 0){
            ServletContext context = getServletContext();
            String relativePath = context.getRealPath("");

            String addon = "pc2-9.6.0-judge.zip";

            String filePath = relativePath + "WEB-INF/secure-downloads/" + addon;
            downloadFile(resp, out, context, filePath);
        }else{
            out.write(Authenticator.generateHTMLMessage("You are not authorized to download this file.").getBytes());
        }

        out.flush();
    }

    public static void downloadFile(HttpServletResponse resp, OutputStream out, ServletContext context, String filePath) throws IOException {
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);

        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        resp.setContentType(mimeType);
        resp.setContentLength((int) downloadFile.length());
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        resp.setHeader(headerKey, headerValue);

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        inStream.close();
    }
}
