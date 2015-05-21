package group10.db;

/**
 * Created by tony on 15-5-5.Change
 */


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


public class JavaDBCon {

    public static void connection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connected");
            Log.d("Worked", "sdsdsd666uu tony???????????????");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Used for connecting to the db server, returns that connection so that it might be used elsweyr
    public static Connection ConnectingSQL() {
        connection();
        String url = "jdbc:mysql://sql5.freemysqlhosting.net/";
        String dbName = "sql574872";
        String userName = "sql574872";
        String password = "zH6!eW7*";
        Connection conn = null;
        try {
            System.out.println("Inne i try");

             conn = DriverManager.getConnection(url+dbName, userName, password);

            System.out.println("efter connn");


        } catch (java.sql.SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }


    public static void InsertUser(String name, int score) {
        Connection conn = ConnectingSQL();
        try {

            //PreparedStatement instatement = (PreparedStatement) conn.prepareStatement(TranslatorV1.translateInsertTeam(t.getName(),t.getPnp(),t.isCl()));
            String query = "INSERT INTO HighScore (Name,Score) VALUES ('" + name + "'," + score + ")";

            Log.d("BDJDS222222222222", query);

            if (conn.isClosed()) {
                Log.d("1111", "2222");
            } else {
                Log.d("2222", "2222");
            }
            Statement statement = conn.createStatement();
            Log.d("BDJDS3333333", "sdsdsdsdsds");

            statement.execute(query);
            Log.d("BDJDS444444444444444444", "dssd3333");
            statement.close();
            Log.d("BDJDS55555555555555555", "5t5gtggb");
            conn.close();
            Log.d("BDJDS", "ghghgth6667");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getdata(String username) {
        Connection conn = ConnectingSQL();

        try {

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM HighScore");
            while (rs.next()) {
                String name1 = rs.getString("NAME");
                int score = rs.getInt("Score");

                System.out.println(name1 + "\n" + score
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getdata() {
        Connection conn = ConnectingSQL();

        try {

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM HighScore");
            while (rs.next()) {
                String name1 = rs.getString("NAME");
                int score = rs.getInt("Score");

                System.out.println(name1 + "\n" + score
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static data[] Getdatas() {
        Connection conn = ConnectingSQL();
        data t = null;
        ResultSet rs = null;
        data[] ta = null;
        int rsrows = 0;
        String s = "SELECT * FROM HighScore";
        System.out.println(conn);
        try {
            System.out.println("ITY");
            PreparedStatement outstatement = conn.prepareStatement(s);
            System.out.println("ITY2");
            rs = outstatement.executeQuery();
            System.out.println("ITY3");
            rs.last();
            rsrows = rs.getRow();
            rs.beforeFirst();
            ta = new data[rsrows];
            System.out.println("Made a resultset");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < rsrows; i++) {
            try {
                rs.next();
                t = new data(rs.getString(1), rs.getString(2));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            ta[i] = t;
        }
        return ta;
    }

    public static class data  {
        public String name;
        public String score;
        data(String n, String p) {
            this.setName(n);
            this.setscore(p);

        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getscore() {
            return score;
        }

        public void setscore(String score) {
            this.score = score;
        }
    }
}