package net.firstleft.gpsPing;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;
import android.app.Activity;

public class GPSTracker extends Service {

    private static int INTERVAL = 60 * 1000;
    private static Timer timer = new Timer();
    public static int LOGLEVEL = 1;
    public static boolean WARN = LOGLEVEL > 1;
    public static boolean DEBUG = LOGLEVEL > 0;
    
    public static final String TAG = "GPSTrackerService";
	
    public void startService() {

        try {

            timer.scheduleAtFixedRate(new TimerTask() {
                
                @Override
                public void run() {

                    postData();

                }
		
            }, 0, INTERVAL);

        } catch(Exception e) {

            // TODO Exception timer was cancelled
            if(DEBUG) Log.v(TAG, "Exception: " + e.toString());

        }

    }
	
    public void stopService() {

        if (timer != null){
                
            timer.cancel();

        }
                
    }

    private String getMyPhoneNumber(){  

        TelephonyManager mTelephonyMgr;  

        mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);   

        if(DEBUG) Log.v(TAG, mTelephonyMgr.getLine1Number());

        return mTelephonyMgr.getLine1Number();  

    }  
      
    private String getMy10DigitPhoneNumber(){  

        String s = getMyPhoneNumber();  

        return s.substring(2);  

    }  
    
    public void postData() {  

    	if (DEBUG) Log.v(TAG, "postData");
    	
    	// Create a new HttpClient and Post Header  

        HttpClient httpclient = new DefaultHttpClient();  

        if (DEBUG) Log.v(TAG, "httpClient");

        HttpPost httppost = new HttpPost("http://firstleft.net/gpsPing");  

        if (DEBUG) Log.v(TAG, "httpPost");
      
        // Add your data  
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  

        nameValuePairs.add(new BasicNameValuePair("id", getMy10DigitPhoneNumber()));  

        if (DEBUG) Log.v(TAG, getMy10DigitPhoneNumber());

        nameValuePairs.add(new BasicNameValuePair("location", getGPS().toString()));  

        if (DEBUG) Log.v(TAG, getGPS().toString());

        try {  

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));  
      
            // Execute HTTP Post Request  
            HttpResponse response = httpclient.execute(httppost);  
            
            if (DEBUG) Log.v(TAG, response.toString());
            
        } catch (ClientProtocolException e) {  

            if (DEBUG) Log.v(TAG, e.toString());

        } catch (IOException e) {  

            if (DEBUG) Log.v(TAG, e.toString());

        }  

    } 
    
	@Override
	public IBinder onBind(Intent intent) {

		// TODO Auto-generated method stub

		return null;

	}

    public boolean locactionServiceAvaiable() {  

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  

        List<String> providers = lm.getProviders(true);   
    	    
        if(providers.size()>0) return true; else return false;  

    }  
    
    private double[] getGPS() {  

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    

        List<String> providers = lm.getProviders(true);  
      
        /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/  
        Location l = null;  
          
        for (int i=providers.size()-1; i>=0; i--) {  

            l = lm.getLastKnownLocation(providers.get(i));  

            if (l != null) break;  

        }  
          
        double[] gps = new double[2];  

        if (l != null) {  

            gps[0] = l.getLatitude();  

            gps[1] = l.getLongitude();  

        }  

        return gps;  

    }  

}
