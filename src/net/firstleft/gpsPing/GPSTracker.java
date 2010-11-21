package net.firstleft.gpsPing;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class GPSTracker extends Service {

	private static int INTERVAL = 10 * 1000;
	private static Timer timer = new Timer();
	public static int LOGLEVEL = 1;
	public static boolean WARN = LOGLEVEL > 1;
	public static boolean DEBUG = LOGLEVEL > 0;
	
	public static final String TAG = "GPSTrackerService";
	
	public static void startService() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO get GPS Location & send post to server
				if(DEBUG) Log.v(TAG, "timerTask");
			}
		
		}, 0, INTERVAL);
	}
	
	public static void stopService() {

		if (timer != null){

			timer.cancel();

		}

    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
