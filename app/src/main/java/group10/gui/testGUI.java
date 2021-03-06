package group10.gui;

/**
 * This is the main class for the game itself.
 */

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;
import android.swedspot.scs.data.*;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import group10.R;
import group10.Voice.VoiceControlTest;
import group10.algorithm.algorithm;
import group10.db.JavaDBCon;
import kankan.wheel.widget.adapters.AbstractWheelAdapter;
import kankan.wheel.widget.annoyance.OnWheelChangedListener;
import kankan.wheel.widget.annoyance.OnWheelScrollListener;
import kankan.wheel.widget.annoyance.WheelView;

public class testGUI extends ActionBarActivity {
    //Creating an instance of the algorithm
    final algorithm slots=new algorithm();

    private static Context activityContext;
    private LinearLayout mix;
    private ViewSwitcher switcher;
    MediaPlayer Sound;
    MediaPlayer Sound2;
    MediaPlayer Sound3;
    MediaPlayer Sound4;
    MediaPlayer Sound5;
    MediaPlayer Sound6;

    EditText textInput;
    final Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start the voicerec service
        activityContext = this;
        Intent service = new Intent(activityContext, VoiceControlTest.class);
        activityContext.startService(service);
        //Register Receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(spinReceiver, new IntentFilter("spinIntent"));

        View decorView = getWindow().getDecorView();
        //Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //Initialize the different mediaplayers with different sounds
        Sound = MediaPlayer.create(this, R.raw.jockemedkniven);
        Sound2 = MediaPlayer.create(this, R.raw.lose);
        Sound3 = MediaPlayer.create(this, R.raw.tada);
        Sound4 = MediaPlayer.create(this, R.raw.dropmetalthing);
        Sound5 = MediaPlayer.create(this,R.raw.fanfar);
        Sound6 = MediaPlayer.create(this,R.raw.loliver);

        setContentView(R.layout.slot_machine_layout);
        initWheel(R.id.slot_1);
        initWheel(R.id.slot_2);
        initWheel(R.id.slot_3);

        //Listener for whole screen clicking
        mix = (LinearLayout)findViewById(R.id.btn_mix);
        mix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //User has more spins
                if (slots.getSpins()>0) {
                    slots.spin();
                    System.out.println("Result: " + slots.getResult());
                    System.out.println(slots.getSpins());

                    Sound.start();
                    mixWheel(R.id.slot_1, slots.get1(), 1, slots);
                    mixWheel(R.id.slot_2, slots.get2(), 2, slots);
                    mixWheel(R.id.slot_3, slots.get3(), 3, slots);

                }
                //User has no more spins
                else {
                    //Submit score
                    showPopup();
                    //plays sound
                    Sound6.start();
                }
            }
        });
        updateStatus();

        //Switcher to handle screen switching to black.
        switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcher);

        new AsyncTask() {
            //Ints to handle the states of the signals for AGA
            int wheelSpeed=0;
            int handBrake=0;
            //Int to handle current view
            int lastView=0;

            @Override
            protected Object doInBackground(Object... objects) {
                // Access to Automotive API
                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() { // Listener that observes the Signals
                            @Override
                            public void receive(final AutomotiveSignal automotiveSignal) {
                                //Switch to handle different signals from AGA
                                switch (automotiveSignal.getSignalId()) {
                                    //Case for speed
                                    case AutomotiveSignalId.FMS_WHEEL_BASED_SPEED:
                                        if (((SCSFloat) automotiveSignal.getData()).getFloatValue() > 0 && wheelSpeed==0) {
                                            wheelSpeed = 1;
                                        } else if (((SCSFloat) automotiveSignal.getData()).getFloatValue() == 0 && wheelSpeed==1) {
                                            wheelSpeed = 0;
                                        };
                                        break;
                                    //Case for parking brake
                                    case AutomotiveSignalId.FMS_PARKING_BRAKE:
                                        if (((Uint8) automotiveSignal.getData()).getIntValue() == 1 && handBrake==0) {
                                            handBrake = 1;
                                        } else if (((Uint8) automotiveSignal.getData()).getIntValue() == 0 && handBrake==1) {
                                            handBrake = 0;
                                        }
                                        break;
                                    //Default for other inputs
                                    default:
                                        break;
                                }//Switch statement end
                                switcher.post(new Runnable() { // Post the result back to the switcher
                                    public void run() {
                                        if (handBrake==1 && wheelSpeed==0 && lastView==1) {
                                            lastView=0;
                                            switcher.showPrevious();
                                        } else if ((handBrake==0 || wheelSpeed==1) && lastView==0) {
                                            lastView=1;
                                            switcher.showNext();
                                        }
                                    }
                                });
                            }
                            @Override
                            public void timeout(int i) {}
                            @Override
                            public void notAllowed(int i) {}
                        },
                        new DriverDistractionListener() {       // Observe driver distraction level

                            @Override
                            public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                                new Runnable() {
                                    public void run() {
                                        //TODO
                                        //Would have had an action if we had time to implement.
                                    }
                                };
                            }
                            @Override
                            public void stealthModeChanged(StealthMode stealthMode) {}
                            @Override
                            public void lightModeChanged(LightMode lightMode) {}
                        }

                ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED, AutomotiveSignalId.FMS_PARKING_BRAKE); // Register for the signals
                return null;
            }
        }.execute(); // And go!


    }

    // Wheel scrolled flag
    private boolean wheelScrolled = false;

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            updateStatus();
        }
    };

    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
                //Plays different sound on different outcomes
                if(!Sound.isPlaying()) {
                    if (slots.getResult() <= 10) {
                        //A loss
                        Sound2.start();
                    }
                    if (11 <= slots.getResult() && slots.getResult() <= 58) {
                        if (!Sound.isPlaying()) {
                            //Small win
                            Sound4.start();
                        }
                    }
                    if (59 <= slots.getResult() && slots.getResult() <= 88) {
                        if (!Sound.isPlaying()) {
                            //Medium win
                            Sound3.start();
                        }
                    }
                    if (89 <= slots.getResult()) {
                        if (!Sound.isPlaying()) {
                            //Large win
                            Sound5.start();
                        }
                    }
                }
                updateStatus();
            }
        }
    };

    /**
     * Updates status
     */
    private void updateStatus() {
        TextView textMain = (TextView) findViewById(R.id.pwd_status);
        textMain.setText(String.valueOf(slots.getScore()));
    }

    /**
     * Initializes wheel
     * @param id the wheel widget Id
     */
    private void initWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(new SlotMachineAdapter(this));
        wheel.setCurrentItem(0);

        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(false);
    }

    /**
     * Returns wheel by Id
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }

    /**
     * Tests wheels
     * @return true
     */
    private boolean test() {
        int value = getWheel(R.id.slot_1).getCurrentItem();
        return testWheelValue(R.id.slot_2, value) && testWheelValue(R.id.slot_3, value);
    }

    /**
     * Tests wheel value
     * @param id the wheel Id
     * @param value the value to test
     * @return true if wheel value is equal to passed value
     */
    private boolean testWheelValue(int id, int value) {
        return getWheel(id).getCurrentItem() == value;
    }

    /**
     * Mixes wheel
     * @param id the wheel id
     *           wheel.scroll(number + (int)(Match.random()* number), number<-- how long it spins)
     */

    private void mixWheel(int id, int slots, int time, algorithm slot) {

        WheelView wheel = getWheel(id);
        slots-=1;
        slots=slots-getWheel(id).getCurrentItem();
        System.out.println("Current item: "+getWheel(id).getCurrentItem());
        System.out.println("ID: " + id);
        if (slots<0){
            slots+=12;
        }
        wheel.scroll(slots - (12 * 4), 3150 * time);

    }

    /**
     * Slot machine adapter
     */
    private class SlotMachineAdapter extends AbstractWheelAdapter {
        // Image size
        final int IMAGE_WIDTH = 100;
        final int IMAGE_HEIGHT = 100;

        // Slot machine symbols Symbol is in order of
        // Square of transparent round picture is best!
        private final int items[] = new int[] {
                R.drawable.cherry,
                R.drawable.fir,
                R.drawable.bar,
                R.drawable.bell,
                R.drawable.snowflake,
                R.drawable.cask,
                R.drawable.nowball,
                R.drawable.deer,
                R.drawable.euro,
                R.drawable.pay,
                R.drawable.leprechaun,
                R.drawable.whitewild
        };

        // Cached images
        private List<SoftReference<Bitmap>> images;

        // Layout inflater
        private Context context;

        /**
         * Constructor
         */
        public SlotMachineAdapter(Context context) {
            this.context = context;
            images = new ArrayList<SoftReference<Bitmap>>(items.length);
            for (int id : items) {
                images.add(new SoftReference<Bitmap>(loadImage(id)));
            }
        }

        /**
         * Loads image from resources
         */
        private Bitmap loadImage(int id) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);
            //bitmap.recycle();
            return scaled;
        }

        @Override
        public int getItemsCount() {
            return items.length;
        }

        // Layout params for image view
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            ImageView img;
            if (cachedView != null) {
                img = (ImageView) cachedView;
            } else {
                img = new ImageView(context);
            }
            img.setLayoutParams(params);
            SoftReference<Bitmap> bitmapRef = images.get(index);
            Bitmap bitmap = bitmapRef.get();
            if (bitmap == null) {
                bitmap = loadImage(items[index]);
                images.set(index, new SoftReference<Bitmap>(bitmap));
            }
            img.setImageBitmap(bitmap);

            return img;
        }
    }

    //The dialog that pops up after the game session is finished
    public void  showPopup(){
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.game_finished_dialog);
        dialog.setTitle("You have run out of spins!");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.textViewScore);
        text.setText(String.valueOf(slots.getScore()));

        textInput = (EditText) (dialog.findViewById(R.id.editTextName));
        Button submit = (Button) dialog.findViewById(R.id.buttonSubmit);
        // if button is clicked, close the custom dialog
        // Listener for the "submit" button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JavaDBCon.InsertUser(textInput.getText().toString(), slots.getScore());
                dialog.dismiss();
            }
        });

        // Listener for the "quit" button.
        Button quit = (Button) dialog.findViewById(R.id.buttonQuit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    //Terminates the service
    public void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this, VoiceControlTest.class));
    }

    private BroadcastReceiver spinReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            mix.performClick();
            System.out.println("broadcastrecieved");

        }
    };
}
