package group10.carsino;


import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import group10.R;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import group10.db.JavaDBCon;
import group10.db.JavaDBCon.data;















public class highscore extends ActionBarActivity {
    ListView list;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data[] data = (JavaDBCon.data[]) getIntent().getExtras().get("Someth");
        populataListView(data);
        setContentView(R.layout.activity_highscore);

    }


    public void populataListView(data[] Data) {

        String[] teams = new String[Data.length];

        for (int i = 0; i < Data.length; i++) {
            teams[i] = Data[i].getName() + Data[i].getscore();






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