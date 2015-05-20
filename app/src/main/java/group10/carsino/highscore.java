package group10.carsino;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import group10.R;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.lang.reflect.Member;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import android.database.Cursor;

import static group10.db.JavaDBCon.ConnectingSQL;
import static group10.db.JavaDBCon.getdata;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import android.util.Log;
import org.json.JSONStringer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import android.view.View;
import android.widget.TextView;
import static group10.db.JavaDBCon.ConnectingSQL;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import org.json.JSONException;
import group10.db.JavaDBCon;
import group10.db.JavaDBCon.data;















public class highscore extends ActionBarActivity {
    ListView list;





    static ArrayList<String> resultRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_highscore);
        populataListView();
        StrictMode.enableDefaults();
    }


    public void populataListView() {


       data[] Data = JavaDBCon.Getdatas();
        String[] teams = new String[Data.length];
        for (int i = 0; i < Data.length; i++) {
            teams[i] = Data[i].getName();


            ListView list = (ListView) findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.name_list, teams);
            list.setAdapter(adapter);


        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
        return true;
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}