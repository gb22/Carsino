package Voice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Oliver on 5/2/2015.
 */
public class VoiceControl extends Service {

    public void onCreate() {
        super.onCreate();
        Voice v = new Voice();
        }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
