package utils.data_management;

import utils.types.Team;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseUtils {
    // INSERT CODE: INSERT INTO Registration1 VALUES(1, "FUCK", 132, "DIDDY", 123, "PPSUCK", 53, 123)
    private static Connection connection;
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

    public static void updateTeamProgrammingScore(Connection connection, int team, int programmingScore) throws SQLException {
        String sql = "UPDATE Registration\n" +
                "SET ProgrammingScore = " + programmingScore + "\n" +
                "WHERE TeamNumber = " + team + ";";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

    public static void updateTeamValues(Connection connection, int team, String school, String teammate1, int teammate1score, String teammate2, int teammate2score, String teammate3, int teammate3score) throws SQLException {
        String sql = "UPDATE Registration\n" +
                "SET SchoolName = \"" + school + "\", Teammate1Name = \"" + teammate1 + "\", Teammate1Written = " + teammate1score + ", Teammate2Name = \"" + teammate2 + "\", Teammate2Written = " + teammate2score + ", Teammate3Name = \"" + teammate3 + "\", Teammate3Written = " + teammate3score +  "\n" +
                "WHERE TeamNumber = " + team + ";";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

    public static Connection getConnectionAndAutoCheck(String path) throws SQLException {
        if (connection == null) {
            connection = getConnection(path);
        }
        return connection;
    }

    private static Connection getConnection(String path) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("WHOOPS");
        }
        Connection a = connect(path);
        tableCheck(a);
        return a;
    }

    public static Team[] getRegisteredTeams(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Registration";
        ArrayList<Team> teams = new ArrayList<>();

        Statement stmt  = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            Team team = new Team();
            team.team = rs.getInt("TeamNumber");
            team.school = rs.getString("SchoolName");
            team.teammate1 = rs.getString("Teammate1Name");
            team.teammate2 = rs.getString("Teammate2Name");
            team.teammate3 = rs.getString("Teammate3Name");
            team.teammate1score = rs.getInt("Teammate1Written");
            team.teammate2score = rs.getInt("Teammate2Written");
            team.teammate3score = rs.getInt("Teammate3Written");
            team.programmingScore = rs.getInt("ProgrammingScore");

            teams.add(team);
        }

        stmt.close();
        return teams.toArray(new Team[0]);
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

        stmt.close();

        return false;
    }

    private static Connection connect(String contextPath) {
        String url = "jdbc:sqlite:" + contextPath + "WEB-INF/data/data.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}