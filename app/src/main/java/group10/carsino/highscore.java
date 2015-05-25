package group10.carsino;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import group10.R;

import android.widget.ListView;
import android.widget.ArrayAdapter;
import group10.db.JavaDBCon;
import group10.db.JavaDBCon.data;

import java.util.ArrayList;

public class highscore extends ActionBarActivity {
    //Get's a list with name and score from the database in to the ListView with asyntask
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                data[] s = JavaDBCon.Getdatas();
                System.out.println("vad är detta " + s);

               final ArrayList<String> list = new ArrayList<>();

                for (int i = 0; i < s.length; i++) {
                    list.add(s[i].getName() + "\t" + "Score:" + s[i].getscore());
                    System.out.println(s[i].getName() + s[i].getscore());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateListView(list);

                    }
                });
                return null;
            }
        }.execute();
    }
    // set a listview
    public void populateListView(ArrayList list){
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.name_list, list));
       // arrayAdapter = new ArrayAdapter(this, android.R.layout.name_list, list);
        //listview.setAdapter(arrayAdapter);
    }
}