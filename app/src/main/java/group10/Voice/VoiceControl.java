package group10.Voice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;

/**
 * Created by Oliver on 5/2/2015.
 */
public class VoiceControl extends Service implements RecognitionListener{

    public void onCreate() {
        super.onCreate();
        System.out.println("fag2");
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        }
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("fag3");
        return null;
    }



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