package group10.db;

/**
 * Created by tony on 15-5-5.Change
 */


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import group10.R;


public class JavaDBCon extends ActionBarActivity {

    public static void connection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connected");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Used for connecting to the db server
    public static Connection ConnectingSQL() {
        connection();
        String url = "jdbc:mysql://sql5.freemysqlhosting.net/";
        String dbName = "sql574872";
        String userName = "sql574872";
        String password = "zH6!eW7*";
        Connection conn = null;
        try {


             conn = DriverManager.getConnection(url+dbName, userName, password);




        } catch (java.sql.SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    // insert name and score into database with asynctask
    public static void InsertUser(final String name, final int score) {


            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    Connection conn = ConnectingSQL();
                    try {

                        String query = "INSERT INTO HighScore (Name,Score) VALUES ('" + name + "'," + score + ")";



                        if (conn.isClosed()) {

                        } else {

                        }
                        Statement statement = conn.createStatement();


                        statement.execute(query);

                        statement.close();

                        conn.close();


                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } return null;
                }
        }.execute();
    }
   /** public static void getdata(String username) {
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
    }*/
// test for geting data from database.

    public static void getdata() {
        Connection conn = ConnectingSQL();

        try {

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM HighScore");
            while (rs.next()) {
                String name1 = rs.getString("NAME");
                int score = rs.getInt("Score");

                System.out.println(name1 + "\n" + score);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //Get's a list with name and score from the database
    public static data[] Getdatas() {
        Connection conn = ConnectingSQL();
        data t = null;
        ResultSet rs = null;
        data[] ta = null;
        int rsrows = 0;

        String s = "SELECT Name,Score FROM HighScore Order By Score Desc LIMIT 10";
        System.out.println(conn);
        try {

            PreparedStatement outstatement = conn.prepareStatement(s);

            rs = outstatement.executeQuery();

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
    //Constructor for getinformation from db
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
