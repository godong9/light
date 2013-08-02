package app.light;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_splash);
        
        Handler handler = new Handler () {
         @Override
         public void handleMessage(Message msg) {
        	 finish();
         }
        };
        
        handler.sendEmptyMessageDelayed(0, 2000);
    }
}
