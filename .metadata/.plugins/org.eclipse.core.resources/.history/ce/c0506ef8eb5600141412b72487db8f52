package geo.geoapplication;

import geo.geoapplication.util.SystemUiHider;

import java.util.Date;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.TextView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    
    private TextView gpsInformation;
    private TextView viewLongitude;
    private TextView viewLatitude;
    private TextView speed;
    private TextView distance;
    private TextView accuracy;
    private TextView lastFix;
    private Button startButton;
    
    private Location location;
    
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        viewLatitude  = (TextView)findViewById(R.id.viewLatitude);
        viewLongitude = (TextView)findViewById(R.id.viewLongitude);
        gpsInformation = (TextView)findViewById(R.id.gpsInformation);
        speed = (TextView)findViewById(R.id.speed);
        distance = (TextView)findViewById(R.id.distance);
        accuracy = (TextView)findViewById(R.id.accuracy);
        lastFix = (TextView)findViewById(R.id.lastFix);
        startButton = (Button)findViewById(R.id.startButton);
        
        ////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////
        //writing gps information
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            gpsInformation.setText("GPS");
        else
        	gpsInformation.setText("GPS Disabled. Please, turn it on");
        
        
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        
        showLocation(location);
        showAdditionalInfo(location);
        
        
        startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showLocation(location);
		        showAdditionalInfo(location);
			}
		});
        
        ///////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        /*findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    
    
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) { 
            
        }
        
        @Override
        public void onProviderEnabled(String arg0) {
            
        }
        
        @Override
        public void onProviderDisabled(String arg0) {
            
        }
        
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
            showAdditionalInfo(location);
            if(savedLocation == null)
                savedLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    };
    
    
    @Override
    protected void onStart() {
        super.onStart();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
                                               3000, 
                                               2, 
                                               locationListener);
    }
    
    
    @Override
    protected void onStop() {
        locationManager.removeUpdates(locationListener);
        super.onStop();
    }
    
    
    private void showLocation(Location location) {
        String latitude  = "Latitude: ";
        String longitude = "Longitude: ";
        if(location == null) {
            latitude  += "...";
            longitude += "...";
        } else {
            latitude  += location.getLatitude();
            longitude += location.getLongitude();
        }
        
        viewLatitude.setText(latitude);
        viewLongitude.setText(longitude);
    }
    
    private Location savedLocation = null;
      
    
    private void showAdditionalInfo(Location location) {
        String distance = "Distance: ";
        String accuracy = "Accuracy: ";
        String speed = "Speed: ";
        String lastFix = "Last fix: ";
        if(savedLocation == null || location == null) {
        	distance += "can't calculate";
        	accuracy += "can't calculate";
        	speed += "can't calculate";
        	lastFix += "can't calculate";
        }
        else {
        	distance += savedLocation.distanceTo(location) + "m";
        	accuracy += location.getAccuracy() + "m \n";
            lastFix += new Date(location.getTime()).toGMTString() + "\n";
            speed += location.getSpeed() + "m/s";
        }            
        
        this.speed.setText(speed);
        this.distance.setText(distance);
        this.accuracy.setText(accuracy);
        this.lastFix.setText(lastFix);
    }
   
}


