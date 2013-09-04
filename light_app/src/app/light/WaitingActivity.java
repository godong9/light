package app.light;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

public class WaitingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_waiting);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.basic_title);
	}
}
