package net.firstleft.gpsPing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

public class Firstleft extends Activity {

	public static int LOGLEVEL = 1;
	public static boolean WARN = LOGLEVEL > 1;
	public static boolean DEBUG = LOGLEVEL > 0;
	
	public static final String TAG = "gpsPingActivity";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.v(TAG, "hello world");
        postData();

        final CheckBox checkbox = (CheckBox) findViewById(R.id.gpsRunning);
        checkbox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                	if(DEBUG) Log.v(TAG, "checked");
                	GPSTracker.startService();
                } else {
                	if(DEBUG) Log.v(TAG, "unchecked");
                	GPSTracker.stopService();
                }
            }
        });
 
    }
    
    private String getMyPhoneNumber(){  
        TelephonyManager mTelephonyMgr;  
        mTelephonyMgr = (TelephonyManager)  
            getSystemService(Context.TELEPHONY_SERVICE);   
        return mTelephonyMgr.getLine1Number();  
    }  
      
    private String getMy10DigitPhoneNumber(){  
        String s = getMyPhoneNumber();  
        return s.substring(2);  
    }  
    
    public boolean locactionServiceAvaiable() {  
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
        List<String> providers = lm.getProviders(true);   
    	    
        if(providers.size()>0) return true; else return false;  
    }  
    
    public void postData() {  
    	if (DEBUG) Log.v(TAG, "postData");
    	
    	// Create a new HttpClient and Post Header  
        HttpClient httpclient = new DefaultHttpClient();  
        HttpPost httppost = new HttpPost("http://firstleft.net/gpsPing");  
      
        try {  
            // Add your data  
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
            nameValuePairs.add(new BasicNameValuePair("id", getMy10DigitPhoneNumber()));  
            nameValuePairs.add(new BasicNameValuePair("location", getGPS().toString()));  
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));  
      
            // Execute HTTP Post Request  
            HttpResponse response = httpclient.execute(httppost);  
            
            TextView myText = (TextView) findViewById(R.id.text);
            
            if (DEBUG) Log.v(TAG, response.toString());
            
            myText.setText(response.toString());
            
        } catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
        }  

        setContentView(R.layout.main);
        
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