package group10.carsino;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import group10.R;


public class Sound extends ActionBarActivity {
    MediaPlayer Sound;
    MediaPlayer Soundtwo;
    MediaPlayer Soundthree;
    private static Button btnsound;
    private static Button btnsoundtwo;
    private static Button soundthree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        Sound = MediaPlayer.create (this, R.raw.coins);
        Soundtwo= MediaPlayer.create(this, R.raw.bell);
        playsound();
        playsoundtwo();
        playsoundthree();
    }

    public void playSound (View view) {
        Sound.start();
    }

    public void playsound(){
        btnsound=(Button) findViewById(R.id.button_sound);

        btnsound.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Sound.start();

                    }
                });
    }


    public void playsoundtwo() {
        btnsoundtwo = (Button) findViewById(R.id.sound_button);
        btnsoundtwo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Soundtwo.start();

                    }
                });
    }

    public void playsoundthree() {
        soundthree = (Button) findViewById(R.id.button);
        soundthree.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Soundthree.start();

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
