package app.light;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JoinActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_join);
    }
       
    public void onJoinCompleteBtn(View v) {
		Intent intent = new Intent(JoinActivity.this, FragmentActivity.class);
		startActivity(intent);
	}
}