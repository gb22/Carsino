package group10.db;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import group10.R;
import android.widget.Button;
import android.content.Intent;

public class DBMain extends ActionBarActivity {
    private static Button button_sbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** if (android.os.Build.VERSION.SDK_INT > 9) {
         android.os.StrictMode.ThreadPolicy policy =
         new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
         android.os.StrictMode.setThreadPolicy(policy);
         }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        onClickbuttonListener();
    }
    // set a highsore activity screen
    public void onClickbuttonListener() {
        Button button_sbm = (Button) findViewById(R.id.button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("group10.carsino.highscore");
                        startActivity(intent);
                    }

                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //test button for connecting database
    public void buttononclick(View v) {
        JavaDBCon.InsertUser("superbadjs",500);
    }
}