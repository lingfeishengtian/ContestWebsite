package accountpages.team;

import java.sql.*;

public class RegistrationUtils {
    // INSERT CODE: INSERT INTO Registration1 VALUES(1, "FUCK", 132, "DIDDY", 123, "PPSUCK", 53, 123)
    final static String SQLCreateNewTableCode = "CREATE TABLE Registration(\n" +
            "  TeamNumber int NOT NULL,\n" +
            "  SchoolName varchar(225) NOT NULL,\n" +
            "  Teammate1Name varchar(225),\n" +
            "  Teammate1Written int,\n" +
            "  Teammate2Name varchar(225),\n" +
            "  Teammate2Written int,\n" +
            "  Teammate3Name varchar(225),\n" +
            "  Teammate3Written int,\n" +
            "  ProgrammingScore INT\n" +
            ");";

    public static void main(String[] args) throws SQLException {
        System.out.println("TEST DATABASE WORKS");

        Connection a = connect("/Users/hunterhan/IdeaProjects/ComputerScienceContestWebsite/out/artifacts/");
        tableCheck(a);
        System.out.println(hasTeamRegistered(1, a));
        teamRegistration(a, 2, "Clements HS", "Yifan Ma", "POLP Ma", "Daniaaaal PEEPEE");
    }

    public static Connection getConnectionAndAutoCheck(String path) throws SQLException {
        Connection a = connect(path);
        tableCheck(a);
        return a;
    }

    public static void teamRegistration(Connection connection, int team, String schoolName, String name1, String name2, String name3) throws SQLException {
        String sql = "INSERT INTO Registration VALUES(" + team + ", \"" + schoolName + "\" , \""  + name1 + "\", NULL, \"" + name2 + "\", NULL, \"" + name3 + "\", NULL, NULL)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

    private static void tableCheck(Connection connection) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "Registration", null);
        if (!tables.next()) {
            PreparedStatement create = connection.prepareStatement(SQLCreateNewTableCode);
            create.execute();
        }
    }

    public static boolean hasTeamRegistered(int team, Connection connection) throws SQLException {
        String sql = "SELECT TeamNumber FROM Registration";

        Statement stmt  = connection.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);

        while (rs.next()) {
            int a = (rs.getInt("TeamNumber"));
            if(a == team) return true;
        }
        return false;
    }

    private static Connection connect(String contextPath) {
        String url = "jdbc:sqlite:" + contextPath + "WEB-INF/data.db";
        System.out.println(contextPath);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}