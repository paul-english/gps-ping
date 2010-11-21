package net.firstleft.gpsPing;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class Firstleft extends Activity {

    public static int LOGLEVEL = 1;
    public static boolean WARN = LOGLEVEL > 1;
    public static boolean DEBUG = LOGLEVEL > 0;
	
    public static final String TAG = "gpsPingActivity";
	
    private GPSTracker tracker = new GPSTracker(); 

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.v(TAG, "hello world");
        setContentView(R.layout.main);
         
        final CheckBox checkbox = (CheckBox) findViewById(R.id.gpsRunning);

        checkbox.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                	if(DEBUG) Log.v(TAG, "checked");
                	tracker.startService();
                    } else {
                	if(DEBUG) Log.v(TAG, "unchecked");
                	tracker.stopService();
                    }
                }
            });
        
    }

}