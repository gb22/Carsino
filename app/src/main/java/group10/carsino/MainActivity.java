package group10.carsino;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import group10.Voice.VoiceControlTest;


public class MainActivity extends ActionBarActivity {

    private static Context activityContext;
    private static VoiceControlTest voiceCommandService;
    //Shit
    int iView=0;
    //hoan
    private int mBindFlag;
    private Messenger mServiceMessenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("fag1");
        //startService(new Intent(this, group10.Voice.VoiceControl.class));
        //hoan
        activityContext= this;
        Intent service = new Intent(activityContext, VoiceControlTest.class);
        activityContext.startService(service);
        mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0 : Context.BIND_ABOVE_CLIENT;

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
    public static void sayHi(){
        System.out.println("Badjs");
        /*if (iView==0) {
            setContentView(R.layout.activity_hello);
            iView = 1;
        }
        if(iView==1){
            setContentView(R.layout.activity_pending);
            iView= 0;
        }*/
        //spinCount();
    }
    
    //Cheat code from SO
    public static void init(Context context)
    {
        voiceCommandService = new VoiceControlTest();
        activityContext = context;
    }

    public static void startContinuousListening()
    {
        Intent service = new Intent(activityContext, VoiceControlTest.class);
        activityContext.startService(service);

        Message msg = new Message();
        msg.what = VoiceControlTest.MSG_RECOGNIZER_START_LISTENING;

        try
        {
            voiceCommandService.mServerMessenger.send(msg);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

    }
}
