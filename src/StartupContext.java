import utils.data_management.DatabaseUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

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
    }

    public static final boolean createFolderIfNotExist(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
           return f.mkdir();
        }
        return true;
    }
}