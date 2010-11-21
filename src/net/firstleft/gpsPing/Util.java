package net.firstleft.gpsPing;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Util {

	public String getMyPhoneNumber(){  
        TelephonyManager mTelephonyMgr;  
        mTelephonyMgr = (TelephonyManager)  
            getSystemService(Context.TELEPHONY_SERVICE);   
        return mTelephonyMgr.getLine1Number();  
    }  
      
    public String getMy10DigitPhoneNumber(){  
        String s = getMyPhoneNumber();  
        return s.substring(2);  
    }  
    
}
