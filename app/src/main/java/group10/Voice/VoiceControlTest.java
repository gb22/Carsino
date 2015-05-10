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

import java.lang.ref.WeakReference;

import group10.carsino.MainActivity;

/**
 * Created by John on 5/3/2015.
 */
public class VoiceControlTest extends Service {
    public AudioManager mAudioManager;
    public SpeechRecognizer mSpeechRecognizer;
    public Intent mSpeechRecognizerIntent;
    public final Messenger mServerMessenger = new Messenger(new IncomingHandler(this));

    protected boolean mIsListening;
    protected volatile boolean mIsCountDownOn;

    public static final int MSG_RECOGNIZER_START_LISTENING = 1;
    public static final int MSG_RECOGNIZER_CANCEL = 2;

    //hoan
    private int mBindFlag;
    private Messenger mServiceMessenger;

    @Override
    public void onCreate()
    {
        super.onCreate();
        System.out.println("yay?");
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        bindService(new Intent(this, VoiceControlTest.class), mServiceConnection, mBindFlag);
    }

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
                        // turn off beep sound
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

    // Count down timer for Jelly Bean work around
    protected CountDownTimer mNoSpeechCountDown = new CountDownTimer(5000, 5000)
    {

        @Override
        public void onTick(long millisUntilFinished)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFinish()
        {
            mIsCountDownOn = false;
            Message message = Message.obtain(null, MSG_RECOGNIZER_CANCEL);
            try
            {
                mServerMessenger.send(message);
                message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
                mServerMessenger.send(message);
            }
            catch (RemoteException e)
            {

            }
        }
    };

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (mIsCountDownOn)
        {
            mNoSpeechCountDown.cancel();
        }
        if (mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }
    }

    protected class SpeechRecognitionListener implements RecognitionListener
    {

        @Override
        public void onBeginningOfSpeech()
        {
            // speech input will be processed, so there is no need for count down anymore
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
            System.out.println("oBR");
        }

        @Override
        public void onEndOfSpeech()
        {
            System.out.println("oEOS");
            MainActivity.sayHi();
        }

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
            System.out.println(error);
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

        @Override
        public void onResults(Bundle results)
        {
            System.out.println("oRes");

        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
            System.out.println("oRmsC");
        }

    }
    //hoan

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
            System.out.println("oServiceDisconn");
            mServiceMessenger = null;
        }

    }; // mServiceConnection

    @Override
    public IBinder onBind(Intent intent)
    {
        System.out.println("Badjs on bind");
        return mServerMessenger.getBinder();
    }
}