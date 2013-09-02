package app.light;

import android.os.Bundle;
import android.view.Window;

public class ProfileActivity extends CommonActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_profile);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.basic_title);
	}
	
	
	
}
