package group10.carsino;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import group10.R;
import android.widget.ListView;
import android.widget.ArrayAdapter;

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



public class highscore extends ActionBarActivity {
    ListView list;
    public class data{
     public String username;
     public int   score;
        public String getname(){
            return username;
        }

        public void setname(String username){

       this.username = username;
   }
        public int getscore(){
            return score;
 }
        public void setscore(int score){
            this.score =score;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_highscore);
        populatelistView();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
        return true;
    }




        public void JsonToArrayList(JSONArray myJsonArray) throws JSONException
        {
            ArrayList<data> listItems = new ArrayList<data>();
            JSONObject jo = new JSONObject();
            Template tem = new Template();
            ListView list = (ListView) findViewById(R.id.listView);

            String listItemString[] = new String[myJsonArray.length];

            for(int i = 0; i<myJsonArray.length(); i++)
            {
                jo = myJsonArray.getJSONObject(i);
                tem.username = jo.getString("username");
                listItemString[i]  = tem.username ; // u can change it according to ur need.
                listItems.add(tem);
                Log.e("Ninja Archives", tem.username);

        String[] getdata = new String[]{"fsdfsdfs", "dsfsdfsdf"};

        ListView list = (ListView) findViewById(R.id.listView);
    }
        ArrayAdapter<String>adapter =new  ArrayAdapter<String>(this,R.layout.name_list,listItemString);
        list.setAdapter(adapter);


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
