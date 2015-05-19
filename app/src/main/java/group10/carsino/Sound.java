package group10.carsino;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import group10.R;


public class Sound extends ActionBarActivity {

    MediaPlayer Sound;
    private static Button btnsound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        Sound = MediaPlayer.create(this, R.raw.coins);
        playsound();
    }

    public void playSound (View view) {
        Sound.start();
    }

    public void playsound(){
        btnsound=(Button) findViewById(R.id.button_sound);

        btnsound.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                public void onClick (View v){

                        Sound.start();

                    }
    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound, menu);
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

