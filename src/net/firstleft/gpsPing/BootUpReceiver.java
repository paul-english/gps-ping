package net.firstleft.gpsPing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootUpReceiver extends BroadcastReceiver {

	public static int LOGLEVEL = 1;
	public static boolean WARN = LOGLEVEL > 1;
	public static boolean DEBUG = LOGLEVEL > 0;
	
	public static final String TAG = "BootUpReceiver";
	
	@Override  
    public void onReceive(Context context, Intent intent) {  
		if(DEBUG) Log.v(TAG, "onReceive");
		Intent i = new Intent(context, Firstleft.class);    
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        context.startActivity(i);    
    }

}
