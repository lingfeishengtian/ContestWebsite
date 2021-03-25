package com.lingfeishengtian.contestwebsite;

import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.data_management.DatabaseUtils;
import com.lingfeishengtian.contestwebsite.utils.data_management.GenerateFinalData;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Scanner;

public class StartupContext
        implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            Path folder = Paths.get(arg0.getServletContext().getRealPath("") + "WEB-INF/");
            createFolderIfNotExist(folder.resolve("appeals").toString());
            DatabaseUtils.setConnection(arg0.getServletContext().getRealPath(""));
            System.out.println(folder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Scanner scan = new Scanner(new File(arg0.getServletContext().getRealPath("") + "WEB-INF/data/config"));
            while(scan.hasNext()){
                String line = scan.nextLine();
                if(!line.startsWith(";")){
                    String[] prop = new String[2];
                    prop[0] = line.substring(0, line.indexOf(':'));
                    prop[1] = line.substring(line.indexOf(':') + 1);
                    if(prop.length == 2){
                        switch (prop[0]){
                            case "admin-password":
                                Authenticator.setAdminPasscode(prop[1]);
                                break;
                            case "pc2-loc":
                                GenerateFinalData.setPC2Loc(prop[1]);
                                break;
                            case "contest-name":
                                MainPage.competition_name = prop[1];
                                break;
                        }
                    }else{
                        System.out.println("Ignored " + line + "\nThis config line is invalid.");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Can't find config file!");
            System.exit(10);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public static final boolean createFolderIfNotExist(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
           return f.mkdir();
        }
        return true;
    }
}