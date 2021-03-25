package com.lingfeishengtian.contestwebsite.utils.data_management;

import com.lingfeishengtian.contestwebsite.login.Authenticator;
import com.lingfeishengtian.contestwebsite.utils.types.Appeal;
import com.lingfeishengtian.contestwebsite.utils.types.Team;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseUtils {
    private static Connection connection;
    final static String SQLCreateNewRegistrationTable = "CREATE TABLE Registration(\n" +
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
    final static String SQLCreateNewAppealsTable = "CREATE TABLE Appeals(\n" +
            "  ProblemNumber int NOT NULL,\n" +
            "  Team int NOT NULL,\n" +
            "  Status varchar(225)\n" +
            ");";

    public static void setConnection(String path) throws SQLException {
        if (DatabaseUtils.connection == null) {
            DatabaseUtils.connection = getConnection(path);
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("TEST DATABASE WORKS");

        String relativePath = "";
        setConnection(relativePath);
        CreateAppeal(new Appeal(4, 2, "oops", "Unresolved"), relativePath);
        System.out.println(GetAppealsForTeam(2, relativePath));
        System.out.println(GetAppealsForTeam(1, relativePath));
        System.out.println(GetAppeals(relativePath));
    }

    public static void CreateAppeal(Appeal a, String relativePath) throws SQLException, IOException {
        String sql = "INSERT INTO Appeals VALUES(?, ?, ?)";

        String fileName = "team" + a.getTeam() + "problem" + a.getProblemNumber();
        if(!DoesAppealExist(a)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, a.getProblemNumber());
            statement.setInt(2, a.getTeam());
            statement.setString(3, a.getStatus());
            statement.execute();
        }else{
            sql = "UPDATE Appeals SET Status=? WHERE ProblemNumber=? AND Team=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, a.getStatus());
            statement.setInt(2, a.getProblemNumber());
            statement.setInt(3, a.getTeam());
            statement.execute();
        }

        File appealsDir = new File(relativePath + "WEB-INF/appeals");
        if (appealsDir.exists()) {
            FileWriter myWriter = new FileWriter(relativePath + "WEB-INF/appeals/" + fileName + ".txt");

            myWriter.write(a.getMessage());
            myWriter.close();
        } else {
            throw new FileNotFoundException(appealsDir.getPath() + " does not exist!");
        }
    }

    public static boolean DoesAppealExist(Appeal a) throws SQLException {
        String sql = "SELECT * FROM Appeals WHERE ProblemNumber = ? AND Team = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, a.getProblemNumber());
        stmt.setInt(2, a.getTeam());
        ResultSet rs = stmt.executeQuery();

        return rs.next();
    }

    public static ArrayList<Appeal> GetAppeals(String relativePath) throws SQLException, IOException {
        String sql = "SELECT * FROM Appeals";

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Appeal> appeals = getAppealsFromDir(relativePath, rs);

        return appeals;
    }

    public static ArrayList<Appeal> GetAppealsForTeam(int team, String relativePath) throws SQLException, IOException {
        String sql = "SELECT * FROM Appeals\n" +
                "WHERE Team = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, team);
        ResultSet result = statement.executeQuery();

        ArrayList<Appeal> appeals = getAppealsFromDir(relativePath, result);

        return appeals;
    }

    private static ArrayList<Appeal> getAppealsFromDir(String relativePath, ResultSet result) throws SQLException, IOException {
        ArrayList<Appeal> appeals = new ArrayList<>();

        File appealsDir = new File(relativePath + "WEB-INF/appeals");
        if (appealsDir.exists()) {
            while(result.next()){
                Appeal a = new Appeal(result.getInt("ProblemNumber"), result.getInt("Team"), result.getString("Status"));
                String fileName = "team" + a.getTeam() + "problem" + a.getProblemNumber();

                File file = new File(relativePath + "WEB-INF/appeals/" + fileName + ".txt");
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();

                String str = new String(data, "UTF-8");

                a.setMessage(str);
                appeals.add(a);
            }
        } else {
            throw new FileNotFoundException(appealsDir.getPath() + " does not exist!");
        }
        return appeals;
    }

    public static void SolveAppeal(Appeal a) throws SQLException {
        String sql = "UPDATE Appeals\n" +
                "SET STATUS = ?\n" +
                "WHERE ProblemNumber = ? AND Team = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, a.getStatus());
        statement.setInt(2, a.getProblemNumber());
        statement.setInt(3, a.getTeam());
        statement.execute();
    }

    public static void updateTeamProgrammingScore(int team, int programmingScore) throws SQLException {
        String sql = "UPDATE Registration\n" +
                "SET ProgrammingScore = ?\n" +
                "WHERE TeamNumber = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1 ,programmingScore);
        statement.setInt(2 ,team);
        statement.execute();
    }

    public static void updateTeamValues(int team, String school, String teammate1, int teammate1score, String teammate2, int teammate2score, String teammate3, int teammate3score) throws SQLException {
        String sql = "UPDATE Registration\n" +
                "SET SchoolName = ?, Teammate1Name = ?, Teammate1Written = ?, Teammate2Name = ?, Teammate2Written = ?, Teammate3Name = ?, Teammate3Written = ?\n" +
                "WHERE TeamNumber = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, school);
        statement.setString(2, teammate1);
        statement.setInt(3, teammate1score);
        statement.setString(4, teammate2);
        statement.setInt(5, teammate2score);
        statement.setString(6, teammate3);
        statement.setInt(7, teammate3score);
        statement.setInt(8, team);
        statement.execute();
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

    public static Team[] getRegisteredTeams() throws SQLException {
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

    public static void teamRegistration(int team, String schoolName, String name1, String name2, String name3) throws SQLException {
        String sql = "INSERT INTO Registration VALUES(?, ?, ?, NULL, ?, NULL, ?, NULL, NULL)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, team);
        statement.setString(2, schoolName);
        statement.setString(3, name1);
        statement.setString(4, name2);
        statement.setString(5, name3);
        statement.execute();
    }

    private static void tableCheck(Connection connection) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "Registration", null);
        ResultSet tables1 = dbm.getTables(null, null, "Appeals", null);
        if (!tables.next()) {
            PreparedStatement create = connection.prepareStatement(SQLCreateNewRegistrationTable);
            create.execute();
        }
        if (!tables1.next()) {
            PreparedStatement create = connection.prepareStatement(SQLCreateNewAppealsTable);
            create.execute();
        }
    }

    public static boolean hasTeamRegistered(int team) throws SQLException {
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