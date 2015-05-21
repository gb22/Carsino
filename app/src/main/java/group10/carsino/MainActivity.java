package group10.carsino;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.app.AlertDialog;

import group10.R;
import group10.gui.testGUI;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        //Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //Listener for slot machine button
        View.OnClickListener listnr=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, testGUI.class);
                MainActivity.this.startActivity(myIntent);
            }
        };
        Button btn =(Button) findViewById(R.id.btn);
        btn.setOnClickListener(listnr);

        //Listener for highscore button
        View.OnClickListener listnrHigh=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("group10.carsino.highscore");
                startActivity(intent);
            }
        };
        Button btnhigh =(Button) findViewById(R.id.high);
        btnhigh.setOnClickListener(listnrHigh);

    }

    public void showPopup(View view){
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("About!");
        alertDialog.setMessage("This app is made by Group 10 from the University of Gothenburg.");
        alertDialog.show();
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
}
