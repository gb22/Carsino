package group10.db;

/**
 * Created by tony on 15-5-5.
 */


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;



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
            Log.d("Worked", "sdsdsd666uu tony????");

            conn = DriverManager.getConnection(url + dbName, userName, password);

            Log.d("Worked", "sdsdsd666uu tony");
        } catch (java.sql.SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }







    public static void InsertName(String name) {
        Connection conn = ConnectingSQL();
        try {

            //PreparedStatement instatement = (PreparedStatement) conn.prepareStatement(TranslatorV1.translateInsertTeam(t.getName(),t.getPnp(),t.isCl()));
            String query = "INSERT INTO HighScore (Name) VALUES ('" + name + "')";

            Log.d("BDJDS222222222222", query);

            if(conn.isClosed()) {
                Log.d("1111", "2222");
            }
            else {
                Log.d("2222", "2222");
            }
            Statement statement = conn.createStatement();
            Log.d("BDJDS33333333333333333333333333", "sdsdsdsdsds");
            ResultSet rs = statement.executeQuery(query);

            statement.execute(query);
         while(rs.next()){
             System.out.println(rs.getString("Name"));
         }
            rs.close();
            Log.d("BDJDS4444444444444444444444444444444444", "dssd3333");
            statement.close();
            Log.d("BDJDS555555555555555555555555", "5t5gtggb");
            conn.close();
            Log.d("BDJDS", "ghghgth6667");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
