package group10.Voice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by Oliver on 5/2/2015.
 */
public class VoiceControl extends Service {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate();
        Voice v = new Voice();
        v.onCreate(savedInstanceState);
        }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
