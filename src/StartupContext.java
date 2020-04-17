import utils.data_management.DatabaseUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class StartupContext
        implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            DatabaseUtils.setConnection(arg0.getServletContext().getRealPath(""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}