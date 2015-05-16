package group10.carsino;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import group10.R;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;





public class highscore extends ActionBarActivity {
  private static ListView list_view;

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

    public void populatelistView () {
        String[]NAMES = new String[]{"tony","jack"};
        list_view =(ListView)findViewById(R.id.listView);
        ArrayAdapter<String>adapter =new  ArrayAdapter<String>(this,R.layout.name_list,NAMES);
        list_view.setAdapter(adapter);

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
