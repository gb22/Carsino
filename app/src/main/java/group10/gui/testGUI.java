package group10.gui;

import android.app.AlertDialog;
import android.app.Activity;
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
import android.view.LayoutInflater;
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

        Sound = MediaPlayer.create(this, R.raw.jockemedkniven);

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
                    mixWheel(R.id.slot_1, slots.get1(), 1);
                    mixWheel(R.id.slot_2, slots.get2(), 2);
                    mixWheel(R.id.slot_3, slots.get3(), 3);
                }
                //User has no more spins
                else {
                    //TODO
                    //Submit score
                    showPopup();
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
                                new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        //TODO
                                        //What happens when to distracted.
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
    /*
         x=10  y=11  z=12
       0. Nothing
       1. 111 -> 222 -> 333 ...
       2. 111 -> 333 ...
       3. 111 -> 444 ...
       4. 111 -> 555 ...
       5. 111 -> 666 ...
       6. 111 -> 777 ...
       7. 111 -> 888 ...
       8. 111 -> 999 ...
       9. 111 -> xxx ...
      10. 111 -> yyy ...
      11. 111 -> zzz ...
      12. 111 -> 111 with a "360" (0)
      13. 111 -> 222 with a "360" (1)
      24. 111 ->

        slots-=1 (Off by one), ID is fucked up...
     */
    /*112,2000 Gives strange same result each spin...
      16/-16 will give the result xyy
      17 xyz-> kxy-> lkx
      18 yyx
     */

    private void mixWheel(int id, int slots, int time) {

        WheelView wheel = getWheel(id);
        slots-=1;
        slots=slots-getWheel(id).getCurrentItem();
        System.out.println("Current item: "+getWheel(id).getCurrentItem());
        System.out.println("ID: " + id);
        // slots=Math.abs(slots);
        if (slots<0){
            slots+=12;
        }
        wheel.scroll(slots - (12 * 4), 5000 * time);
    }

    /**
     * Slot machine adapter
     */
    private class SlotMachineAdapter extends AbstractWheelAdapter {
        // Image size
        final int IMAGE_WIDTH = 50;
        final int IMAGE_HEIGHT =50;

        // Slot machine symbols Symbol is in order of
        // Square of transparent round picture is best!
        private final int items[] = new int[] {
                android.R.drawable.star_big_on,
                android.R.drawable.stat_sys_warning,
                android.R.drawable.radiobutton_on_background,
                android.R.drawable.ic_delete,
                android.R.drawable.ic_dialog_email,
                R.drawable.usa,
                R.drawable.world_of_warcraft,
                R.drawable.canada,
                R.drawable.france,
                R.drawable.ukraine,
                android.R.drawable.button_onoff_indicator_off,
                android.R.drawable.arrow_down_float
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
    public void showPopup(){
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialog.setView(inflater.inflate(R.layout.game_finished_dialog, null));
        alertDialog.setTitle("You have run out of spins!");

        //Get actual score
        setContentView(R.layout.game_finished_dialog);
        TextView textDialog = (TextView) findViewById(R.id.textViewScore);
        System.out.println("Score: " + slots.getScore());
        textDialog.setText(slots.getScore());
        //TODO Not working
        alertDialog.show();
    }

    //Restart button
    public void restart(View v) {
        recreate();
    }
    //Quit button
    public void quit(View v) {
        finish();
    }
    //Submit score button
    public void submit(View v) {
        //TODO
        //Submit to DB
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
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
    */
}
