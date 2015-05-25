package group10.Voice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import group10.gui.testGUI;

/**
 * Created by John(Olivers dator) on 5/3/2015.
 */
public class VoiceControlTest extends Service {
    public AudioManager mAudioManager;
    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;
    public final Messenger mServerMessenger = new Messenger(new IncomingHandler(this));
    //private TextView txtSpeechInput;

    protected boolean mIsListening;
    protected volatile boolean mIsCountDownOn;

    public static final int MSG_RECOGNIZER_START_LISTENING = 1;
    public static final int MSG_RECOGNIZER_CANCEL = 2;

    private int mBindFlag;
    private Messenger mServiceMessenger;

    private static Context mActivityContext;

    @Override
    public void onCreate()
        {
        super.onCreate();
        //System.out.println("yay?");
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 500);
        bindService(new Intent(this, VoiceControlTest.class), mServiceConnection, mBindFlag);


    }
    //Needed for jellybean workaround
    //taken from StackOverFlow
    //http://stackoverflow.com/questions/18039429/
    //rmooney and Hoan Nguyen
    protected static class IncomingHandler extends Handler
    {
        private WeakReference<VoiceControlTest> mtarget;
        IncomingHandler(VoiceControlTest target)
        {
            mtarget = new WeakReference<VoiceControlTest>(target);
        }


        @Override
        public void handleMessage(Message msg)
        {
           final VoiceControlTest target = mtarget.get();

            switch (msg.what)
            {
                case MSG_RECOGNIZER_START_LISTENING:

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    {
                        //Turns off the beep sound
                        target.mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                    }
                    if (!target.mIsListening)
                    {
                        target.mSpeechRecognizer.startListening(target.mSpeechRecognizerIntent);
                        target.mIsListening = true;
                        //Log.d(TAG, "message start listening"); //$NON-NLS-1$
                    }
                    break;

                case MSG_RECOGNIZER_CANCEL:
                    target.mSpeechRecognizer.cancel();
                    target.mIsListening = false;
                    //Log.d(TAG, "message canceled recognizer"); //$NON-NLS-1$
                    break;
            }
        }
    }

    //Count down timer for Jelly Bean work around taken from StackOverFlow
    // http://stackoverflow.com/questions/18039429/
    //rmooney and Hoan Nguyen
    protected CountDownTimer mNoSpeechCountDown = new CountDownTimer(5000, 5000)
    {
        @Override
        public void onTick(long millisUntilFinished) {;}
        @Override
        public void onFinish()
        {
            System.out.println("onFinish");
            mIsCountDownOn = false;
            Message message = Message.obtain(null, MSG_RECOGNIZER_CANCEL);
            try
            {
                mServerMessenger.send(message);
                message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
                mServerMessenger.send(message);
            }
            catch (RemoteException e){}
        }
    };
    //Unmakes everything that is going on in the recognition classes.
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //System.out.println("onDestroy");
        stopService(new Intent(this, VoiceControlTest.class));
        unbindService(mServiceConnection);
        mSpeechRecognizer.destroy();
        if (mIsCountDownOn)
        {
            mNoSpeechCountDown.cancel();
        }
        if (mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }
        this.stopSelf();

    }

    protected class SpeechRecognitionListener implements RecognitionListener
    {
        //The recognizer has begun processing the conversation
        @Override
        public void onBeginningOfSpeech()
        {
            //So , there is no need for count down anymore
            if (mIsCountDownOn)
            {
                mIsCountDownOn = false;
                mNoSpeechCountDown.cancel();
            }
            System.out.println("oBOS");
        }
        @Override
        public void onBufferReceived(byte[] buffer)
        {
            //System.out.println("oBR");
        }
        @Override
        public void onEndOfSpeech()
        {
            //System.out.println("oEOS");
        }
        //If the VoiceRecognition gets stuck it will try to restart itself
        @Override
        public void onError(int error)
        {
            if (mIsCountDownOn)
            {
                mIsCountDownOn = false;
                mNoSpeechCountDown.cancel();
            }
            mIsListening = false;
            Message message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
            try
            {
                mServerMessenger.send(message);
            }
            catch (RemoteException e)
            {

            }
            if(error==8) {
                stopSelf();
                System.out.println(error);

                //Start the voicerec service
                mActivityContext = getApplicationContext();
                Intent service = new Intent(mActivityContext, VoiceControlTest.class);
                mActivityContext.startService(service);
            }
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {
            System.out.println("oEvt");
        }
        @Override
        public void onPartialResults(Bundle partialResults)
        {
            System.out.println("oPR");
        }
        @Override
        public void onReadyForSpeech(Bundle params)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            {
                mIsCountDownOn = true;
                mNoSpeechCountDown.start();
                mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
            }
            System.out.println("oRFSpeech");
        }
        /**
         * Handles the result of the speech recognition and calls the broadcaster if the
         * comparisons are correct.
         */
        @Override
        public void onResults(Bundle results)
        {
            System.out.println("onResults1");
            ArrayList<String> mGibberishList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            boolean spinCheck = false;
            for (int i=0; mGibberishList.size()>i; i++) {
                //keywords that will be checked when trying to make a spin.
                    if (mGibberishList.get(i).toLowerCase().equals("bing") ||
                        mGibberishList.get(i).toLowerCase().equals("teen") == true ||
                        mGibberishList.get(i).toLowerCase().contains("snurra") == true ||
                        mGibberishList.get(i).toLowerCase().contains("spin") ||
                        mGibberishList.get(i).toLowerCase().contains("rulla") == true
                        )
                {
                    spinCheck = true;
                }
            }
            if (spinCheck == true){
                spinCheck = false;
                //System.out.println("Spinågotannanstanstning...");
                sendMessage();
                mNoSpeechCountDown.start();

            }else{
                mNoSpeechCountDown.start();
            }
            //System.out.println(mGibberishList);
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
            /*System.out.println("oRmsC " + rmsdB )*/;
        }

    }

    //Needed for jellybean workaround
    //taken from StackOverFlow
    //http://stackoverflow.com/questions/18039429/
    //rmooney and Hoan Nguyen
    private final ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mServiceMessenger = new Messenger(service);
            Message msg = new Message();
            msg.what = VoiceControlTest.MSG_RECOGNIZER_START_LISTENING;

            try
            {
                mServerMessenger.send(msg);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
            System.out.println("oServiceConn");
        }
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            //System.out.println("oServiceDisconn");
            mServiceMessenger = null;
        }
    };

    /**
     *  Sends a broadcast that tells a listening class to spin.
     */
    private void sendMessage() {
        Intent spinIntent = new Intent("spinIntent");
        spinIntent.putExtra("spinIntent", "spinIntent");
        LocalBroadcastManager.getInstance(this).sendBroadcast(spinIntent);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        //System.out.println("Badjs on bind");
        return mServerMessenger.getBinder();
    }
}