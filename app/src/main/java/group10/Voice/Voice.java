package group10.Voice;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import group10.R;

//import android.speech.SpeechRecognizer;

//kommentar
public class Voice extends ActionBarActivity implements RecognitionListener {
    static int reCode = 1203;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("fag4");
        setContentView(R.layout.activity_pending);
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startActivityForResult(i, reCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == reCode && resultCode == RESULT_OK){
            sayHi();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //badjs
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
    public void sayHi(){
        setContentView(R.layout.activity_hello);
        //spinCount();
    }

    /*private void spinCount() {
        long c = 0;
        long i = 0;
        long ii = System.currentTimeMillis();
        while (c > -5000) {
            i = System.currentTimeMillis();
            c = ii - i;
        }
        reset();
        
    }*/

    /*private void reset() {
        setContentView(R.layout.activity_db);
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startActivityForResult(i, badjs);
    }*/

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
      //  sayHi();
    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
